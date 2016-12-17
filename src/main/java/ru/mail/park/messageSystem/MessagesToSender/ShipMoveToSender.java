package ru.mail.park.messageSystem.MessagesToSender;

import ru.mail.park.mechanics.internal.SenderMessageToFront;
import ru.mail.park.mechanics.utils.results.Result;
import ru.mail.park.messageSystem.Abonent;
import ru.mail.park.messageSystem.Address;

import java.util.List;

/**
 * Created by victor on 17.12.16.
 */
public class ShipMoveToSender extends MessageToSender {

    private Long activePlayerId;

    private Long passivePlayerId;

    private List<Result> results;

    public ShipMoveToSender(Address from, Address to, Long activePlayerId, Long passivePlayerId, List<Result> results) {
        super(from, to);
        this.activePlayerId = activePlayerId;
        this.passivePlayerId = passivePlayerId;
        this.results = results;
    }

    @Override
    public void exec(Abonent abonent) {
        if(abonent instanceof SenderMessageToFront) {
            exec((SenderMessageToFront) abonent);
        }
    }

    void exec(SenderMessageToFront senderMessageToFront){
        senderMessageToFront.sendShipMove(results, activePlayerId, passivePlayerId);
    };
}
