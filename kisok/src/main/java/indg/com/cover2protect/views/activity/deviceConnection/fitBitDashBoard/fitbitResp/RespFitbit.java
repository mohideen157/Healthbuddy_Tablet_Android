
package indg.com.cover2protect.views.activity.deviceConnection.fitBitDashBoard.fitbitResp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RespFitbit implements Serializable
{

    @SerializedName("user")
    @Expose
    private User user;
    private final static long serialVersionUID = -7091156774883718325L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RespFitbit() {
    }

    /**
     * 
     * @param user
     */
    public RespFitbit(User user) {
        super();
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
