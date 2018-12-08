package vf.client.com.vishwasfarm.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnFragmentChange;
import vf.client.com.vishwasfarm.ServiceListener.OnLatestSubscription;
import vf.client.com.vishwasfarm.ServiceListener.OnPauseSubscriptionDateChange;
import vf.client.com.vishwasfarm.ServiceListener.OnSubscription;
import vf.client.com.vishwasfarm.ServiceListener.OnSubscriptionListClickListener;
import vf.client.com.vishwasfarm.ServiceListener.OnUpdateSubscriptionResult;
import vf.client.com.vishwasfarm.dialogs.CustomPauseSubscriptionDialog;
import vf.client.com.vishwasfarm.fragments.AnnouncementFragment;
import vf.client.com.vishwasfarm.fragments.ContactUsFragment;
import vf.client.com.vishwasfarm.fragments.DiaryFragment;
import vf.client.com.vishwasfarm.fragments.HomeFragment;
import vf.client.com.vishwasfarm.fragments.ProfileFragment;
import vf.client.com.vishwasfarm.fragments.SubscriptionFragment;
import vf.client.com.vishwasfarm.model.TopupProduct;
import vf.client.com.vishwasfarm.model.TopupProductList;
import vf.client.com.vishwasfarm.model.VishwasMySubscription;
import vf.client.com.vishwasfarm.model.VishwasMySubscriptionList;
import vf.client.com.vishwasfarm.model.VishwasProduct;
import vf.client.com.vishwasfarm.model.VishwasProductList;
import vf.client.com.vishwasfarm.model.VishwasUser;
import vf.client.com.vishwasfarm.parser.SubscriptionProductParser;
import vf.client.com.vishwasfarm.service.DeleteSubscriptionService;
import vf.client.com.vishwasfarm.service.FetchLatestSubscriptionService;
import vf.client.com.vishwasfarm.service.PauseSubscriptionService;

import static vf.client.com.vishwasfarm.utility.VishwasConstants.LATEST_UBSCRIPTION_TAG;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.ProductData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.SubscritpionData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.TopupData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.UserData;


public class MainActivity extends AppCompatActivity implements OnPauseSubscriptionDateChange,
        OnFragmentChange, OnUpdateSubscriptionResult,OnSubscription, OnLatestSubscription, OnSubscriptionListClickListener{

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;

    private VishwasProductList mVishwasProductList;
    private VishwasMySubscriptionList mVishwasMySubscriptionList;
    private TopupProductList mTopupProductList;
    private VishwasUser mVishwasUser;

    private List<VishwasProduct> mProductList;
    private List<VishwasMySubscription> mMySubscriptionList;
    private List<TopupProduct> mMyTopupProductList;

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;
    private String[] storage_permissions =
            {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
    private CustomPauseSubscriptionDialog mCustomDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        setupToolbar();

//Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.home);

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected( MenuItem item) {
                        Fragment selectedFragment = null;
                        String lTag=null;
                        FragmentTransaction transaction;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                loadHomeFragment();
                                break;
                            case R.id.action_item2:
                                //selectedFragment = new SubscriptionFragment();
                                //lTag=SubscriptionFragment.class.getSimpleName();
                                new FetchLatestSubscriptionService(MainActivity.this,mVishwasUser.getmCustId()).execute();
                                break;
                            case R.id.action_item3:
                                Fragment lFragement= getSupportFragmentManager().findFragmentByTag(ProfileFragment.class.getSimpleName());
                                if(lFragement==null){
                                    lFragement=new ProfileFragment();
                                }

                                if (lFragement != null) {
                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.frame, lFragement,ProfileFragment.class.getSimpleName()).commit();
                                }
                                break;
                        }

                        return true;
                    }
                });



