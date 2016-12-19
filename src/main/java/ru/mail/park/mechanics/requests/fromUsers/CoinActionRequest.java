package ru.mail.park.mechanics.requests.fromUsers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by victor on 19.12.16.
 */
public class CoinActionRequest {
    private Integer piratId;
    private Boolean pickCoin;
    private Boolean dropCoin;

    @JsonCreator
    public CoinActionRequest(@JsonProperty("piratId") Integer piratId,
                             @JsonProperty(value = "pickCoin") Boolean pickCoin,
                             @JsonProperty("dropCoin") Boolean dropCoin){
        this.piratId = piratId;
        this.pickCoin = pickCoin!=null?pickCoin:false;
        this.dropCoin = dropCoin!=null?dropCoin:false;
    }

    public Integer getPiratId() {
        return piratId;
    }

    public Boolean getPickCoin() {
        return pickCoin;
    }

    public Boolean getDropCoin() {
        return dropCoin;
    }
}
