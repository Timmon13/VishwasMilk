package vf.client.com.vishwasfarm.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnRegistration;
import vf.client.com.vishwasfarm.model.VishwasLoginDetails;
import vf.client.com.vishwasfarm.model.VishwasProductList;
import vf.client.com.vishwasfarm.parser.VishwasLoginDetailParser;
import vf.client.com.vishwasfarm.service.RegisterUser;

import static vf.client.com.vishwasfarm.utility.VishwasConstants.ProductData;


/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class RegisterActivity extends AppCompatActivity implements OnConnectionFailedListener, OnClickListener,
        ConnectionCallbacks,
        LocationListener, OnRegistration {

    //Define a request code to send to Google Play services
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleApiClient mGoogleLoginApiClient, mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;

    private boolean mIsLocationGranted = false;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    Toolbar toolbar;
    private VishwasLoginDetails mVishwasLoginDetails;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    // UI references.
    private AutoCompleteTextView mEmailView;
    TextView mAutoLocation;
    private EditText mFirstName, mLastname, mPasswordView, mAddressView, mMobileNumberView;
    private Button mProceed;
    ImageView mLName, mFName, mMobile, mEmail, mPassword, mAddress;
    LinearLayout mParentLayout;

    private boolean mGoogleUser;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    public ProgressDialog mDialog;

    private VishwasProductList mProductList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register_user);
        mTitle = mDrawerTitle = getTitle();
// Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(this);
// Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        mGoogleLoginApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                // The next two lines tell the new client that “this” current class will handle connection stuff
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                //fourth line adds the LocationServices API endpoint from GooglePlayServices
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        mParentLayout = (LinearLayout) findViewById(R.id.register_user);
        mFirstName = (EditText) findViewById(R.id.firstname);
        mLastname = (EditText) findViewById(R.id.lastname);
        mPasswordView = (EditText) findViewById(R.id.password);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mAddressView = (EditText) findViewById(R.id.address);
        mMobileNumberView = (EditText) findViewById(R.id.mobile);
        mAutoLocation = (TextView) findViewById(R.id.google_location);
        mProceed = (Button) findViewById(R.id.proceed);
        mProceed.setOnClickListener(this);

        mLName = (ImageView) findViewById(R.id.alert_lname);
        mFName = (ImageView) findViewById(R.id.alert_fname);
        mMobile = (ImageView) findViewById(R.id.alert_mobile);
        mEmail = (ImageView) findViewById(R.id.alert_email);
        mPassword = (ImageView) findViewById(R.id.alert_password);
        mAddress = (ImageView) findViewById(R.id.alert_address);
/*
        GPSTracker gpsTracker = new GPSTracker(this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            gpsTracker.getLocation();
            getLocationString(gpsTracker.getLatitude(),gpsTracker.getLongitude());
            gpsTracker.stopUsingGPS();
        }
        else{
            gpsTracker.showSettingsAlert();
            }
*/
        if (checkAndRequestPermissions()) {
            // carry on the normal flow, as the case of  permissions  granted.

        }

        mDialog = new ProgressDialog(this); // this = YourActivity
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("Loading. Please wait...");
        mDialog.setIndeterminate(true);
        mDialog.setCanceledOnTouchOutside(false);

        setupToolbar();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