//        navigationView.getMenu().getItem(0).setChecked(true);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                Fragment fragment = null;
                String lFragmentTag=null;
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.home:
                        fragment = new HomeFragment();
                        lFragmentTag=HomeFragment.class.getSimpleName();
                        setTitle(mNavigationDrawerItemTitles[0]);
                        navigationView.getMenu().findItem(R.id.home).setChecked(true);
                        break;

                    // For rest of the options we just show a toast on click

                    case R.id.diary:
                        fragment = new DiaryFragment();
                        setTitle(mNavigationDrawerItemTitles[1]);
                        navigationView.getMenu().findItem(R.id.diary).setChecked(true);
                        break;
                    case R.id.announcement:
                        setTitle(mNavigationDrawerItemTitles[2]);
                        fragment = new AnnouncementFragment();
                        navigationView.getMenu().findItem(R.id.announcement).setChecked(true);
                        break;
                    case R.id.contact:
                        setTitle(mNavigationDrawerItemTitles[3]);
                        fragment = new ContactUsFragment();
                        navigationView.getMenu().findItem(R.id.contact).setChecked(true);

                        break;


                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        break;

                }
                if (fragment != null) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame, fragment,lFragmentTag).commit();
                }
                return true;
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.home, 1);
        }
        navigationView.getMenu().findItem(R.id.home).setChecked(true);
        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        Bundle lProductBundle = this.getIntent().getExtras();
        if (lProductBundle != null) {
            mVishwasProductList = lProductBundle.getParcelable(ProductData);
            if (mVishwasProductList != null) {
                mProductList = mVishwasProductList.getmVishwasProductList();
            }
            mVishwasMySubscriptionList = lProductBundle.getParcelable(SubscritpionData);
            if (mVishwasMySubscriptionList != null) {
                mMySubscriptionList = mVishwasMySubscriptionList.getmVishwasMySubscriptionList();
            }
            mTopupProductList = lProductBundle.getParcelable(TopupData);
            if (mTopupProductList != null) {
                mMyTopupProductList= mTopupProductList.getmVishwasProductList();
            }

            if (lProductBundle.getParcelable(UserData) != null) {
                mVishwasUser = lProductBundle.getParcelable(UserData);
            }
        }


        askStorgaePermission();
    }

    private void askStorgaePermission() {
        if ((int) Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
                        builder.setMessage("To get storage access you have to allow us access to your sd card content.");
                        builder.setTitle("Storage");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this, storage_permissions, 0);

                            }
                        });

                        builder.show();
                    } else {
                        ActivityCompat.requestPermissions(this, storage_permissions, 0);

                    }
                } else {
                    ActivityCompat.requestPermissions(this,
                            storage_permissions,
                            MY_PERMISSIONS_REQUEST_STORAGE);

                }

            }
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onFragmentChange(Fragment lChildFragment) {
        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.frame);
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, lChildFragment).commit();

        }
    }

    @Override
    public void onSubsciptionResult(boolean lStatus, String lResult) {
        loadHomeFragment();

    }

    @Override
    public void onLatestSubsciptionResult(boolean lStatus, String lResult) {

        mVishwasMySubscriptionList=new SubscriptionProductParser().parse(lResult);

        Bundle lProductBundle = this.getIntent().getExtras();
        if (lProductBundle != null) {
            lProductBundle.putParcelable(SubscritpionData,mVishwasMySubscriptionList);
            getIntent().putExtras(lProductBundle);
        }



        Fragment lFragement= getSupportFragmentManager().findFragmentByTag(LATEST_UBSCRIPTION_TAG);
        if(lFragement==null){
            lFragement=new SubscriptionFragment();
        }

        if (lFragement != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, lFragement,LATEST_UBSCRIPTION_TAG).commit();

        }
    }


    public void loadHomeFragment(){
        Fragment lFragement= getSupportFragmentManager().findFragmentByTag(HomeFragment.class.getSimpleName());
        if(lFragement==null){
            lFragement=new HomeFragment();
        }

        if (lFragement != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, lFragement,HomeFragment.class.getSimpleName()).commit();

        }
    }

    @Override
    public void onPauseOrderClick(String fSubscriptionId) {

        mCustomDialog=new CustomPauseSubscriptionDialog(this, fSubscriptionId);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mCustomDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height =WindowManager.LayoutParams.WRAP_CONTENT;
        mCustomDialog.show();
        mCustomDialog.setCancelable(false);
        mCustomDialog.getWindow().setAttributes(lp);


    }

    @Override
    public void onDeleteOrderClick(String fSubscriptionId) {
        new DeleteSubscriptionService(this,mVishwasUser.getmCustId().toString(),fSubscriptionId ).execute();
    }

    @Override
    public void onEditOrderClick(String fItemPosition) {

    }

    @Override
    public void onUpdateSubsciptionResult(boolean lStatus, String lResult) {

        Fragment lFragement= getSupportFragmentManager().findFragmentByTag(LATEST_UBSCRIPTION_TAG);
        if(lFragement==null){
            lFragement=new SubscriptionFragment();
        }

        if (lFragement != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, lFragement,LATEST_UBSCRIPTION_TAG).commit();
            ((SubscriptionFragment)lFragement).onUpdateSubsciptionResult(lStatus,lResult);
        }
    }

    @Override
    public void onPauseSubscriptionDateModified(String fSubscriptionId,String fStartDate,String fEndDate) {
        new PauseSubscriptionService(this,mVishwasUser.getmCustId().toString(),fSubscriptionId,fStartDate,fEndDate ).execute();

    }
}