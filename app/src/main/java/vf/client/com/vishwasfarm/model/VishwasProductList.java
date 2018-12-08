package vf.client.com.vishwasfarm.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amhule on 11/30/2016.
 */
public class VishwasProductList implements Parcelable{

    List<VishwasProduct> mVishwasProductList;

    public VishwasProductList(){
        mVishwasProductList=new ArrayList();
    }

    public void addVishwasProduct(VishwasProduct lVishwasProduct){
        mVishwasProductList.add(lVishwasProduct);
    }
    public List<VishwasProduct> getmVishwasProductList() {
        return mVishwasProductList;
    }

    public void setmVishwasProductList(ArrayList<VishwasProduct> mVishwasProductList) {
        this.mVishwasProductList = mVishwasProductList;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mVishwasProductList);
    }

    /*** Here how do I populate my List of Products ***/
    private VishwasProductList(Parcel in) {
        mVishwasProductList = new ArrayList<VishwasProduct>();
        in.readTypedList(mVishwasProductList, VishwasProduct.CREATOR);
    }

    public static final Creator CREATOR = new Creator() {
        public VishwasProductList createFromParcel(Parcel in) {
            return new VishwasProductList(in);
        }

        public VishwasProductList[] newArray(int size) {
            return new VishwasProductList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }




}
