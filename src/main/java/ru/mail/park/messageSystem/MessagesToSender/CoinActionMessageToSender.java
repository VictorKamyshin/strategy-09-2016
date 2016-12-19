package ru.mail.park.messageSystem.MessagesToSender;

import ru.mail.park.mechanics.internal.SenderMessageToFront;
import ru.mail.park.mechanics.utils.results.Result;
import ru.mail.park.messageSystem.Address;

import java.util.List;

/**
 * Created by victor on 19.12.16.
 */
public class CoinActionMessageToSender extends MessageToSender {
    private List<Result> coinActionResults;

    private Long activePlayerId;

    private Long passivePLayerId;

    public CoinActionMessageToSender(Address from, Address to, List<Result> coinActionResults, Long activePlayerId, Long passivePLayerId) {
        super(from, to);
        this.coinActionResults = coinActionResults;
        this.activePlayerId = activePlayerId;
        this.passivePLayerId = passivePLayerId;
    }

    @Override
    public void exec(SenderMessageToFront senderMessageToFront){
        senderMessageToFront.sendCoinAction(coinActionResults, activePlayerId, passivePLayerId);
    }
}
