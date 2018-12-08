package vf.client.com.vishwasfarm.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vf.client.com.vishwasfarm.model.VishwasMySubscription;
import vf.client.com.vishwasfarm.model.VishwasMySubscriptionList;

/**
 * Created by WVMQ73N on 7/8/2017.
 */

public class SubscriptionProductParser {
    private VishwasMySubscriptionList mVishwasMySubscriptionList;
    private VishwasMySubscription mVishwasMySubscription;

    public SubscriptionProductParser() {
        this.mVishwasMySubscriptionList = new VishwasMySubscriptionList();
    }

    public VishwasMySubscriptionList parse(String lResult) {

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(lResult);


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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mVishwasMySubscriptionList;
    }
}
