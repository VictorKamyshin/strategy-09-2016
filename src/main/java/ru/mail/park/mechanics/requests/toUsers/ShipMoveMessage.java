package ru.mail.park.mechanics.requests.toUsers;

/**
 * Created by victor on 04.12.16.
 */
public class ShipMoveMessage {
    public static final class Request {
        private Integer playerId;
        private Integer targetCellIndex;

        private String movements;

        public Integer getPlayerId() {
            return playerId;
        }

        public void setPlayerId(Integer playerId) {
            this.playerId = playerId;
        }

        public Integer getTargetCellIndex() {
            return targetCellIndex;
        }

        public void setTargetCellIndex(Integer targetCellIndex) {
            this.targetCellIndex = targetCellIndex;
        }

        public String getMovements() {
            return movements;
        }

        public void setMovements(String movements) {
            this.movements = movements;
        }
    }
}
