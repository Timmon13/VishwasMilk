package vf.client.com.vishwasfarm.service;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import vf.client.com.vishwasfarm.utility.VishwasServices;

public class UpdateCustProfile extends AsyncTask<Void, Integer, String> implements VishwasServices{

    Bitmap bitmap;
    String FlagSMSAlert, FlagEmailAlert, CustCode;
    int status;
    Activity mActivity;
    private DataOutputStream outputStream=null;

    public UpdateCustProfile(FragmentActivity fActivity, String custCode, Bitmap bitmap, String FlagSMSAlert, String FlagEmailAlert){
        this.mActivity=fActivity;
        this.CustCode = custCode;
        this.bitmap = bitmap;
        this.FlagSMSAlert = FlagSMSAlert;
        this.FlagEmailAlert = FlagEmailAlert;
    }

    @Override
    protected String doInBackground(Void... params) {

        String str_result = null;


        HttpURLConnection conn = null;
        String URL=mUpdateCustProfile;
        String formattedURL=URL.replaceAll(" ", "%20");
        URL lPriceURL = null;
        BufferedReader reader = null;
        try {

            lPriceURL = new URL(formattedURL);
            conn = (HttpURLConnection) lPriceURL.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(150000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.addRequestProperty("cod_cust", CustCode);
            conn.addRequestProperty("FlagEmailAlert", FlagEmailAlert);
            conn.addRequestProperty("FlagSMSAlert", FlagSMSAlert);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            byte[] byteArray = stream.toByteArray();

            conn.addRequestProperty("profile_image", "iVBORw0KGgoAAAANSUhEUgAAABsAAAAbCAYAAACN1PRVAAAABGdBTUEAALGPC/xhBQAAAAZiS0dE\n" +
                    "AP8A/wD/oL2nkwAAAAlwSFlzAAAOwwAADsMBx2+oZAAAACdJREFUSMftzSEBAAAIA7AHpiuRoAWG\n" +
                    "iekl1XNGJpPJZDKZTCZ7nS1lu1eFaRECMwAAABJ0RVh0RVhJRjpPcmllbnRhdGlvbgAxhFjs7wAA\n" +
                    "AABJRU5ErkJggg==");



/*
                lSendMsgConnection.setRequestProperty("Connection", "Keep-Alive");
                lSendMsgConnection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            lSendMsgConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            lSendMsgConnection.connect();

            outputStream = new DataOutputStream(lSendMsgConnection.getOutputStream());
*/


                // Upload POST Data
          /*  JSONObject jsonParam = new JSONObject();
            jsonParam.put("cod_cust", CustCode);
            jsonParam.put("FlagEmailAlert", FlagEmailAlert );
            jsonParam.put("FlagSMSAlert", FlagSMSAlert );

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            //output.close();

            //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
            outputStream.writeBytes(jsonParam.toString());

            //lSendMsgConnection.connect();
            outputStream.flush();
            outputStream.close();*/
                status = conn.getResponseCode();
                InputStream lPriceInputStream = null;
                if (status != 200)
                    lPriceInputStream = conn.getErrorStream();
                else
                    lPriceInputStream = conn.getInputStream();

                reader = new BufferedReader(new InputStreamReader(lPriceInputStream));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                System.out.println(result.toString());

                conn.disconnect();
                str_result = result.toString();
            } catch (ProtocolException e1) {
            e1.printStackTrace();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
            str_result = e.getMessage();
        }
        finally {
            conn.disconnect();
        }
        return str_result;

    }

    private String getQuery(List<Pair<String, String>> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        String str_result;

        for (Pair<String, String> pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            if (pair.first.equals("profile_image"))
                result.append(pair.second);
            else
                result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }
        str_result = result.toString();
        return str_result;
    }

    @Override
    protected void onPostExecute(String result) {
        StringBuilder sb = new StringBuilder();
        String str_result;
        sb.append(status); sb.append(" ");
        sb.append(result); sb.append(" ");
        str_result = sb.toString();

        if(status==200){
            Toast.makeText(mActivity,"Profile updated successfully ".concat(str_result), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(mActivity,"Unable to update profile ".concat(str_result), Toast.LENGTH_LONG).show();
        }
        super.onPostExecute(result);
    }

}
