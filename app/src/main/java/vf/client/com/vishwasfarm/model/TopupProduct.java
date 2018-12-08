package vf.client.com.vishwasfarm.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amhule on 11/30/2016.
 */
public class TopupProduct implements Parcelable{
    String ProductName;
    String ProductId;
    String ProductType;
    String ProductImage_URL;
    String ProductStockStatus;
    String ProductDescription;
    String ProductUnit;
    String MRP;



    String Min_order_qty;
    String MRP_FromQuantity;
    String MRP_ToQuantity;
    String PriceRangeId;


    public TopupProduct(){
    }

    public TopupProduct(Parcel in) {
        ProductName=in.readString();
         ProductId=in.readString();
         ProductType=in.readString();
         ProductImage_URL=in.readString();
         ProductStockStatus=in.readString();
         ProductDescription=in.readString();
         ProductUnit=in.readString();
        MRP=in.readString();
        Min_order_qty=in.readString();
        MRP_FromQuantity=in.readString();
        MRP_ToQuantity=in.readString();
        PriceRangeId=in.readString();

    }


    public String getMin_order_qty() {
        return Min_order_qty;
    }

    public void setMin_order_qty(String min_order_qty) {
        Min_order_qty = min_order_qty;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public String getProductImage_URL() {
        return ProductImage_URL;
    }

    public void setProductImage_URL(String productImage_URL) {
        ProductImage_URL = productImage_URL;
    }

    public String getProductStockStatus() {
        return ProductStockStatus;
    }

    public void setProductStockStatus(String productStockStatus) {
        ProductStockStatus = productStockStatus;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public String getProductUnit() {
        return ProductUnit;
    }

    public void setProductUnit(String productUnit) {
        ProductUnit = productUnit;
    }



    public String getMRP() {
        return MRP;
    }

    public void setMRP(String MRP) {
        this.MRP = MRP;
    }

    public String getMRP_FromQuantity() {
        return MRP_FromQuantity;
    }

    public void setMRP_FromQuantity(String MRP_FromQuantity) {
        this.MRP_FromQuantity = MRP_FromQuantity;
    }

    public String getMRP_ToQuantity() {
        return MRP_ToQuantity;
    }

    public void setMRP_ToQuantity(String MRP_ToQuantity) {
        this.MRP_ToQuantity = MRP_ToQuantity;
    }

    public String getPriceRangeId() {
        return PriceRangeId;
    }

    public void setPriceRangeId(String priceRangeId) {
        PriceRangeId = priceRangeId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProductName);
        dest.writeString(ProductId);
        dest.writeString(ProductType);
        dest.writeString(ProductImage_URL);
        dest.writeString(ProductStockStatus);
        dest.writeString(ProductDescription);
        dest.writeString(ProductUnit);
        dest.writeString(MRP);
        dest.writeString(Min_order_qty);
        dest.writeString(MRP_FromQuantity);
        dest.writeString(MRP_ToQuantity);
        dest.writeString(PriceRangeId);
       }

    public static final Creator CREATOR = new Creator() {
        public TopupProduct createFromParcel(Parcel in) {
            return new TopupProduct(in);
        }
        public TopupProduct[] newArray(int size) {
            return new TopupProduct[size];
        }
    };
}
