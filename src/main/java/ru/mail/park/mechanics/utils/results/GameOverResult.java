package ru.mail.park.mechanics.utils.results;

/**
 * Created by victor on 23.12.16.
 */
public class GameOverResult extends Result {
    private Integer winnerId;

    public GameOverResult(Integer status, Integer playerIngameId, Integer winnerId) {
        super(status, playerIngameId, ResultType.GameOver);
        this.winnerId = winnerId;
    }

    public GameOverResult(Integer status, Integer winnerId) {
        super(status);
        this.winnerId = winnerId;
    }

    public Integer getWinnerId() {
        return winnerId;
    }
}
