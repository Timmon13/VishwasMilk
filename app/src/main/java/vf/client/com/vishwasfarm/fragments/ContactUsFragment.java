package vf.client.com.vishwasfarm.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnFragmentChange;
import vf.client.com.vishwasfarm.model.VishwasProduct;
import vf.client.com.vishwasfarm.model.VishwasProductList;
import vf.client.com.vishwasfarm.utility.ImageLoader;


public class ContactUsFragment extends Fragment {
    private VishwasProductList mVishwasProductList;
    private List<VishwasProduct> mProductList;
    Bundle lProductBundle;
    public ImageLoader imageLoader;
    ImageView mProductImageView;
    TextView mProductTitle;
    LinearLayout inflatedChild;

    OnFragmentChange mOnFragmentChange;

    public ContactUsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_contactus, container, false);
        return rootView;
    }
}