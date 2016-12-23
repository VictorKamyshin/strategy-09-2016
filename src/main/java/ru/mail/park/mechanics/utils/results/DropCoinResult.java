package ru.mail.park.mechanics.utils.results;

/**
 * Created by victor on 19.12.16.
 */
public class DropCoinResult extends  Result{
    private Integer piratId;

    public DropCoinResult(Integer status, Integer playerIngameId, Integer piratId) {
        super(status, playerIngameId, ResultType.PickCoin);
        this.piratId = piratId;
    }

    public DropCoinResult(Integer status) {
        super(status);
    }
}
