package vf.client.com.vishwasfarm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnGuestEntry;
import vf.client.com.vishwasfarm.ServiceListener.OnLogin;
import vf.client.com.vishwasfarm.ServiceListener.OnRegistration;
import vf.client.com.vishwasfarm.dialogs.CustomLoader;
import vf.client.com.vishwasfarm.model.TopupProductList;
import vf.client.com.vishwasfarm.model.VishwasLoginDetails;
import vf.client.com.vishwasfarm.model.VishwasMySubscriptionList;
import vf.client.com.vishwasfarm.model.VishwasProductList;
import vf.client.com.vishwasfarm.model.VishwasUser;
import vf.client.com.vishwasfarm.parser.VishwasLoginDetailParser;
import vf.client.com.vishwasfarm.service.GuestUserEntry;
import vf.client.com.vishwasfarm.service.LoginUser;

import static vf.client.com.vishwasfarm.utility.VishwasConstants.ProductData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.SubscritpionData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.TopupData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.UserData;


/**
 * A login screen that offers login via email/password and via Google+ sign in.
 * <p/>
 * ************ IMPORTANT SETUP NOTES: ************
 * In order for Google+ sign in to work with your app, you must first go to:
 * https://developers.google.com/+/mobile/android/getting-started#step_1_enable_the_google_api
 * and follow the steps in "Step 1" to create an OAuth 2.0 client for your package.
 */
public class LoginActivity extends AppCompatActivity implements  OnClickListener, OnRegistration,OnLogin,OnGuestEntry,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    Toolbar toolbar;
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mEmailLoginFormView;
    private SignInButton mPlusSignInButton;
    private View mSignOutButtons;
    private View mLoginFormView;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private TextView mStatusTextView;
    Button mSignInButton,mRegisterButton;//,mGuestEntryButton;


    private VishwasProductList mProductList;
    private TopupProductList mTopupProductList;
    private VishwasMySubscriptionList mVishwasMySubscriptionList;
    private VishwasUser mVishwasUser;
    private VishwasLoginDetails mVishwasLoginDetails;


    SignInButton mGoogleSignInButton;
    private GoogleApiClient mGoogleLoginApiClient,mGoogleApiClient;
    private CustomLoader mCustomLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        mTitle = mDrawerTitle = getTitle();
