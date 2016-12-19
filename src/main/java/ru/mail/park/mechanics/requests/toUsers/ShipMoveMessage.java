package ru.mail.park.mechanics.requests.toUsers;

/**
 * Created by victor on 04.12.16.
 */
public class ShipMoveMessage {
    public static final class Request {
        private Boolean isActive;

        private String movements;

        public Boolean getActive() {
            return isActive;
        }

        public void setActive(Boolean active) {
            isActive = active;
        }

        public String getMovements() {
            return movements;
        }

        public void setMovements(String movements) {
            this.movements = movements;
        }
    }
}
