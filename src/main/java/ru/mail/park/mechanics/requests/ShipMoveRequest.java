package ru.mail.park.mechanics.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.mail.park.mechanics.game.GameBoard;

public class ShipMoveRequest { //то, что придет к нам с клиента
    //запрос на передвижение корабля
    private Integer targetCellX; //пока не используется, на фронте еще нет кораблей
    private Integer targetCellY;

    @JsonCreator
    public ShipMoveRequest(@JsonProperty("targetCellIndex") Integer targerCellIndex){
        this.targetCellX = targerCellIndex % GameBoard.BOARDHIGHT;
        this.targetCellY = targerCellIndex / GameBoard.BOARDHIGHT ;
    }

    public Integer getTargetCellX() {
        return targetCellX;
    }

    public Integer getTargetCellY() {
        return targetCellY;
    }
}
