package ru.mail.park.messageSystem.MessagesToSender;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.internal.SenderMessageToFront;
import ru.mail.park.messageSystem.Address;

/**
 * Created by victor on 24.12.16.
 */
public class EndGameMessage extends MessageToSender {
    private Long firstPlayerId;

    private Long secondPLayerId;

    public EndGameMessage(Address from, Address to, @NotNull Long playerId, @NotNull  Long secondPlayer) {
        super(from, to);
        this.firstPlayerId = playerId;
        this.secondPLayerId = secondPlayer;
    }

    @Override
    public void exec(SenderMessageToFront senderMessageToFront){
        senderMessageToFront.endGame(firstPlayerId, secondPLayerId);
    }
}
