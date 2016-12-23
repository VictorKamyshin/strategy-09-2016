package ru.mail.park.mechanics.requests.toUsers;

/**
 * Created by victor on 23.12.16.
 */
public class GameOverMessage {
    public static final class Request {
        Integer winner;

        public Integer getWinner() {
            return winner;
        }

        public void setWinner(Integer winner) {
            this.winner = winner;
        }
    }
}
