package vf.client.com.vishwasfarm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnFragmentChange;
import vf.client.com.vishwasfarm.ServiceListener.OnUpdateSubscriptionResult;
import vf.client.com.vishwasfarm.adapter.MySubscriptionAdapter;
import vf.client.com.vishwasfarm.dialogs.CustomProductDialog;
import vf.client.com.vishwasfarm.model.VishwasMySubscription;
import vf.client.com.vishwasfarm.model.VishwasMySubscriptionList;
import vf.client.com.vishwasfarm.model.VishwasProductList;
import vf.client.com.vishwasfarm.model.VishwasUser;
import vf.client.com.vishwasfarm.parser.SubscriptionProductParser;
import vf.client.com.vishwasfarm.utility.ImageLoader;
import vf.client.com.vishwasfarm.utility.SwipeDetector;
import vf.client.com.vishwasfarm.utility.SwipeHandler;

import static vf.client.com.vishwasfarm.utility.VishwasConstants.SubscritpionData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.UserData;


public class SubscriptionFragment extends Fragment implements View.OnClickListener,OnUpdateSubscriptionResult,SwipeHandler.OnRecyclerListClickListener {
    private VishwasProductList mVishwasProductList;
    private VishwasMySubscriptionList mVishwasMySubscriptionList;

    private List<VishwasMySubscription> mMySubscriptionList;
//    private List<VishwasProduct> mProductList;
//    private VishwasUser mVishwasUser;
    Bundle lProductBundle;
    RecyclerView mySubscriptionList;
    MySubscriptionAdapter mMySubscriptionAdapter;
    public ImageLoader imageLoader;
    ImageView mProductImageView;
    TextView mProductTitle;
    LinearLayout inflatedChild;

    OnFragmentChange mOnFragmentChange;
    CustomProductDialog mCustomDialog;
    private VishwasUser mVishwasUser;
    private SwipeDetector mSwipeDetector;
   // private OnSwipeViewListener mOnSwipeViewListener;

    private String TAG=SubscriptionFragment.class.getSimpleName();
    private SwipeHandler mSwipeHandler;

    public SubscriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.my_subscriptions, container, false);
        imageLoader = new ImageLoader(getActivity().getApplicationContext());
        mOnFragmentChange= (OnFragmentChange) getActivity();


        lProductBundle = getActivity().getIntent().getExtras();
        if (lProductBundle != null) {
//            mVishwasProductList = lProductBundle.getParcelable(ProductData);
//            if (mVishwasProductList != null) {
//                mProductList = mVishwasProductList.getmVishwasProductList();
//            }
            mVishwasMySubscriptionList = lProductBundle.getParcelable(SubscritpionData);
            if (mVishwasMySubscriptionList != null) {
                mMySubscriptionList = mVishwasMySubscriptionList.getmVishwasMySubscriptionList();
            }
            if (lProductBundle.getParcelable(UserData) != null) {
               mVishwasUser = lProductBundle.getParcelable(UserData);
           }
        }

        mySubscriptionList = (RecyclerView) rootView.findViewById(R.id.subscription_list);


        mMySubscriptionAdapter=new MySubscriptionAdapter(this,R.layout.my_subscriptions_adapter,mMySubscriptionList,mVishwasUser.getmCustId().toString());
        mySubscriptionList.setAdapter(mMySubscriptionAdapter);
        //recyclerview initialization

        mSwipeHandler =new SwipeHandler(this).build(mySubscriptionList);
        mSwipeHandler.setDimensionSpaceForSwipingView(R.dimen.dimen_200dp);




        return rootView;
    }

    @Override
    public void onClick(View v) {

        if(Integer.parseInt(v.getTag().toString())==0){
            //mCustomDialog=new CustomDialog(mProductList.get(Integer.parseInt(view.getTag().toString())).getProductImage_URL(), getActivity(),new ProductDetailsFragment());
            //mOnFragmentChange.onFragmentChange(new ProductDetailsFragment());
        }
        else if(Integer.parseInt(v.getTag().toString())==1){
            //mOnFragmentChange.onFragmentChange(new ProductDetailsFragment());
        }
    }

    @Override
    public void onUpdateSubsciptionResult(boolean lStatus, String lResult) {
        mVishwasMySubscriptionList=new SubscriptionProductParser().parse(lResult);
        mMySubscriptionList=mVishwasMySubscriptionList.getmVishwasMySubscriptionList();
       // mMySubscriptionAdapter=new MySubscriptionAdapter(this,R.layout.my_subscriptions_adapter_old,mMySubscriptionList,mVishwasUser.getmCustId().toString());
        if(mMySubscriptionAdapter!=null) {
         //   mySubscriptionList.setAdapter(mMySubscriptionAdapter);
            mMySubscriptionAdapter.notifyDataSetChanged();
            if(mMySubscriptionList.size()==0){
                mMySubscriptionAdapter=new MySubscriptionAdapter(this,R.layout.my_subscriptions_adapter_old,mMySubscriptionList,mVishwasUser.getmCustId().toString());
                mySubscriptionList.setAdapter(mMySubscriptionAdapter);

                Toast.makeText(getActivity(),"No data Available",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getActivity(),"No data Available",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRecyclerListMainItemClick(Object fViewTag) {

    }

    @Override
    public void onRecyclerListOptionItemClick(Object fViewTag) {
       /* String fSubscriptionId=fViewTag.toString();
        switch (fView.getId()){
            case prod_delete:
                new DeleteSubscriptionService((MainActivity) this.getActivity(), mVishwasUser.getmCustId().toString(), fSubscriptionId).execute();
                break;
            case prod_modify:

                break;
            case prod_pause:
                CustomPauseSubscriptionDialog mCustomPauseSubscriptionDialog = new CustomPauseSubscriptionDialog((MainActivity) this.getActivity(), fSubscriptionId);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(mCustomDialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height =WindowManager.LayoutParams.WRAP_CONTENT;
                mCustomDialog.show();
                mCustomDialog.setCancelable(false);
                mCustomDialog.getWindow().setAttributes(lp);
                break;
        }*/
    }
}