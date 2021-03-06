package ru.mail.park.messageSystem.MessagesToGameMechanics;

import org.jetbrains.annotations.NotNull;
import ru.mail.park.mechanics.game.CoordPair;
import ru.mail.park.mechanics.internal.GameMechanicsInNewThread;
import ru.mail.park.messageSystem.Address;

/**
 * Created by victor on 13.12.16.
 */
public class MovePiratMessage extends  MessageToGameMechanics {
    //Integer piratId, CoordPair targetCell, Long playerId
    @NotNull
    private Integer piratId;

    @NotNull
    private CoordPair targetCell;

    @NotNull
    private Long playerId;

    public MovePiratMessage(Address from, Address to, @NotNull Integer piratId, @NotNull CoordPair targetCell, @NotNull Long playerId) {
        super(from, to);
        this.piratId = piratId;
        this.targetCell = targetCell;
        this.playerId = playerId;
    }

    @Override
    public void exec(GameMechanicsInNewThread gameMechanicsInNewThread){
        gameMechanicsInNewThread.movePirat(piratId,targetCell, playerId);
    }
}
