package ru.mail.park.mechanics.utils.results;

/**
 * Created by victor on 19.12.16.
 */
public class PickCoinResult extends Result {
    private final ResultType type = ResultType.PickCoin;

    private Integer piratId;

    public PickCoinResult(Integer status, Integer playerIngameId, Integer piratId) {
        super(status, playerIngameId);
        this.piratId = piratId;
    }

    public PickCoinResult(Integer status) {
        super(status);
    }

}
