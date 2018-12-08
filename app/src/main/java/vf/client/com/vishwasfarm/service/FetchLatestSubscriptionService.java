package vf.client.com.vishwasfarm.service;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import vf.client.com.vishwasfarm.ServiceListener.OnLatestSubscription;
import vf.client.com.vishwasfarm.utility.VishwasServices;

public class FetchLatestSubscriptionService extends AsyncTask<Void, Integer, String> implements VishwasServices{

	Activity mRegisterActivity;
	String mCustCode;
	int status ;
	OnLatestSubscription onLatestSubscription;

	public FetchLatestSubscriptionService(Activity mainActivity, String CustCode) {


		mRegisterActivity=mainActivity;
		onLatestSubscription= (OnLatestSubscription) mRegisterActivity;
		mCustCode=CustCode;

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			HttpURLConnection lSendMsgConnection = null;


			String URL=mLatestSubscriptions;

			URL+="?cod_cust="+mCustCode;

			String formatedURL=URL.replaceAll(" ", "%20");

			URL lPriceURL = new URL(formatedURL);


			lSendMsgConnection = (HttpURLConnection) lPriceURL.openConnection();
			lSendMsgConnection.setRequestMethod("GET");
			//lSendMsgConnection.setDoOutput(false);

//	            lSendMsgConnection.setRequestProperty("Accept","*/*");

			lSendMsgConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			lSendMsgConnection.setRequestProperty("Content-Language", "en-US");
			lSendMsgConnection.setRequestProperty("Proxy-Connection", "keep-alive");


			status = lSendMsgConnection.getResponseCode();
			InputStream lPriceInputStream=null;
			if(status!=200){
				lPriceInputStream=lSendMsgConnection.getErrorStream();
			}
			else{
				lPriceInputStream = lSendMsgConnection.getInputStream();
			}


			BufferedReader reader = new BufferedReader(new InputStreamReader(lPriceInputStream));
			StringBuilder result = new StringBuilder();
			String line;
			while((line = reader.readLine()) != null) {
				result.append(line);
			}
			System.out.println(result.toString());
			lSendMsgConnection.disconnect();
			return result.toString();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		if(status==200){
			onLatestSubscription.onLatestSubsciptionResult(true,result);

			Toast.makeText(mRegisterActivity,"Registration done Successfully", Toast.LENGTH_SHORT).show();
		}
		else{
			onLatestSubscription.onLatestSubsciptionResult(false,result);
			Toast.makeText(mRegisterActivity,"Unable to sregister user, Please try again later..", Toast.LENGTH_SHORT).show();
		}
		super.onPostExecute(result);
	}

}
