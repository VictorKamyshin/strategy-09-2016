package ru.mail.park.mechanics.internal;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mail.park.mechanics.GameContent;
import ru.mail.park.mechanics.game.CoordPair;
import ru.mail.park.mechanics.game.GameBoard;
import ru.mail.park.mechanics.utils.results.Result;
import ru.mail.park.messageSystem.Abonent;
import ru.mail.park.messageSystem.Address;
import ru.mail.park.messageSystem.MessageSystem;
import ru.mail.park.messageSystem.MessagesToSender.*;
import ru.mail.park.model.UserProfile;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by victor on 13.12.16.
 */
@Service
public class GameMechanicsInNewThread implements Runnable, Abonent { //Новая игровая механика, которая будет жить в отдельном треде

    @NotNull
    private final Map<Long, GameContent> usersToGamesMap = new HashMap<>(); //связь юзеров и игр

    @NotNull
    private final Map<Long, Long> userToUserMap = new HashMap<>();

    private MessageSystem ms;

    private static final long STEP_TIME = 100;

    private Executor tickExecutor = Executors.newSingleThreadExecutor();

    private final Address myAddress = new Address();

    private final Address senderAddress;

    @Autowired
    public GameMechanicsInNewThread(MessageSystem ms, SenderMessageToFront senderMessageToFront) {
        this.ms = ms;
        ms.addAbonent(myAddress);
        this.senderAddress = senderMessageToFront.getAddress(); //А если двум сервисам надо будет послать сообщения друг другу?
    }

    @PostConstruct
    public void initAfterStartup() {
        tickExecutor.execute(this);
    }

