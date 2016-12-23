package ru.mail.park.mechanics.utils.results;

/**
 * Created by victor on 15.12.16.
 */
public abstract class Result {
    public enum ResultType {PiratMove, ShipMove, PickCoin, GameOver};

    protected ResultType type;

    protected Integer status;

    protected Integer playerIngameId;

    public Result(Integer status, Integer playerIngameId, ResultType type) {
        this.type = type;
        this.status = status;
        this.playerIngameId = playerIngameId;
    }

    public Result(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ResultType getType() {
        return type;
    }
}
