package vf.client.com.vishwasfarm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amhule on 11/30/2016.
 */
public class VishwasLoginDetails implements Parcelable{

    VishwasProductList mVishwasProductList;
    VishwasMySubscriptionList mVishwasMySubscriptionList;


    TopupProductList mTopupProductList;
    VishwasUser mVishwasUser;

    String mLoginErrorCode;
    String mLoginErrorDisc;

    public VishwasLoginDetails(){

    }

    public VishwasMySubscriptionList getmVishwasMySubscriptionList() {
        return mVishwasMySubscriptionList;
    }

    public void setmVishwasMySubscriptionList(VishwasMySubscriptionList mVishwasMySubscriptionList) {
        this.mVishwasMySubscriptionList = mVishwasMySubscriptionList;
    }
    public VishwasProductList getmVishwasProductList() {
        return mVishwasProductList;
    }

    public void setmVishwasProductList(VishwasProductList mVishwasProductList) {
        this.mVishwasProductList = mVishwasProductList;
    }

    public VishwasUser getmVishwasUser() {
        return mVishwasUser;
    }

    public void setmVishwasUser(VishwasUser mVishwasUser) {
        this.mVishwasUser = mVishwasUser;
    }


    public String getmLoginErrorDisc() {
        return mLoginErrorDisc;
    }

    public void setmLoginErrorDisc(String mLoginErrorDisc) {
        this.mLoginErrorDisc = mLoginErrorDisc;
    }

    public String getmLoginErrorCode() {
        return mLoginErrorCode;
    }

    public void setmLoginErrorCode(String mLoginErrorCode) {
        this.mLoginErrorCode = mLoginErrorCode;
    }


    public TopupProductList getmTopupProductList() {
        return mTopupProductList;
    }

    public void setmTopupProductList(TopupProductList mTopupProductList) {
        this.mTopupProductList = mTopupProductList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mVishwasProductList, flags);
        dest.writeParcelable(mVishwasUser, flags);
        dest.writeParcelable(mVishwasMySubscriptionList, flags);
        dest.writeParcelable(mTopupProductList, flags);

        dest.writeString(mLoginErrorCode);
        dest.writeString(mLoginErrorDisc);
    }

    /*** Here how do I populate my List of Products ***/
    private VishwasLoginDetails(Parcel in) {
        mVishwasProductList = (VishwasProductList) in.readParcelable(VishwasProductList.class.getClassLoader());
        mVishwasUser= (VishwasUser) in.readParcelable(VishwasProductList.class.getClassLoader());
        mVishwasMySubscriptionList= (VishwasMySubscriptionList) in.readParcelable(VishwasMySubscriptionList.class.getClassLoader());
        mTopupProductList= (TopupProductList) in.readParcelable(TopupProductList.class.getClassLoader());
        mLoginErrorCode=in.readString();
        mLoginErrorDisc=in.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public VishwasLoginDetails createFromParcel(Parcel in) {
            return new VishwasLoginDetails(in);
        }

        public VishwasLoginDetails[] newArray(int size) {
            return new VishwasLoginDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }




}
