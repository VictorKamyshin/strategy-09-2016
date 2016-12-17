package ru.mail.park.mechanics.utils.results;

import ru.mail.park.mechanics.game.CoordPair;
import ru.mail.park.mechanics.game.GameBoard;

/**
 * Created by victor on 17.12.16.
 */
public class ShipMovementResult extends Result {
    private final ResultType type = ResultType.ShipMove;

    private Integer targetCellIndex;

    public ShipMovementResult(Integer playerId, Integer piratId, CoordPair targetCell){
        super(0,playerId);
        this.targetCellIndex = GameBoard.BOARDWIGHT * targetCell.getY() + targetCell.getX();
    }

    public ShipMovementResult(Integer status){
        super(status);
    }

    public Integer getStatus() {
        return super.status;
    }

    public void setStatus(Integer status) {
        super.status = status;
    }

}
