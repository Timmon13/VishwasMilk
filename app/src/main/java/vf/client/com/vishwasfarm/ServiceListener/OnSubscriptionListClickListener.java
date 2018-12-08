package vf.client.com.vishwasfarm.ServiceListener;

/**
 * Created by WVMQ73N on 12/31/2017.
 */

/**
 * Handles callback for License plate order item clicks
 */
public interface OnSubscriptionListClickListener {
    void onPauseOrderClick(String fLicensePlateNumber);

    void onDeleteOrderClick(String fItemPosition);
    void onEditOrderClick(String fItemPosition);
}
