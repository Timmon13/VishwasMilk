package vf.client.com.vishwasfarm.database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by amhule on 12/1/2016.
 */
public class VishwasSharedPreferance  {

    private  SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    public VishwasSharedPreferance(Context lContext){
        mContext=lContext;
         pref = mContext.getSharedPreferences("VishwasFarms", lContext.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setLoging(boolean status){
        editor.putBoolean("login", true);
        editor.commit();
    }

    public boolean getLogin(){
        return  pref.getBoolean("login", false);
    }

    public void setData(){
        editor.putBoolean("key_name1", true);           // Saving boolean - true/false
        editor.putInt("key_name2", 0);        // Saving integer
        editor.putFloat("key_name3", 0);    // Saving float
        editor.putLong("key_name4", 0);      // Saving long
        editor.putString("key_name5", "string value");  // Saving string

        // Save the changes in SharedPreferences
        editor.commit(); // commit changes
        }

    public void getData(){
        pref.getBoolean("key_name1", false);         // getting boolean
        pref.getInt("key_name2", 0);             // getting Integer
        pref.getFloat("key_name3", 0);           // getting Float
        pref.getLong("key_name4", 0);            // getting Long
        pref.getString("key_name5", null);          // getting String
    }

    public void deleteData(){

        editor.remove("key_name3"); // will delete key key_name3
        editor.remove("key_name4"); // will delete key key_name4

        // Save the changes in SharedPreferences
        editor.commit(); // commit changes
        }

    public void deleteAllData(){
        editor.clear();
        editor.commit();
    }
}
