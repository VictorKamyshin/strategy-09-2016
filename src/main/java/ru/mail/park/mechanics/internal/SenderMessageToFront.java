package ru.mail.park.mechanics.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mail.park.mechanics.requests.toUsers.*;
import ru.mail.park.mechanics.utils.results.GameOverResult;
import ru.mail.park.mechanics.utils.results.MovementResult;
import ru.mail.park.mechanics.utils.results.Result;
import ru.mail.park.messageSystem.Abonent;
import ru.mail.park.messageSystem.Address;
import ru.mail.park.messageSystem.MessageSystem;
import ru.mail.park.model.UserProfile;
import ru.mail.park.websocket.Message;
import ru.mail.park.websocket.MessageToClient;
import ru.mail.park.websocket.RemotePointService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by victor on 13.12.16.
 */
@Service
public class SenderMessageToFront implements  Runnable, Abonent{
    private MessageSystem ms; //класс, который будет слать сообщения фронту

    private static final long STEP_TIME = 100;

    private Executor tickExecutor = Executors.newSingleThreadExecutor();

    private final Address myAddress = new Address();

    @NotNull
    private final RemotePointService remotePointService; // сервис, рассылающие сообщения юзерам

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public SenderMessageToFront(MessageSystem ms, @NotNull RemotePointService remotePointService) {
        this.ms = ms;
        ms.addAbonent(myAddress);
        this.remotePointService = remotePointService;
    }

    @PostConstruct
    public void initAfterStartup() {
        tickExecutor.execute(this);
    }

