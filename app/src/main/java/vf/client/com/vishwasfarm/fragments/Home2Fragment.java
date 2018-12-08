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

import static vf.client.com.vishwasfarm.utility.VishwasConstants.ProductData;


public class Home2Fragment extends Fragment implements View.OnClickListener {
    private VishwasProductList mVishwasProductList;
    private List<VishwasProduct> mProductList;
    Bundle lProductBundle;
    public ImageLoader imageLoader;
    ImageView mProductImageView;
    TextView mProductTitle;
    LinearLayout inflatedChild;

    OnFragmentChange mOnFragmentChange;

    public Home2Fragment() {
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
        }




       // LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.linear);

        for (int i = 0; i < mProductList.size(); i++) {
            inflatedChild= (LinearLayout) inflater.inflate(R.layout.product_image,null);
            mProductImageView= (ImageView) inflatedChild.findViewById(R.id.product_image);
            mProductTitle= (TextView) inflatedChild.findViewById(R.id.product_title);
            //mProductImageView= new ImageView(getActivity());
           // mProductTitle=new TextView(getActivity());
            LinearLayout lChild=new LinearLayout(getActivity());
            LinearLayout.LayoutParams lChildLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
            lChildLayoutParams.setMargins(20, 0, 20, 0);
            inflatedChild.setLayoutParams(lChildLayoutParams);
            //lChild.setOrientation(LinearLayout.VERTICAL);

           // LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(200, 200);
            // lParams.setMargins(15,15,15,15);
            //mProductImageView.setLayoutParams(lParams);
            inflatedChild.setTag(i);
            //mProductImageView.setPadding(2, 2, 2, 2);

            imageLoader.DisplayImage(mProductList.get(i).getProductImage_URL(), mProductImageView);
          /*  imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.drawable.ic_dairy));*/
            mProductImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //mProductImageView.setBackgroundColor(R.color.colorPrimary);
            mProductTitle.setPadding(2, 2, 2, 2);
            mProductTitle.setText(""+mProductList.get(i).getProductName());
            if(inflatedChild.getParent()!=null)
                ((ViewGroup)inflatedChild.getParent()).removeView(inflatedChild);
            lChild.addView(inflatedChild);
            //lChild.addView(mProductTitle);
            inflatedChild.setOnClickListener(this);
          //  layout.addView(lChild);

        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(Integer.parseInt(v.getTag().toString())==0){
            mOnFragmentChange.onFragmentChange(new ProductDetailsFragment());
        }
        else if(Integer.parseInt(v.getTag().toString())==1){
            mOnFragmentChange.onFragmentChange(new ProductDetailsFragment());
        }
    }
}