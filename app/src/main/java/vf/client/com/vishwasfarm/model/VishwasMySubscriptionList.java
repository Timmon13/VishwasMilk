package vf.client.com.vishwasfarm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amhule on 11/30/2016.
 */
public class VishwasMySubscriptionList implements Parcelable{

    List<VishwasMySubscription> mVishwasMySubscriptionList;

    public VishwasMySubscriptionList(){
        mVishwasMySubscriptionList =new ArrayList();
    }

    public void addVishwasProduct(VishwasMySubscription lVishwasMySubscription){
        mVishwasMySubscriptionList.add(lVishwasMySubscription);
    }
    public List<VishwasMySubscription> getmVishwasMySubscriptionList() {
        return mVishwasMySubscriptionList;
    }

    public void setmVishwasProductList(ArrayList<VishwasMySubscription> mVishwasProductList) {
        this.mVishwasMySubscriptionList = mVishwasProductList;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mVishwasMySubscriptionList);
    }

    /*** Here how do I populate my List of Products ***/
    private VishwasMySubscriptionList(Parcel in) {
        mVishwasMySubscriptionList = new ArrayList<VishwasMySubscription>();
        in.readTypedList(mVishwasMySubscriptionList, VishwasMySubscription.CREATOR);
    }

    public static final Creator CREATOR = new Creator() {
        public VishwasMySubscriptionList createFromParcel(Parcel in) {
            return new VishwasMySubscriptionList(in);
        }

        public VishwasMySubscriptionList[] newArray(int size) {
            return new VishwasMySubscriptionList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }




}
