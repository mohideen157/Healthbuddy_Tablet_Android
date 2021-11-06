
package indg.com.cover2protect.views.activity.deviceConnection.fitBitDashBoard.fitbitResp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Features implements Serializable
{

    @SerializedName("exerciseGoal")
    @Expose
    private Boolean exerciseGoal;
    private final static long serialVersionUID = 5581425168568017302L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Features() {
    }

    /**
     * 
     * @param exerciseGoal
     */
    public Features(Boolean exerciseGoal) {
        super();
        this.exerciseGoal = exerciseGoal;
    }

    public Boolean getExerciseGoal() {
        return exerciseGoal;
    }

    public void setExerciseGoal(Boolean exerciseGoal) {
        this.exerciseGoal = exerciseGoal;
    }

}
