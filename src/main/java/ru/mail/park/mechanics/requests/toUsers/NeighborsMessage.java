package ru.mail.park.mechanics.requests.toUsers;

import java.util.List;

//это уже сообщение для юзера с окрестностями клекти, о которой он спрашивал
public class NeighborsMessage { // не уверен в названии
    public static class Request{ //так-то это скорее response, чем request
        private List<Integer> neighbors;

        public List<Integer> getNeighbors() {
            return neighbors;
        }

        public void setNeighbors(List<Integer> neighbors) {
            this.neighbors = neighbors;
        }
    }
}