    @Override
    public void run() {
        while (true) try {
            ms.execForAbonent(this);
            Thread.sleep(STEP_TIME);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void infoMessage(String messageContent, Long playerId, Long secondPlayerId) {
        final MessageToClient.Request infoMessage = new MessageToClient.Request(); //вещь для отладки
        infoMessage.setMyMessage(messageContent);
        try {
            final Message responseMessage = new Message(MessageToClient.Request.class.getName(),
                    objectMapper.writeValueAsString(infoMessage));
            remotePointService.sendMessageToUser(playerId,responseMessage);
            remotePointService.sendMessageToUser(secondPlayerId,responseMessage);
        } catch( IOException e){
            e.printStackTrace();
        }
    }

    public void sendNeighbors(List<Integer> neighborsList, Long playerId) {
        final NeighborsMessage.Request messageWithNeighbors = new NeighborsMessage.Request();
        messageWithNeighbors.setNeighbors(neighborsList);
        try{
            final Message responseMessage = new Message(NeighborsMessage.Request.class.getName(),
                    objectMapper.writeValueAsString(messageWithNeighbors));
            remotePointService.sendMessageToUser(playerId,responseMessage);
        } catch( IOException e){
            e.printStackTrace();
        }
    }

    public void sendShipMove(List<Result> results, Long activePlayerId, Long passivePlayerId){
        System.out.println("Готовимся отсылать движение корабля");
        System.out.println("Нулл?? "+(results!=null));
        final ShipMoveMessage.Request shipMoveMessage = new ShipMoveMessage.Request();
        shipMoveMessage.setMovements(new Gson().toJson(results));
        shipMoveMessage.setActive(false);
        try {
            final Message responseMessageToActivePLayer = new Message(ShipMoveMessage.Request.class.getName(),
                    objectMapper.writeValueAsString(shipMoveMessage));
            remotePointService.sendMessageToUser(activePlayerId,responseMessageToActivePLayer);
        } catch( IOException e){
            e.printStackTrace();
        }
        try {
            shipMoveMessage.setActive(true);
            final Message responseMessageToPassivePlayer = new Message(ShipMoveMessage.Request.class.getName(),
                    objectMapper.writeValueAsString(shipMoveMessage));
            remotePointService.sendMessageToUser(passivePlayerId, responseMessageToPassivePlayer);//надо переделать sessionService, чтобы не было таких цепочек
        } catch( IOException e){
            e.printStackTrace();
        }
    }

    public void sendCoinAction(List<Result> results, Long activePlayerId, Long passivePlayerId){
        final CoinActionMessage.Request coinActionMessage = new CoinActionMessage.Request();
        coinActionMessage.setMovement(new Gson().toJson(results));
        try {
            final Message responseMessage = new Message(CoinActionMessage.Request.class.getName(),
                    objectMapper.writeValueAsString(coinActionMessage));
            remotePointService.sendMessageToUser(activePlayerId,responseMessage);
            remotePointService.sendMessageToUser(passivePlayerId,responseMessage);
        } catch( IOException e){
            e.printStackTrace();
        }
    }

    public void piratMove(List<Result> movementResults, Long activePlayerId, Long passivePlayerId){
        if(movementResults.get(0) instanceof GameOverResult){
            final GameOverResult result = (GameOverResult) movementResults.get(0);
            final GameOverMessage.Request gameOverMessage = new GameOverMessage.Request();
            gameOverMessage.setWinner(result.getWinnerId());
            try {
                final Message gameOver = new Message(GameOverMessage.Request.class.getName(),
                        objectMapper.writeValueAsString(gameOverMessage));
                remotePointService.sendMessageToUser(activePlayerId,gameOver);
                remotePointService.sendMessageToUser(passivePlayerId,gameOver);
            } catch( IOException e){
                e.printStackTrace();
            }
        }

        final PiratMoveMessage.Request newTurnMessage = new PiratMoveMessage.Request();
        newTurnMessage.setActive(false);
        final List<MovementResult> moveResults = new ArrayList<>();
        for(Result rs:movementResults){
            moveResults.add((MovementResult)rs);
        }
        newTurnMessage.setMovement(new Gson().toJson(movementResults));
        try {
            final Message responseMessageToActivePLayer = new Message(PiratMoveMessage.Request.class.getName(),
                    objectMapper.writeValueAsString(newTurnMessage));
            remotePointService.sendMessageToUser(activePlayerId,responseMessageToActivePLayer);
        } catch( IOException e){
            e.printStackTrace();
        }
        try {
            newTurnMessage.setActive(true);
            final Message responseMessageToPassivePlayer = new Message(PiratMoveMessage.Request.class.getName(),
                    objectMapper.writeValueAsString(newTurnMessage));
            remotePointService.sendMessageToUser(passivePlayerId, responseMessageToPassivePlayer);//надо переделать sessionService, чтобы не было таких цепочек
        } catch( IOException e){
            e.printStackTrace();
        }
    }

    public void initGame(List<Integer> BoardMap, UserProfile firstPLayer, UserProfile secondPlayer){

        final BoardMapForUsers.Request requestToFirstPlayer = new BoardMapForUsers.Request();
        requestToFirstPlayer.setGameBoard(BoardMap);
        requestToFirstPlayer.setEnemyNick(secondPlayer.getLogin());
        requestToFirstPlayer.setActive(true);

        final BoardMapForUsers.Request requestToSecondPlayer = new BoardMapForUsers.Request();
        requestToSecondPlayer.setGameBoard(BoardMap);
        requestToSecondPlayer.setEnemyNick(secondPlayer.getLogin());
        requestToSecondPlayer.setActive(false);
        //noinspection OverlyBroadCatchBlock
        try {
            System.out.println("Send start message to first player");
            final Message messageToFirst = new Message(BoardMapForUsers.Request.class.getName(),
                    objectMapper.writeValueAsString(requestToFirstPlayer));
            remotePointService.sendMessageToUser(firstPLayer.getId(), messageToFirst);

            System.out.println("Send start message to second player");
            final Message messageToSecond = new Message(BoardMapForUsers.Request.class.getName(),
                    objectMapper.writeValueAsString(requestToSecondPlayer));
            remotePointService.sendMessageToUser(secondPlayer.getId(), messageToSecond);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPingResponse(Long forUser){
        final ReplyPingMessage.Request replyPingMessage = new ReplyPingMessage.Request();
        replyPingMessage.setPingMessage("Up. Got ur message");
        try {
            final Message pingMessage = new Message(ReplyPingMessage.Request.class.getName(),
                    objectMapper.writeValueAsString(replyPingMessage));
            remotePointService.sendMessageToUser(forUser, pingMessage);
        } catch( Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public Address getAddress(){
        return this.myAddress;
    }
}
