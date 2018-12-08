package vf.client.com.vishwasfarm.utility;

/**
 * Created by amhule on 11/28/2016.
 */
public interface VishwasServices {

    String mRegisterNewUser="http://www.vishwasfarms.com/vfarm_backup/services/register_cust.php?";
    String mLoginUser="http://www.vishwasfarms.com/vfarm_backup/services/login_cust.php?";//user_id=9545750033&txt_password=mypwd&flg_google_cust=N";
    String mProductDetails="http://www.vishwasfarms.com/vfarm_backup/services/prod_dtls.php";
    String mSubscriptionService="http://www.vishwasfarms.com/vfarm_backup/services/new_subs.php";
    String mLatestSubscriptions="http://www.vishwasfarms.com/vfarm_backup/services/subs_dtls.php";
    String mDeleteSubscription="http://www.vishwasfarms.com/vfarm_backup/services/delete_subs.php";
    String mPauseSubscription="http://www.vishwasfarms.com/vfarm_backup/services/pause_subs.php";
    String mUpdateCustProfile="http://www.vishwasfarms.com/vfarm_backup/services/UpdateCustProfile.php";
}
