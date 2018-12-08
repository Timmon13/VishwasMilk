package vf.client.com.vishwasfarm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amhule on 11/30/2016.
 */
public class TopupProductList implements Parcelable{

    List<TopupProduct> mVishwasProductList;

    public TopupProductList(){
        mVishwasProductList=new ArrayList();
    }

    public void addVishwasProduct(TopupProduct lVishwasProduct){
        mVishwasProductList.add(lVishwasProduct);
    }
    public List<TopupProduct> getmVishwasProductList() {
        return mVishwasProductList;
    }

    public void setmVishwasProductList(ArrayList<TopupProduct> mVishwasProductList) {
        this.mVishwasProductList = mVishwasProductList;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mVishwasProductList);
    }

    /*** Here how do I populate my List of Products ***/
    private TopupProductList(Parcel in) {
        mVishwasProductList = new ArrayList<TopupProduct>();
        in.readTypedList(mVishwasProductList, TopupProduct.CREATOR);
    }

    public static final Creator CREATOR = new Creator() {
        public TopupProductList createFromParcel(Parcel in) {
            return new TopupProductList(in);
        }

        public TopupProductList[] newArray(int size) {
            return new TopupProductList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }




}
