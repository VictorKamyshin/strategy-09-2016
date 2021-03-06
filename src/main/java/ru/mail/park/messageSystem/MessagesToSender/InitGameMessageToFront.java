package ru.mail.park.messageSystem.MessagesToSender;

import ru.mail.park.mechanics.internal.SenderMessageToFront;
import ru.mail.park.messageSystem.Address;
import ru.mail.park.model.UserProfile;

import java.util.List;

/**
 * Created by victor on 13.12.16.
 */
public class InitGameMessageToFront extends MessageToSender {
    private List<Integer> boardMap;

    private UserProfile firstPlayerId;

    private UserProfile secondPlayerId;

    public InitGameMessageToFront(Address from, Address to, List<Integer> boardMap, UserProfile firstPlayerId, UserProfile secondPlayerId) {
        super(from, to);
        this.boardMap = boardMap;
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
    }

    @Override
    public void exec(SenderMessageToFront senderMessageToFront){
        senderMessageToFront.initGame(boardMap, firstPlayerId, secondPlayerId);
    }
}
