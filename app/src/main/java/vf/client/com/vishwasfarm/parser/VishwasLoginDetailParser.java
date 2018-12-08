package vf.client.com.vishwasfarm.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vf.client.com.vishwasfarm.model.TopupProduct;
import vf.client.com.vishwasfarm.model.TopupProductList;
import vf.client.com.vishwasfarm.model.VishwasLoginDetails;
import vf.client.com.vishwasfarm.model.VishwasMySubscription;
import vf.client.com.vishwasfarm.model.VishwasMySubscriptionList;
import vf.client.com.vishwasfarm.model.VishwasProduct;
import vf.client.com.vishwasfarm.model.VishwasProductList;
import vf.client.com.vishwasfarm.model.VishwasUser;

/**
 * Created by amhule on 11/30/2016.
 */
public class VishwasLoginDetailParser {

    private VishwasUser mVishwasUser;

    private VishwasProduct mVishwasProduct;
    private VishwasProductList mVishwasProductList;

    private TopupProduct mTopupProduct;
    private TopupProductList mTopupProductList;

    private VishwasMySubscription mVishwasMySubscription;
    private VishwasMySubscriptionList mVishwasMySubscriptionList;
    VishwasLoginDetails mVishwasLoginDetails;
    private String mResponse;

    public VishwasLoginDetailParser(String StringToParse){

        mVishwasUser=new VishwasUser();

        mVishwasProduct=new VishwasProduct();
        mVishwasProductList=new VishwasProductList();

        mTopupProduct=new TopupProduct();
        mTopupProductList=new TopupProductList();

        mVishwasMySubscription=new VishwasMySubscription();
        mVishwasMySubscriptionList=new VishwasMySubscriptionList();

        mVishwasLoginDetails=new VishwasLoginDetails();
        mResponse=StringToParse;
            }

