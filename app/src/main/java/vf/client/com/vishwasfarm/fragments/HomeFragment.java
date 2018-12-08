package vf.client.com.vishwasfarm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnFragmentChange;
import vf.client.com.vishwasfarm.adapter.ProductsAdapter;
import vf.client.com.vishwasfarm.adapter.TopUpAdapter;
import vf.client.com.vishwasfarm.dialogs.CustomProductDialog;
import vf.client.com.vishwasfarm.model.TopupProduct;
import vf.client.com.vishwasfarm.model.TopupProductList;
import vf.client.com.vishwasfarm.model.VishwasMySubscription;
import vf.client.com.vishwasfarm.model.VishwasMySubscriptionList;
import vf.client.com.vishwasfarm.model.VishwasProduct;
import vf.client.com.vishwasfarm.model.VishwasProductList;
import vf.client.com.vishwasfarm.model.VishwasUser;
import vf.client.com.vishwasfarm.utility.ImageLoader;

import static vf.client.com.vishwasfarm.utility.VishwasConstants.ProductData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.SubscritpionData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.TopupData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.UserData;


public class HomeFragment extends Fragment {
    private VishwasProductList mVishwasProductList;
    private TopupProductList mTopUpProductList;
    private VishwasMySubscriptionList mVishwasMySubscriptionList;

    private VishwasUser mVishwasUser;

    private List<TopupProduct> mTopUpList;
    private List<VishwasProduct> mProductList;
    private List<VishwasMySubscription> mMySubscriptionList;
    Bundle lProductBundle;
    public ImageLoader imageLoader;
    ImageView mProductImageView;
    TextView mProductTitle;
    LinearLayout inflatedChild;
    GridView mProductGrid,mTopupProductGrid;
    OnFragmentChange mOnFragmentChange;
    private TopupProductList mTopupProductList;
    private List<TopupProduct> mMyTopupProductList;

    CustomProductDialog mCustomDialog;
    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        imageLoader = new ImageLoader(getActivity().getApplicationContext());
        mOnFragmentChange= (OnFragmentChange) getActivity();


        Bundle lProductBundle = getActivity().getIntent().getExtras();
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

        ProductsAdapter lProductsAdapter=new ProductsAdapter(getActivity(),R.layout.products_adapter,mProductList);
        mProductGrid= (GridView) rootView.findViewById(R.id.product_list);
        mProductGrid.setAdapter(lProductsAdapter);

        TopUpAdapter lTopupProductsAdapter=new TopUpAdapter(getActivity(),R.layout.products_adapter,mMyTopupProductList);
        mTopupProductGrid= (GridView) rootView.findViewById(R.id.topup_list);
        mTopupProductGrid.setAdapter(lTopupProductsAdapter);
        return rootView;
    }


}