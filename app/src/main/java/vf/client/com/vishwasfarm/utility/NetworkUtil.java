package vf.client.com.vishwasfarm.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {
	
	public static int TYPE_WIFI = 1;
	public static int TYPE_MOBILE = 2;
	public static int TYPE_NOT_CONNECTED = 0;
	
	
	public static int getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;
			
			if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		} 
		return TYPE_NOT_CONNECTED;
	}
	
	public static boolean getConnectivityStatusString(Context context) {
		int conn = NetworkUtil.getConnectivityStatus(context);
		boolean status = false;
		if (conn == NetworkUtil.TYPE_WIFI) {

                ConnectivityManager connectivity = (ConnectivityManager) context
                        .getApplicationContext().getSystemService(
                                Context.CONNECTIVITY_SERVICE);

                if (connectivity != null) {
                    NetworkInfo[] info = connectivity.getAllNetworkInfo();
                    if (info != null) {
                        for (int i = 0; i < info.length; i++) {
                            if (info[i].getExtraInfo() != null) {
                       
                                    if( info[i].isConnected()){
                                        return true;
                                    }
                            

                            }
                        }
                    }
                }
            else
                return false;
		} else if (conn == NetworkUtil.TYPE_MOBILE) {
			status = true;
		} else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
			status = false;
		}
		return status;
	}
}