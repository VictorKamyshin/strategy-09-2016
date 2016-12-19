package ru.mail.park.mechanics.requests.toUsers;

/**
 * Created by victor on 19.12.16.
 */
public class CoinActionMessage {
    public static final class Request {
        private String results;

        public String getMovement() {
            return results;
        }

        public void setMovement(String movement) {
            this.results = movement;
        }
    }
}
