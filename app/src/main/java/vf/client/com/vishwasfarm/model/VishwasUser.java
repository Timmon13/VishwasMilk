package vf.client.com.vishwasfarm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amhule on 12/5/2016.
 */
public class VishwasUser implements Parcelable {

    String mUserFirstName;
    String mUserLastName;
    String mUserAddress;
    String mUserEmail;
    String mUserMobile;
    String mUserImageURL;

    String mCustId;
    String mAdvanceAmount;
    String mFlagSMSAlert;
    String mFlagEmailAlert;
    String mCustPassword;
    String mCustStatus;
    String mCustLoggedState;
    String mCustType;
    String mCustRegisterDate;
    String mFlagGoogle;



    public VishwasUser() {

    }

    public String getmUserFirstName() {
        return mUserFirstName;
    }

    public void setmUserFirstName(String mUserFirstName) {
        this.mUserFirstName = mUserFirstName;
    }

    public String getmUserLastName() {
        return mUserLastName;
    }

    public void setmUserLastName(String mUserLastName) {
        this.mUserLastName = mUserLastName;
    }

    public String getmUserAddress() {
        return mUserAddress;
    }

    public void setmUserAddress(String mUserAddress) {
        this.mUserAddress = mUserAddress;
    }

    public String getmUserEmail() {
        return mUserEmail;
    }

    public void setmUserEmail(String mUserEmail) {
        this.mUserEmail = mUserEmail;
    }

    public String getmUserMobile() {
        return mUserMobile;
    }

    public void setmUserMobile(String mUserMobile) {
        this.mUserMobile = mUserMobile;
    }

    public String getmUserImageURL() {
        return mUserImageURL;
    }

    public void setmUserImageURL(String mUserImageURL) {
        this.mUserImageURL = mUserImageURL;
    }

    public String getmCustId() {
        return mCustId;
    }

    public void setmCustId(String mCustId) {
        this.mCustId = mCustId;
    }

    public String getmAdvanceAmount() {
        return mAdvanceAmount;
    }

    public void setmAdvanceAmount(String mAdvanceAmount) {
        this.mAdvanceAmount = mAdvanceAmount;
    }

    public String getmFlagSMSAlert() {
        return mFlagSMSAlert;
    }

    public void setmFlagSMSAlert(String mFlagSMSAlert) {
        this.mFlagSMSAlert = mFlagSMSAlert;
    }

    public String getmFlagEmailAlert() {
        return mFlagEmailAlert;
    }

    public void setmFlagEmailAlert(String mFlagEmailAlert) {
        this.mFlagEmailAlert = mFlagEmailAlert;
    }

    public String getmCustStatus() {
        return mCustStatus;
    }

    public void setmCustStatus(String mCustStatus) {
        this.mCustStatus = mCustStatus;
    }

    public String getmCustLoggedState() {
        return mCustLoggedState;
    }

    public void setmCustLoggedState(String mCustLoggedState) {
        this.mCustLoggedState = mCustLoggedState;
    }

    public String getmCustType() {
        return mCustType;
    }

    public void setmCustType(String mCustType) {
        this.mCustType = mCustType;
    }

    public String getmCustRegisterDate() {
        return mCustRegisterDate;
    }

    public void setmCustRegisterDate(String mCustRegisterDate) {
        this.mCustRegisterDate = mCustRegisterDate;
    }

    public String getmFlagGoogle() {
        return mFlagGoogle;
    }

    public void setmFlagGoogle(String mFlagGoogle) {
        this.mFlagGoogle = mFlagGoogle;
    }

    public String getmCustPassword() {
        return mCustPassword;
    }

    public void setmCustPassword(String mCustPassword) {
        this.mCustPassword = mCustPassword;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mUserFirstName);
        dest.writeString(mUserLastName);
        dest.writeString(mUserAddress);
        dest.writeString(mUserEmail);
        dest.writeString(mUserMobile);
        dest.writeString(mUserImageURL);
        dest.writeString(mCustId);
        dest.writeString(mAdvanceAmount);
        dest.writeString(mFlagSMSAlert);
        dest.writeString(mFlagEmailAlert);
        dest.writeString(mCustPassword);
        dest.writeString(mCustStatus);
        dest.writeString(mCustLoggedState);
        dest.writeString(mCustType);
        dest.writeString(mCustRegisterDate);
        dest.writeString(mFlagGoogle);


    }

    public VishwasUser(Parcel in){
        mUserFirstName=in.readString();
        mUserLastName=in.readString();
        mUserAddress=in.readString();
        mUserEmail=in.readString();
        mUserMobile=in.readString();
        mUserImageURL=in.readString();
        mCustId=in.readString();
        mAdvanceAmount=in.readString();
        mFlagSMSAlert=in.readString();
        mFlagEmailAlert=in.readString();
        mCustPassword=in.readString();
        mCustStatus=in.readString();
        mCustLoggedState=in.readString();
        mCustType=in.readString();
        mCustRegisterDate=in.readString();
        mFlagGoogle=in.readString();
    }

    public static final Creator<VishwasUser> CREATOR = new Creator<VishwasUser>() {

        @Override
        public VishwasUser createFromParcel(Parcel source) {
            return new VishwasUser(source);
        }

        @Override
        public VishwasUser[] newArray(int size) {
            return new VishwasUser[size];
        }
    };
}
