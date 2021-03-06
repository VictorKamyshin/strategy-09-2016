package ru.mail.park.mechanics.game;

public class Pirat {
    private Integer id;
    private CoordPair location;
    private Boolean isAlive;
    private Boolean haveCoin;

    public Pirat(Integer id, CoordPair starterLocation){
        this.id = id;
        this.location = starterLocation;
        this.isAlive = true;
        this.haveCoin = false;
    }

    public CoordPair getLocation() {
        return location;
    }

    public void setLocation(CoordPair location) {
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getHaveCoin() {
        return haveCoin;
    }

    public void setHaveCoin(Boolean haveCoin) {
        this.haveCoin = haveCoin;
    }
}
