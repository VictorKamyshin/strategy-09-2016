package ru.mail.park.mechanics.internal;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.mail.park.mechanics.game.CoordPair;
import ru.mail.park.messageSystem.Address;
import ru.mail.park.messageSystem.MessageSystem;
import ru.mail.park.messageSystem.MessagesToGameMechanics.*;
import ru.mail.park.model.UserProfile;

/**
 * Created by victor on 14.11.16.
 */
@Service
public class GameProgressService {

    private Address myAddress = new Address();

    private Address gameMechanincsAddress;

    private MessageSystem ms;

    public GameProgressService(@NotNull GameMechanicsInNewThread gameMechanicsInNewThread,
                               @NotNull MessageSystem ms){
        this.gameMechanincsAddress = gameMechanicsInNewThread.getAddress();
        this.ms = ms;
    }

    public void coinAction(Integer piratId, Boolean pickCoin, Boolean dropCoin, Long playerId){
        ms.sendMessage(new CoinActionMessageToMechanics(myAddress,gameMechanincsAddress,playerId, piratId, true, false));
    }

    public void movePirat(Integer piratId, CoordPair targetCell, Long playerId){
        ms.sendMessage(new MovePiratMessage(myAddress, gameMechanincsAddress, piratId, targetCell, playerId));
    }

    public void sendNeighbord(Integer cellIndex, Long playerId){
        ms.sendMessage(new GetNeighborsMessage(myAddress, gameMechanincsAddress,cellIndex, playerId));

    }

    public void moveShip(CoordPair targetCell, Long playerId){
        ms.sendMessage(new MoveShipMessage(myAddress, gameMechanincsAddress,targetCell,playerId));

    }

    public void initGameFor(UserProfile firstPlayer, UserProfile secondPLayer) {
        ms.sendMessage(new InitGameMessage(myAddress,gameMechanincsAddress,firstPlayer, secondPLayer));
    }

}
