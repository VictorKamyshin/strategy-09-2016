package ru.mail.park.messageSystem.MessagesToGameMechanics;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.internal.GameMechanicsInNewThread;
import ru.mail.park.messageSystem.Address;

/**
 * Created by victor on 19.12.16.
 */
public class CoinActionMessageToMechanics extends MessageToGameMechanics {
    @NotNull
    private Long playerId;

    @NotNull
    private Integer piratId;

    @NotNull
    private Boolean pickCoin;

    @NotNull
    private Boolean dropCoin;

    public CoinActionMessageToMechanics(Address from, Address to, @NotNull Long playerId, @NotNull Integer piratId, @NotNull Boolean pickCoin, @NotNull Boolean dropCoin) {
        super(from, to);
        this.playerId = playerId;
        this.piratId = piratId;
        this.pickCoin = pickCoin;
        this.dropCoin = dropCoin;
    }

    @Override
    public void exec(GameMechanicsInNewThread gameMechanicsInNewThread){
        gameMechanicsInNewThread.coinAction(piratId, pickCoin, dropCoin, playerId);
    }
}
