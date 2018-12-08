package vf.client.com.vishwasfarm.service;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import vf.client.com.vishwasfarm.ServiceListener.OnLogin;
import vf.client.com.vishwasfarm.utility.VishwasServices;

public class LoginUser extends AsyncTask<Void, Integer, String> implements VishwasServices{

	Activity mRegisterActivity;
	String mEmailId,mPassword,mGoogleUser;
    int status ;

    OnLogin loginInterface;
	public LoginUser(Activity registerActivity, String emailId, String password, String googleUser){

        mRegisterActivity=registerActivity;
        loginInterface= (OnLogin) mRegisterActivity;
        mEmailId=emailId;
        mPassword=password;
        mGoogleUser=googleUser;
	}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
	protected String doInBackground(Void... params) {
//		String lNumner=params[0].get(0);
//		String lMsg=params[0].get(1);
		
		 try {
	            HttpURLConnection lSendMsgConnection = null;
	           

	            String URL=mLoginUser;

	            URL+="user_id="+mEmailId+"&txt_password="+mPassword+"&flg_google_cust="+mGoogleUser;
	            
	            String formatedURL=URL.replaceAll(" ", "%20");

	            URL lPriceURL = new URL(formatedURL);
            
	            
	            lSendMsgConnection = (HttpURLConnection) lPriceURL.openConnection();
	            lSendMsgConnection.setRequestMethod("GET");
	            //lSendMsgConnection.setDoOutput(false); 
	            
//	            lSendMsgConnection.setRequestProperty("Accept","*/*");
	        
	          /*  lSendMsgConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
	            lSendMsgConnection.setRequestProperty("Content-Language", "en-US");
	            lSendMsgConnection.setRequestProperty("Proxy-Connection", "keep-alive");*/
	        
	            
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
		return "";
	}

	@Override
	protected void onPostExecute(String result) {

       		if(status==200){
                loginInterface.onLogin(true,result);

			 Toast.makeText(mRegisterActivity,"Successfully Logged in", Toast.LENGTH_SHORT).show();
		}
		else{
                loginInterface.onLogin(false,result);
			 Toast.makeText(mRegisterActivity,"Unable to Login user, Please try again later..", Toast.LENGTH_SHORT).show();
		}
		super.onPostExecute(result);
	}
	
}