    public VishwasLoginDetails parceVishwasLoginDetails(){
        try {

            JSONObject jsonObj = new JSONObject(mResponse);

            JSONObject lErrorCheck = jsonObj.getJSONObject("err_dtls");
            String lErrorCode = lErrorCheck.getString("ERR_NUM");
            String lErrorDisc = lErrorCheck.getString("ERR_MSG");
            mVishwasLoginDetails.setmLoginErrorCode(lErrorCode);
            mVishwasLoginDetails.setmLoginErrorDisc(lErrorDisc);
            if(lErrorCode.equals("150") ||lErrorCode.equals("250") || lErrorCode.equals("105") ||lErrorCode.equals("106") )
            {
              //Do nothing
            }
            else if(lErrorCode.equals("0")) {
                JSONArray lUserArray = jsonObj.getJSONArray("cust_dtls");
                JSONObject lUserObject = lUserArray.getJSONObject(0);

                String lCustId = lUserObject.getString("cod_cust");
                String lFName = lUserObject.getString("txt_fname");
                String lLName = lUserObject.getString("txt_lname");
                String lAddress = lUserObject.getString("txt_address");
                String lEmailId = lUserObject.getString("txt_email_id");
                String lMobileNo = lUserObject.getString("mobile_no");
                String lLocation = lUserObject.getString("txt_location");
                String lAdvanceAmount = lUserObject.getString("amt_advance");
                String lFlagSMSAlter = lUserObject.getString("flg_sms_alert");
                String lFlagEmailAlert = lUserObject.getString("flg_email_alert");
                String lCustomerStatus = lUserObject.getString("cod_status");
                String lPassword = lUserObject.getString("txt_password");
                String lLoggedStatus = lUserObject.getString("logged_status");
                String lCustomerType = lUserObject.getString("cod_cust_type");
                String lRegisterDate = lUserObject.getString("dat_register");
                String lFlagGooggleCustomer = lUserObject.getString("flg_google_cust");
                String lUserImageURL = lUserObject.getString("cust_profileImage");
                mVishwasUser.setmUserAddress(lAddress);
                mVishwasUser.setmUserEmail(lEmailId);
                mVishwasUser.setmUserFirstName(lFName);
                mVishwasUser.setmUserLastName(lLName);
                mVishwasUser.setmUserMobile(lMobileNo);
                mVishwasUser.setmAdvanceAmount(lAdvanceAmount);
                mVishwasUser.setmCustId(lCustId);
                mVishwasUser.setmCustLoggedState(lLoggedStatus);
                mVishwasUser.setmCustRegisterDate(lRegisterDate);
                mVishwasUser.setmCustStatus(lCustomerStatus);
                mVishwasUser.setmCustType(lCustomerType);
                mVishwasUser.setmFlagEmailAlert(lFlagEmailAlert);
                mVishwasUser.setmFlagGoogle(lFlagGooggleCustomer);
                mVishwasUser.setmFlagSMSAlert(lFlagSMSAlter);
                mVishwasUser.setmUserImageURL(lUserImageURL);
                mVishwasUser.setmCustPassword(lPassword);
            }
            // Getting JSON Array node
            JSONArray jsonarray = jsonObj.getJSONArray("subs_prod");

            //JSONArray jsonarray = new JSONArray(mResponse);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject VishwasProductbject = jsonarray.getJSONObject(i);
                mVishwasProduct=new VishwasProduct();
                String prod_id = VishwasProductbject.getString("prod_id");
                String prod_name = VishwasProductbject.getString("prod_name");
                String prod_type = VishwasProductbject.getString("prod_type");
                String prod_image = VishwasProductbject.getString("prod_image");
                String flg_stk_status = VishwasProductbject.getString("flg_stk_status");
                String prod_desc = VishwasProductbject.getString("prod_desc");
                String MRP_unit = VishwasProductbject.getString("MRP_unit");
                String MRP_INR = VishwasProductbject.getString("MRP_INR");
                String Min_order_qty = VishwasProductbject.getString("min_order_qty");

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
                mVishwasProduct.setMin_order_qty(Min_order_qty);
                mVishwasProduct.setMRP_FromQuantity(MRP_from_qty);
                mVishwasProduct.setMRP_ToQuantity(MRP_to_qty);
                mVishwasProduct.setPriceRangeId(MRP_range_id);

                mVishwasProductList.addVishwasProduct(mVishwasProduct);
            }

            // Getting JSON Array node
            JSONArray jsonTopuparray = jsonObj.getJSONArray("prod_dtls");

            //JSONArray jsonarray = new JSONArray(mResponse);
            for (int i = 0; i < jsonTopuparray.length(); i++) {
                JSONObject VishwasProductbject = jsonTopuparray.getJSONObject(i);
                mTopupProduct=new TopupProduct();
                String prod_id = VishwasProductbject.getString("prod_id");
                String prod_name = VishwasProductbject.getString("prod_name");
                String prod_type = VishwasProductbject.getString("prod_type");
                String prod_image = VishwasProductbject.getString("prod_image");
                String flg_stk_status = VishwasProductbject.getString("flg_stk_status");
                String prod_desc = VishwasProductbject.getString("prod_desc");
                String MRP_unit = VishwasProductbject.getString("MRP_unit");
                String MRP_INR = VishwasProductbject.getString("MRP_INR");
                String Min_order_qty = VishwasProductbject.getString("min_order_qty");
                String MRP_from_qty = VishwasProductbject.getString("MRP_from_qty");
                String MRP_to_qty = VishwasProductbject.getString("MRP_to_qty");
                String MRP_range_id = VishwasProductbject.getString("price_range_id");

                mTopupProduct.setProductId(prod_id);
                mTopupProduct.setProductName(prod_name);
                mTopupProduct.setProductDescription(prod_desc);
                mTopupProduct.setProductImage_URL(prod_image);
                mTopupProduct.setProductStockStatus(flg_stk_status);
                mTopupProduct.setProductType(prod_type);
                mTopupProduct.setProductUnit(MRP_unit);
                mTopupProduct.setMRP(MRP_INR);
                mTopupProduct.setMin_order_qty(Min_order_qty);
                mTopupProduct.setMRP_FromQuantity(MRP_from_qty);
                mTopupProduct.setMRP_ToQuantity(MRP_to_qty);
                mTopupProduct.setPriceRangeId(MRP_range_id);

                mTopupProductList.addVishwasProduct(mTopupProduct);
            }
            //Subscrption parsing
            JSONArray jsonSubscriptionArray = jsonObj.getJSONArray("subs_dtls");

            //JSONArray jsonarray = new JSONArray(mResponse);
            for (int i = 0; i < jsonSubscriptionArray.length(); i++) {
                JSONObject VishwasMySub = jsonSubscriptionArray.getJSONObject(i);
                mVishwasMySubscription=new VishwasMySubscription();
                String prod_id = VishwasMySub.getString("prod_id");
                String prod_name = VishwasMySub.getString("prod_name");
                String prod_image = VishwasMySub.getString("prod_image");
                String prod_price = VishwasMySub.getString("subs_price");

                String subs_id = VishwasMySub.getString("subs_id");
                String subs_qty = VishwasMySub.getString("subs_qty");
                String subs_frq = VishwasMySub.getString("subs_frq");
                String subs_start_date = VishwasMySub.getString("subs_start_date");
                String sub_time_slot = VishwasMySub.getString("sub_time_slot");
                String sub_status = VishwasMySub.getString("sub_status");
                String service_charge = VishwasMySub.getString("service_charge");
                String sub_end_date = VishwasMySub.getString("sub_end_date");
                String sub_price_range_id = VishwasMySub.getString("sub_price_range_id");
                String sub_reg_date = VishwasMySub.getString("sub_reg_date");

                mVishwasMySubscription.setmProd_id(prod_id);
                mVishwasMySubscription.setmProd_name(prod_name);
                mVishwasMySubscription.setmProd_url(prod_image);
                mVishwasMySubscription.setmProd_price(prod_price);

                mVishwasMySubscription.setmSubs_id(subs_id);

                mVishwasMySubscription.setmSubs_id(subs_id);
                mVishwasMySubscription.setmSubs_id(subs_id);

                mVishwasMySubscription.setmSubs_qty(subs_qty);
                mVishwasMySubscription.setmSubs_frq(subs_frq);
                mVishwasMySubscription.setmSubs_start_date(subs_start_date);
                mVishwasMySubscription.setmSub_time_slot(sub_time_slot);
                mVishwasMySubscription.setmSub_status(sub_status);
                mVishwasMySubscription.setService_charge(service_charge);
                mVishwasMySubscription.setmSub_end_date(sub_end_date);
                mVishwasMySubscription.setmSub_price_range_id(sub_price_range_id);
                mVishwasMySubscription.setmSub_reg_date(sub_reg_date);

                mVishwasMySubscriptionList.addVishwasProduct(mVishwasMySubscription);
            }
            mVishwasLoginDetails.setmVishwasProductList(mVishwasProductList);
            mVishwasLoginDetails.setmVishwasMySubscriptionList(mVishwasMySubscriptionList);
            mVishwasLoginDetails.setmTopupProductList(mTopupProductList);
            mVishwasLoginDetails.setmVishwasUser(mVishwasUser);
            return mVishwasLoginDetails;
        }
       catch (JSONException e) {
            e.printStackTrace();
           return null;
        }

    }
}