// Set the dimensions of the sign-in button.
        mEmailView= (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView= (EditText) findViewById(R.id.password);
        mSignInButton = (Button) findViewById(R.id.sign_in);
        //mGuestEntryButton=(Button) findViewById(R.id.guest_button);
        mRegisterButton= (Button) findViewById(R.id.register_button);
        mSignInButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);
        //mGuestEntryButton.setOnClickListener(this);

        // Set the dimensions of the sign-in button.
        mGoogleSignInButton = (SignInButton) findViewById(R.id.sign_in_button);

        mGoogleSignInButton.setSize(SignInButton.SIZE_STANDARD);

        mGoogleSignInButton.setOnClickListener(this);
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

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null && (bundle.get("data")!=null)) {
            String lData = bundle.getString("data");
            Log.d("LoginActivity","Notification Data :"+lData);
        }
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
        mCustomLoader=new CustomLoader(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mCustomLoader.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height =WindowManager.LayoutParams.WRAP_CONTENT;
        mCustomLoader.show();
        mCustomLoader.setCancelable(false);
        mCustomLoader.getWindow().setAttributes(lp);

        switch (v.getId()) {
            case R.id.sign_in:
                signIn();
                break;
            case R.id.register_button:
                registerUser();
                break;
           /* case R.id.guest_button:
                guestEntry();
                break;*/
            case R.id.sign_in_button:
                googleSignIn();
                break;
        }
    }

    private void guestEntry() {
        new GuestUserEntry(this).execute();
    }

    private void registerUser() {
        Intent i=new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    private void signIn() {

        new LoginUser(this,""+mEmailView.getText(),""+mPasswordView.getText(), "N").execute();

        //Intent i=new Intent(this,RegisterActivity.class);
        // startActivity(i);
    }


    @Override
    public void onRegistration(boolean lStatus, String lresult) {
        if(lStatus==true){
            Log.d("RegisterActivity", "lresult: " + lresult);

            mProductList=mVishwasLoginDetails.getmVishwasProductList();
            mVishwasUser=mVishwasLoginDetails.getmVishwasUser();
            mVishwasMySubscriptionList=mVishwasLoginDetails.getmVishwasMySubscriptionList();

            Intent startMainScope=new Intent(this,MainActivity.class);
            Bundle b = new Bundle();
            b.putParcelable(ProductData, mProductList);
            b.putParcelable(SubscritpionData, mVishwasMySubscriptionList);
            b.putParcelable(UserData, mVishwasUser);

            startMainScope.putExtras(b);

            startActivity(startMainScope);
            finish();
            //  Intent appStart=new Intent(RegisterActivity.class,MainActivity.class);
        }
        else{
            mCustomLoader.dismiss();
            mCustomLoader.setmState(false);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(mCustomLoader.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height =WindowManager.LayoutParams.WRAP_CONTENT;
            mCustomLoader.show();
            mCustomLoader.setCancelable(false);
            mCustomLoader.getWindow().setAttributes(lp);
        }
    }

    @Override
    public void onLogin(boolean lStatus, String lResult) {
        Log.d("RegisterActivity", "lresult: " + lResult);
        if(lStatus) {
            mVishwasLoginDetails = new VishwasLoginDetailParser(lResult).parceVishwasLoginDetails();

            Toast.makeText(this, "***" + mVishwasLoginDetails.getmLoginErrorDisc(), Toast.LENGTH_LONG).show();

            mProductList = mVishwasLoginDetails.getmVishwasProductList();
            mVishwasUser = mVishwasLoginDetails.getmVishwasUser();
            mVishwasMySubscriptionList = mVishwasLoginDetails.getmVishwasMySubscriptionList();
            mTopupProductList = mVishwasLoginDetails.getmTopupProductList();

            Intent startMainScope = new Intent(this, MainActivity.class);
            Bundle b = new Bundle();
            b.putParcelable(ProductData, mProductList);
            b.putParcelable(SubscritpionData, mVishwasMySubscriptionList);
            b.putParcelable(UserData, mVishwasUser);
            b.putParcelable(TopupData, mTopupProductList);


            startMainScope.putExtras(b);

            startActivity(startMainScope);
            finish();
        }
        else{
        mCustomLoader.setmState(false);
    }
    }

    @Override
    public void onGuestEntry(boolean lStatus, String lResult) {
        Log.d("RegisterActivity", "lresult: " + lResult);

        mProductList=mVishwasLoginDetails.getmVishwasProductList();
        mVishwasUser=mVishwasLoginDetails.getmVishwasUser();
        mVishwasMySubscriptionList=mVishwasLoginDetails.getmVishwasMySubscriptionList();

        Intent startMainScope=new Intent(this,MainActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(ProductData, mProductList);
        b.putParcelable(SubscritpionData, mVishwasMySubscriptionList);
        b.putParcelable(UserData, mVishwasUser);

        startMainScope.putExtras(b);

        startActivity(startMainScope);
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        //checkAndRequestPermissions();
        mGoogleApiClient.connect();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void googleSignIn() {
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

            QueryGoogleLogin(acct,true);
        } else {
            // Signed out, show unauthenticated UI.
            QueryGoogleLogin(null, false);
        }
    }

    private void QueryGoogleLogin(GoogleSignInAccount acct, boolean b) {
        Log.d(TAG, "handleSignInResult:" + b);
        if(acct!=null && b==true) {
            new LoginUser(this,""+acct.getEmail(),"", "Y").execute();
        }

    }
}



