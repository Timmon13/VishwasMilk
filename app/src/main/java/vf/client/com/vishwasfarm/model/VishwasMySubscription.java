package vf.client.com.vishwasfarm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amhule on 11/30/2016.
 */
public class VishwasMySubscription implements Parcelable{


    String mSubs_id;
    String mProd_id;

    String mProd_name;
    String mProd_image;


    String mProd_price;

    String mSubs_qty;
    String mSubs_frq;
    String mSubs_start_date;
    String mSub_time_slot;
    String mSub_status;
    String mSub_end_date;
    String mSub_price_range_id;
    String mSub_reg_date;
    String mService_charge;

    public String getService_charge() {
        return mService_charge;
    }

    public void setService_charge(String service_charge) {
        this.mService_charge = service_charge;
    }



    public VishwasMySubscription(){
    }



    public String getmProd_price() {
        return mProd_price;
    }

    public void setmProd_price(String mProd_price) {
        this.mProd_price = mProd_price;
    }

    public String getmProd_name() {
        return mProd_name;
    }

    public void setmProd_name(String mProd_name) {
        this.mProd_name = mProd_name;
    }

    public String getmProd_url() {
        return mProd_image;
    }

    public void setmProd_url(String mProd_url) {
        this.mProd_image = mProd_url;
    }


    public String getmSubs_id() {
        return mSubs_id;
    }

    public void setmSubs_id(String mSubs_id) {
        this.mSubs_id = mSubs_id;
    }

    public String getmProd_id() {
        return mProd_id;
    }

    public void setmProd_id(String mProd_id) {
        this.mProd_id = mProd_id;
    }

    public String getmSubs_qty() {
        return mSubs_qty;
    }

    public void setmSubs_qty(String mSubs_qty) {
        this.mSubs_qty = mSubs_qty;
    }

    public String getmSubs_frq() {
        return mSubs_frq;
    }

    public void setmSubs_frq(String mSubs_frq) {
        this.mSubs_frq = mSubs_frq;
    }

    public String getmSubs_start_date() {
        return mSubs_start_date;
    }

    public void setmSubs_start_date(String mSubs_start_date) {
        this.mSubs_start_date = mSubs_start_date;
    }

    public String getmSub_time_slot() {
        return mSub_time_slot;
    }

    public void setmSub_time_slot(String mSub_time_slot) {
        this.mSub_time_slot = mSub_time_slot;
    }

    public String getmSub_status() {
        return mSub_status;
    }

    public void setmSub_status(String mSub_status) {
        this.mSub_status = mSub_status;
    }

    public String getmSub_end_date() {
        return mSub_end_date;
    }

    public void setmSub_end_date(String mSub_end_date) {
        this.mSub_end_date = mSub_end_date;
    }

    public String getmSub_price_range_id() {
        return mSub_price_range_id;
    }

    public void setmSub_price_range_id(String mSub_price_range_id) {
        this.mSub_price_range_id = mSub_price_range_id;
    }

    public String getmSub_reg_date() {
        return mSub_reg_date;
    }

    public void setmSub_reg_date(String mSub_reg_date) {
        this.mSub_reg_date = mSub_reg_date;
    }
    public VishwasMySubscription(Parcel in) {
        mSubs_id=in.readString();
        mProd_id=in.readString();
        mProd_name=in.readString();
        mProd_image=in.readString();
        mProd_price=in.readString();
        mSubs_qty=in.readString();
        mSubs_frq=in.readString();
        mSubs_start_date=in.readString();
        mSub_time_slot=in.readString();
        mSub_status=in.readString();
        mSub_end_date=in.readString();
        mSub_price_range_id=in.readString();
        mSub_reg_date=in.readString();
        mService_charge=in.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSubs_id);
        dest.writeString(mProd_id);
        dest.writeString(mProd_name);
        dest.writeString(mProd_image);
        dest.writeString(mProd_price);
        dest.writeString(mSubs_qty);
        dest.writeString(mSubs_frq);
        dest.writeString(mSubs_start_date);
        dest.writeString(mSub_time_slot);
        dest.writeString(mSub_status);
        dest.writeString(mSub_end_date);
        dest.writeString(mSub_price_range_id);
        dest.writeString(mSub_reg_date);
        dest.writeString(mService_charge);
       }

    public static final Creator CREATOR = new Creator() {
        public VishwasMySubscription createFromParcel(Parcel in) {
            return new VishwasMySubscription(in);
        }
        public VishwasMySubscription[] newArray(int size) {
            return new VishwasMySubscription[size];
        }
    };
}
