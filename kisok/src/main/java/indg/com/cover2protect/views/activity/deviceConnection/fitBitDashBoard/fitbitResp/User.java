
package indg.com.cover2protect.views.activity.deviceConnection.fitBitDashBoard.fitbitResp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable
{

    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("ambassador")
    @Expose
    private Boolean ambassador;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("avatar150")
    @Expose
    private String avatar150;
    @SerializedName("avatar640")
    @Expose
    private String avatar640;
    @SerializedName("averageDailySteps")
    @Expose
    private Integer averageDailySteps;
    @SerializedName("clockTimeDisplayFormat")
    @Expose
    private String clockTimeDisplayFormat;
    @SerializedName("corporate")
    @Expose
    private Boolean corporate;
    @SerializedName("corporateAdmin")
    @Expose
    private Boolean corporateAdmin;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("displayNameSetting")
    @Expose
    private String displayNameSetting;
    @SerializedName("distanceUnit")
    @Expose
    private String distanceUnit;
    @SerializedName("encodedId")
    @Expose
    private String encodedId;
    @SerializedName("familyGuidanceEnabled")
    @Expose
    private Boolean familyGuidanceEnabled;
    @SerializedName("features")
    @Expose
    private Features features;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("foodsLocale")
    @Expose
    private String foodsLocale;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("glucoseUnit")
    @Expose
    private String glucoseUnit;
    @SerializedName("height")
    @Expose
    private Double height;
    @SerializedName("heightUnit")
    @Expose
    private String heightUnit;
    @SerializedName("isChild")
    @Expose
    private Boolean isChild;
    @SerializedName("isCoach")
    @Expose
    private Boolean isCoach;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("locale")
    @Expose
    private String locale;
    @SerializedName("memberSince")
    @Expose
    private String memberSince;
    @SerializedName("mfaEnabled")
    @Expose
    private Boolean mfaEnabled;
    @SerializedName("offsetFromUTCMillis")
    @Expose
    private Integer offsetFromUTCMillis;
    @SerializedName("startDayOfWeek")
    @Expose
    private String startDayOfWeek;
    @SerializedName("strideLengthRunning")
    @Expose
    private Double strideLengthRunning;
    @SerializedName("strideLengthRunningType")
    @Expose
    private String strideLengthRunningType;
    @SerializedName("strideLengthWalking")
    @Expose
    private Double strideLengthWalking;
    @SerializedName("strideLengthWalkingType")
    @Expose
    private String strideLengthWalkingType;
    @SerializedName("swimUnit")
    @Expose
    private String swimUnit;
    @SerializedName("timezone")
    @Expose
    private String timezone;
    @SerializedName("topBadges")
    @Expose
    private List<Object> topBadges = new ArrayList<Object>();
    @SerializedName("waterUnit")
    @Expose
    private String waterUnit;
    @SerializedName("waterUnitName")
    @Expose
    private String waterUnitName;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("weightUnit")
    @Expose
    private String weightUnit;
    private final static long serialVersionUID = -7145497539237721817L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public User() {
    }

    /**
     * 
     * @param distanceUnit
     * @param lastName
     * @param gender
     * @param displayName
     * @param timezone
     * @param waterUnit
     * @param avatar640
     * @param clockTimeDisplayFormat
     * @param displayNameSetting
     * @param locale
     * @param offsetFromUTCMillis
     * @param foodsLocale
     * @param strideLengthRunningType
     * @param features
     * @param memberSince
     * @param waterUnitName
     * @param mfaEnabled
     * @param heightUnit
     * @param isChild
     * @param height
     * @param isCoach
     * @param strideLengthWalking
     * @param avatar150
     * @param topBadges
     * @param familyGuidanceEnabled
     * @param strideLengthRunning
     * @param fullName
     * @param weight
     * @param dateOfBirth
     * @param avatar
     * @param encodedId
     * @param swimUnit
     * @param startDayOfWeek
     * @param firstName
     * @param glucoseUnit
     * @param corporate
     * @param ambassador
     * @param strideLengthWalkingType
     * @param corporateAdmin
     * @param averageDailySteps
     * @param age
     * @param weightUnit
     */
    public User(Integer age, Boolean ambassador, String avatar, String avatar150, String avatar640, Integer averageDailySteps, String clockTimeDisplayFormat, Boolean corporate, Boolean corporateAdmin, String dateOfBirth, String displayName, String displayNameSetting, String distanceUnit, String encodedId, Boolean familyGuidanceEnabled, Features features, String firstName, String foodsLocale, String fullName, String gender, String glucoseUnit, Double height, String heightUnit, Boolean isChild, Boolean isCoach, String lastName, String locale, String memberSince, Boolean mfaEnabled, Integer offsetFromUTCMillis, String startDayOfWeek, Double strideLengthRunning, String strideLengthRunningType, Double strideLengthWalking, String strideLengthWalkingType, String swimUnit, String timezone, List<Object> topBadges, String waterUnit, String waterUnitName, Integer weight, String weightUnit) {
        super();
        this.age = age;
        this.ambassador = ambassador;
        this.avatar = avatar;
        this.avatar150 = avatar150;
        this.avatar640 = avatar640;
        this.averageDailySteps = averageDailySteps;
        this.clockTimeDisplayFormat = clockTimeDisplayFormat;
        this.corporate = corporate;
        this.corporateAdmin = corporateAdmin;
        this.dateOfBirth = dateOfBirth;
        this.displayName = displayName;
        this.displayNameSetting = displayNameSetting;
        this.distanceUnit = distanceUnit;
        this.encodedId = encodedId;
        this.familyGuidanceEnabled = familyGuidanceEnabled;
        this.features = features;
        this.firstName = firstName;
        this.foodsLocale = foodsLocale;
        this.fullName = fullName;
        this.gender = gender;
        this.glucoseUnit = glucoseUnit;
        this.height = height;
        this.heightUnit = heightUnit;
        this.isChild = isChild;
        this.isCoach = isCoach;
        this.lastName = lastName;
        this.locale = locale;
        this.memberSince = memberSince;
        this.mfaEnabled = mfaEnabled;
        this.offsetFromUTCMillis = offsetFromUTCMillis;
        this.startDayOfWeek = startDayOfWeek;
        this.strideLengthRunning = strideLengthRunning;
        this.strideLengthRunningType = strideLengthRunningType;
        this.strideLengthWalking = strideLengthWalking;
        this.strideLengthWalkingType = strideLengthWalkingType;
        this.swimUnit = swimUnit;
        this.timezone = timezone;
        this.topBadges = topBadges;
        this.waterUnit = waterUnit;
        this.waterUnitName = waterUnitName;
        this.weight = weight;
        this.weightUnit = weightUnit;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getAmbassador() {
        return ambassador;
    }

    public void setAmbassador(Boolean ambassador) {
        this.ambassador = ambassador;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar150() {
        return avatar150;
    }

    public void setAvatar150(String avatar150) {
        this.avatar150 = avatar150;
    }

    public String getAvatar640() {
        return avatar640;
    }

    public void setAvatar640(String avatar640) {
        this.avatar640 = avatar640;
    }

    public Integer getAverageDailySteps() {
        return averageDailySteps;
    }

    public void setAverageDailySteps(Integer averageDailySteps) {
        this.averageDailySteps = averageDailySteps;
    }

    public String getClockTimeDisplayFormat() {
        return clockTimeDisplayFormat;
    }

    public void setClockTimeDisplayFormat(String clockTimeDisplayFormat) {
        this.clockTimeDisplayFormat = clockTimeDisplayFormat;
    }

    public Boolean getCorporate() {
        return corporate;
    }

    public void setCorporate(Boolean corporate) {
        this.corporate = corporate;
    }

    public Boolean getCorporateAdmin() {
        return corporateAdmin;
    }

    public void setCorporateAdmin(Boolean corporateAdmin) {
        this.corporateAdmin = corporateAdmin;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayNameSetting() {
        return displayNameSetting;
    }

    public void setDisplayNameSetting(String displayNameSetting) {
        this.displayNameSetting = displayNameSetting;
    }

    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(String distanceUnit) {
        this.distanceUnit = distanceUnit;
    }

    public String getEncodedId() {
        return encodedId;
    }

    public void setEncodedId(String encodedId) {
        this.encodedId = encodedId;
    }

    public Boolean getFamilyGuidanceEnabled() {
        return familyGuidanceEnabled;
    }

    public void setFamilyGuidanceEnabled(Boolean familyGuidanceEnabled) {
        this.familyGuidanceEnabled = familyGuidanceEnabled;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFoodsLocale() {
        return foodsLocale;
    }

    public void setFoodsLocale(String foodsLocale) {
        this.foodsLocale = foodsLocale;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGlucoseUnit() {
        return glucoseUnit;
    }

    public void setGlucoseUnit(String glucoseUnit) {
        this.glucoseUnit = glucoseUnit;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getHeightUnit() {
        return heightUnit;
    }

    public void setHeightUnit(String heightUnit) {
        this.heightUnit = heightUnit;
    }

    public Boolean getIsChild() {
        return isChild;
    }

    public void setIsChild(Boolean isChild) {
        this.isChild = isChild;
    }

    public Boolean getIsCoach() {
        return isCoach;
    }

    public void setIsCoach(Boolean isCoach) {
        this.isCoach = isCoach;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(String memberSince) {
        this.memberSince = memberSince;
    }

    public Boolean getMfaEnabled() {
        return mfaEnabled;
    }

    public void setMfaEnabled(Boolean mfaEnabled) {
        this.mfaEnabled = mfaEnabled;
    }

    public Integer getOffsetFromUTCMillis() {
        return offsetFromUTCMillis;
    }

    public void setOffsetFromUTCMillis(Integer offsetFromUTCMillis) {
        this.offsetFromUTCMillis = offsetFromUTCMillis;
    }

    public String getStartDayOfWeek() {
        return startDayOfWeek;
    }

    public void setStartDayOfWeek(String startDayOfWeek) {
        this.startDayOfWeek = startDayOfWeek;
    }

    public Double getStrideLengthRunning() {
        return strideLengthRunning;
    }

    public void setStrideLengthRunning(Double strideLengthRunning) {
        this.strideLengthRunning = strideLengthRunning;
    }

    public String getStrideLengthRunningType() {
        return strideLengthRunningType;
    }

    public void setStrideLengthRunningType(String strideLengthRunningType) {
        this.strideLengthRunningType = strideLengthRunningType;
    }

    public Double getStrideLengthWalking() {
        return strideLengthWalking;
    }

    public void setStrideLengthWalking(Double strideLengthWalking) {
        this.strideLengthWalking = strideLengthWalking;
    }

    public String getStrideLengthWalkingType() {
        return strideLengthWalkingType;
    }

    public void setStrideLengthWalkingType(String strideLengthWalkingType) {
        this.strideLengthWalkingType = strideLengthWalkingType;
    }

    public String getSwimUnit() {
        return swimUnit;
    }

    public void setSwimUnit(String swimUnit) {
        this.swimUnit = swimUnit;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public List<Object> getTopBadges() {
        return topBadges;
    }

    public void setTopBadges(List<Object> topBadges) {
        this.topBadges = topBadges;
    }

    public String getWaterUnit() {
        return waterUnit;
    }

    public void setWaterUnit(String waterUnit) {
        this.waterUnit = waterUnit;
    }

    public String getWaterUnitName() {
        return waterUnitName;
    }

    public void setWaterUnitName(String waterUnitName) {
        this.waterUnitName = waterUnitName;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

}