//        getSupportActionBar().setTitle(mTitle);
    }

    private void setupToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                mGoogleUser = true;
                mEmail.setVisibility(View.INVISIBLE);
                mPassword.setVisibility(View.GONE);
                signIn();
                break;
            case R.id.proceed:
                String firstName = mFirstName.getText().toString();
                String lastName = mLastname.getText().toString();
                String email = mEmailView.getText().toString();
                String mobile = mMobileNumberView.getText().toString();
                String password = "" + mPasswordView.getText().toString();
                String address = mAddressView.getText().toString();
                String gpsLocation = currentLatitude + ";" + currentLongitude;
                String googleUser = (mGoogleUser == true) ? "Y" : "N";


                if (firstName.length() > 0 && lastName.length() > 0 && email.length() > 0 && mobile.length() > 0 && address.length() > 0 && gpsLocation.length() > 0) {
                    mDialog.show();
                    mFName.setVisibility(View.INVISIBLE);
                    mLName.setVisibility(View.INVISIBLE);
                    mMobile.setVisibility(View.INVISIBLE);
                    mAddress.setVisibility(View.INVISIBLE);
                    mEmail.setVisibility(View.INVISIBLE);
                    mPassword.setVisibility(View.INVISIBLE);
                    new RegisterUser(this, firstName, lastName, address,
                            gpsLocation, mobile, email, password, googleUser).execute();
                } else {
                    if (firstName.length() == 0) {
                        mFName.setVisibility(View.VISIBLE);
                    } else {
                        mFName.setVisibility(View.INVISIBLE);
                    }
                    if (lastName.length() == 0) {
                        mLName.setVisibility(View.VISIBLE);
                    } else {
                        mLName.setVisibility(View.INVISIBLE);
                    }
                    if (address.length() == 0) {
                        mAddress.setVisibility(View.VISIBLE);
                    } else {
                        mAddress.setVisibility(View.INVISIBLE);
                    }
                    if (mobile.length() == 0) {
                        mMobile.setVisibility(View.VISIBLE);
                    } else {
                        mMobile.setVisibility(View.INVISIBLE);
                    }
                    if (email.length() == 0) {
                        mEmail.setVisibility(View.VISIBLE);
                    } else {
                        mEmail.setVisibility(View.INVISIBLE);
                    }
                    if (password.length() == 0 && mGoogleUser == false) {
                        mPassword.setVisibility(View.VISIBLE);
                    } else {
                        mPassword.setVisibility(View.INVISIBLE);
                    }
                    View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
                    Snackbar snackbar = Snackbar.make(rootView, "Please complete the form with valid information.", Snackbar.LENGTH_LONG);
                    View snackbarLayout = snackbar.getView();
                    TextView textView = (TextView) snackbarLayout.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_error, 0, 0, 0);
                    textView.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.snackbar_icon_padding));
                    snackbar.show();
                }
                break;
        }
    }

    private void signIn() {
        mDialog.show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleLoginApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            updateUI(acct, true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(null, false);
        }
    }

    private void updateUI(GoogleSignInAccount acct, boolean b) {
        Log.d(TAG, "handleSignInResult:" + b);
        mDialog.dismiss();
        if (acct != null && b == true) {
            mGoogleUser = true;
            mFirstName.setText("" + acct.getGivenName());
            mLastname.setText("" + acct.getFamilyName());
            mEmailView.setText("" + acct.getEmail());
            mEmailView.setEnabled(false);
            mPasswordView.setVisibility(View.GONE);
            acct.getPhotoUrl();
        }
        //  Intent i=new Intent(this,MainActivity.class);
        //  startActivity(i);
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (checkAndRequestPermissions()) {

            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            } else {
                //If everything went fine lets get latitude and longitude
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();


                getLocationString(currentLatitude, currentLongitude);
            }
        }
        mDialog.dismiss();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mDialog.show();
        //Now lets connect to the API
        //checkAndRequestPermissions();
        mGoogleApiClient.connect();

    }

    private  boolean checkAndRequestPermissions() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
            return false;
        }
        return true;
    }




    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }



    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
            Log.e("Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
        mDialog.dismiss();
    }

    /**
     * If locationChanges change lat and long
     *
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mIsLocationGranted=true;

                    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    if (location == null) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

                    } else {
                        //If everything went fine lets get latitude and longitude
                        currentLatitude = location.getLatitude();
                        currentLongitude = location.getLongitude();


                        getLocationString(currentLatitude,currentLongitude);
                    }
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                }
                else  {
                    mIsLocationGranted=false;
                    mAutoLocation.setText("Google Location: \n Unable to fetch your latest location. No worries!! You can proceed further." );
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void getLocationString(double latitude,double longitude) {
        Geocoder geocoder;
        List<Address> addresses=null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses!=null && addresses.size()>0) {
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            mAutoLocation.setText("Google Location: \n" + address + ", " + city + ", " + state + ", \n" + country + ", Pin code:" + postalCode);
        }
        else{
            mAutoLocation.setText("Google Location: \n Unable to fetch your latest location. No worries!! You can proceed further." );
        }
    }



    @Override
    public void onRegistration(boolean lStatus,String lResult) {
        if(mDialog!=null || mDialog.isShowing()){
            mDialog.dismiss();
        }
        if(lStatus==true){
            Log.d("RegisterActivity","lresult: "+lResult);

            mVishwasLoginDetails=new VishwasLoginDetailParser(lResult).parceVishwasLoginDetails();

            Toast.makeText(this,mVishwasLoginDetails.getmLoginErrorDisc(),Toast.LENGTH_LONG).show();

            mProductList=mVishwasLoginDetails.getmVishwasProductList();
           // mProductList=new VishwasProductParser(lResult).parseVishwasProduct();
            Intent startMainScope=new Intent(this,MainActivity.class);
            Bundle b = new Bundle();
            b.putParcelable(ProductData, mProductList);
            startMainScope.putExtras(b);

            startActivity(startMainScope);
            finish();
            //  Intent appStart=new Intent(RegisterActivity.class,MainActivity.class);
        }
    }
}