    @Override
    public void run() {
        while (true) {
            try { //Все остальные методы вызываются при выполнении сообщений в этом цикле
                ms.execForAbonent(this);
                Thread.sleep(STEP_TIME);//сделать так, чтобы таски выполнялись через равные промежутки времени
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void createNewGame(@NotNull UserProfile firstPlayer, @NotNull UserProfile secondPlayer){
        final GameContent game = new GameContent(firstPlayer.getId(), secondPlayer.getId()); //Сообщение для игровой механики
        usersToGamesMap.put(firstPlayer.getId(), game);
        usersToGamesMap.put(secondPlayer.getId(), game); // создали игру, запомнили ее связь с пользователями
        userToUserMap.put(firstPlayer.getId(),secondPlayer.getId());
        userToUserMap.put(secondPlayer.getId(), firstPlayer.getId());
        System.out.println("Created gameBoard");
        ms.sendMessage(new InitGameMessageToFront(myAddress,senderAddress,game.getMap(),firstPlayer,secondPlayer));
    }

    public void movePirat(Integer piratId, CoordPair targetCell, Long firstPlayerId) {
        if (usersToGamesMap.containsKey(firstPlayerId)) {
            final List<Result> result = usersToGamesMap.get(firstPlayerId).movePirat(piratId, targetCell, firstPlayerId); //Сообщение для игровой механики
            if(result==null){
                ms.sendMessage(new InfoMessage(myAddress, senderAddress, "Такой ход невозможен. Скорее всего, вы ошиблись в выборе клетки", firstPlayerId, userToUserMap.get(firstPlayerId)));
            } else {
                ms.sendMessage(new PiratMoveResultMessage(myAddress, senderAddress,result,firstPlayerId, userToUserMap.get(firstPlayerId)));
            }
            if(usersToGamesMap.get(firstPlayerId).getCountOfTurns()>29){
                System.out.println(usersToGamesMap.get(firstPlayerId).getCountOfTurns());
                ms.sendMessage(new EndGameMessage(myAddress, senderAddress, firstPlayerId, userToUserMap.get(firstPlayerId)));
                usersToGamesMap.remove(firstPlayerId);
                usersToGamesMap.remove(userToUserMap.get(firstPlayerId));
                userToUserMap.remove(userToUserMap.get(firstPlayerId));
                userToUserMap.remove(firstPlayerId);
            }
        }
    }

    public void getNeighbor(Integer cellIndex, Long playerId){

        final Integer x = cellIndex % GameBoard.BOARDWIGHT;
        final Integer y = cellIndex / GameBoard.BOARDWIGHT;
        final CoordPair piratCord = new CoordPair(x,y);
        final CoordPair[] neighbors = usersToGamesMap.get(playerId).getNeighbors(piratCord, playerId); //Сообщение для игровой механики

        final List<Integer> neighborsList = new ArrayList<>();
        for(CoordPair cell:neighbors){
            neighborsList.add(GameBoard.BOARDWIGHT*cell.getY()+cell.getX());
        }

        if(CoordPair.equals(piratCord,usersToGamesMap.get(playerId).getShipCord(playerId))){
            final CoordPair[] shipNeighbors = usersToGamesMap.get(playerId).getShipAvailableDirection(playerId); //Сообщение для игровой механики
            for(CoordPair cell:shipNeighbors){
                final CoordPair tempPair = new CoordPair((cell.getY()+piratCord.getY()),(cell.getX()+piratCord.getX()));
                if(tempPair.getX()>1&&tempPair.getX()<11) {
                    neighborsList.add(GameBoard.BOARDWIGHT * (cell.getY() + piratCord.getY()) + (cell.getX() + piratCord.getX()));
                }
            }
        }

        ms.sendMessage(new NeighborsMessage(myAddress,senderAddress,neighborsList, playerId));

    }

    public void moveShip(CoordPair targetCell, Long playerId){
        System.out.println("Готовимся двигать корабль");
        if(usersToGamesMap.containsKey(playerId)){
            final List<Result> shipMovementResults = usersToGamesMap.get(playerId).moveShip(targetCell, playerId);
            if(shipMovementResults!=null){ //Сообщение для игровой механики
                ms.sendMessage(new ShipMoveToSender(myAddress,senderAddress,playerId, userToUserMap.get(playerId),shipMovementResults));
                //ms.sendMessage(new InfoMessage(myAddress, senderAddress,"корабль передвинулся, но мы этого пока не увидим",playerId));
            } else {
                ms.sendMessage(new InfoMessage(myAddress, senderAddress,"Капитан, корабль не может туда плыть.",playerId,userToUserMap.get(playerId)));
            }
        } else {
            ms.sendMessage(new InfoMessage(myAddress, senderAddress,"Этот игрок вообще не участвует в играх",playerId, userToUserMap.get(playerId)));
        }
    }

    public void coinAction(Integer piratId, Boolean pickCoin, Boolean dropCoin, Long firstPlayerId){
        if(pickCoin && dropCoin){
            ms.sendMessage(new InfoMessage(myAddress, senderAddress,"Ты прислал мне какую-то дичь", firstPlayerId, userToUserMap.get(firstPlayerId)));
        } else if( !pickCoin && !dropCoin){
            ms.sendMessage(new InfoMessage(myAddress, senderAddress,"Ты прислал мне какую-то дичь", firstPlayerId, userToUserMap.get(firstPlayerId)));
        } else {
            final List<Result> coinAction = usersToGamesMap.get(firstPlayerId).coinAction(pickCoin, dropCoin, piratId, firstPlayerId);
            try{
            if(coinAction.get(0).getStatus()==-1){
                ms.sendMessage(new InfoMessage(myAddress, senderAddress,"У пирата уже есть монетка или ему нечего выбрасывать", firstPlayerId, userToUserMap.get(firstPlayerId)));
            }
            if(coinAction.get(0).getStatus()==-2){
                ms.sendMessage(new InfoMessage(myAddress, senderAddress,"В локации не было монетки", firstPlayerId, userToUserMap.get(firstPlayerId)));
            }
            if( coinAction.get(0).getStatus()==0){
                ms.sendMessage(new InfoMessage(myAddress, senderAddress,"Норм сообщение о монетке, но пока не могу его отрисовать", firstPlayerId, userToUserMap.get(firstPlayerId)));
            }
            } catch(NullPointerException e){
                System.out.println("Кто-то подсунул нам нулевой список, что за фигня");
            }
            ms.sendMessage(new CoinActionMessageToSender(myAddress, senderAddress,coinAction, firstPlayerId,userToUserMap.get(firstPlayerId)));
        }
    }

    @Override
    public Address getAddress(){
        return this.myAddress;
    }

}
