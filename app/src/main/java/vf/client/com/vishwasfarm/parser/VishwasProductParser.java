package vf.client.com.vishwasfarm.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vf.client.com.vishwasfarm.model.VishwasProduct;
import vf.client.com.vishwasfarm.model.VishwasProductList;

/**
 * Created by amhule on 11/30/2016.
 */
public class VishwasProductParser {

    private VishwasProduct mVishwasProduct;
    private VishwasProductList mVishwasProductList;
    private String mResponse;

    public VishwasProductParser(String StringToParse){
        mVishwasProduct=new VishwasProduct();
        mVishwasProductList=new VishwasProductList();
        mResponse=StringToParse;
            }

    public VishwasProductList parseVishwasProduct(){
        try {
            JSONArray jsonarray = new JSONArray(mResponse);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject VishwasProductbject = jsonarray.getJSONObject(i);
                String prod_id = VishwasProductbject.getString("prod_id");
                String prod_name = VishwasProductbject.getString("prod_name");
                String prod_type = VishwasProductbject.getString("prod_type");
                String prod_image = VishwasProductbject.getString("prod_image");
                String flg_stk_status = VishwasProductbject.getString("flg_stk_status");
                String prod_desc = VishwasProductbject.getString("prod_desc");
                String MRP_unit = VishwasProductbject.getString("MRP_unit");
                String MRP_INR = VishwasProductbject.getString("MRP_INR");
                String MRP_from_qty = VishwasProductbject.getString("MRP_from_qty");
                String MRP_to_qty = VishwasProductbject.getString("MRP_to_qty");
                String MRP_range_id = VishwasProductbject.getString("price_range_id");

                mVishwasProduct.setProductId(prod_id);
                mVishwasProduct.setProductName(prod_name);
                mVishwasProduct.setProductDescription(prod_desc);
                mVishwasProduct.setProductImage_URL(prod_image);
                mVishwasProduct.setProductStockStatus(flg_stk_status);
                mVishwasProduct.setProductType(prod_type);
                mVishwasProduct.setProductUnit(MRP_unit);
                mVishwasProduct.setMRP(MRP_INR);
                mVishwasProduct.setMRP_FromQuantity(MRP_from_qty);
                mVishwasProduct.setMRP_ToQuantity(MRP_to_qty);
                mVishwasProduct.setPriceRangeId(MRP_range_id);

                mVishwasProductList.addVishwasProduct(mVishwasProduct);
            }
            return mVishwasProductList;
        }
       catch (JSONException e) {
            e.printStackTrace();
           return null;
        }

    }
}
