package ru.mail.park.mechanics.game;

public class CoordPair {
    private Integer x;
    private Integer y;

    public static CoordPair sum(CoordPair firstPair, CoordPair secondPair){
        return new CoordPair(firstPair.x+ secondPair.x, firstPair.y + secondPair.y);
    }
    public static Boolean equals(CoordPair firstPair, CoordPair secondPair){
        return firstPair.x.equals(secondPair.x)&&firstPair.y.equals(secondPair.y);
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof CoordPair))
            return false;
        final CoordPair tempPair = (CoordPair) obj;
        return tempPair.x.equals(x)&&tempPair.y.equals(y);
    }

    public CoordPair(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
