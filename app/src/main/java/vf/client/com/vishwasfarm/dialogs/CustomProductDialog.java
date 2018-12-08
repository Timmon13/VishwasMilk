package vf.client.com.vishwasfarm.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnFragmentChange;
import vf.client.com.vishwasfarm.model.VishwasProduct;
import vf.client.com.vishwasfarm.utility.ImageLoader;

/**
 * Created by amhule on 5/2/2017.
 */
public class CustomProductDialog extends Dialog implements
        View.OnClickListener {

    private Activity mActivity;
    private Fragment mFragment;
    private Dialog mDialog;
    private Button mConfirmation;
    private ImageView mProductImage;
    private TextView mProductname, mProductDisc;

    private OnFragmentChange mOnFragmentChange;
    private ImageLoader imageLoader;
    VishwasProduct mProduct;

     public CustomProductDialog(VishwasProduct lProduct, Activity lActivity, Fragment lFragment) {
        super(lActivity);
        // TODO Auto-generated constructor stub
        this.mActivity = lActivity;
        this.mFragment=lFragment;
        mOnFragmentChange= (OnFragmentChange) lActivity;
        imageLoader=new ImageLoader(mActivity);
        mProduct=lProduct;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_product);
        mConfirmation = (Button) findViewById(R.id.dialog_ok);
        mProductImage= (ImageView) findViewById(R.id.dialog_img);
        mProductname= (TextView) findViewById(R.id.product_name);
        mProductDisc = (TextView) findViewById(R.id.product_disc);
        imageLoader.DisplayImage(mProduct.getProductImage_URL(), mProductImage);
        mProductname.setText(mProduct.getProductName());
        mProductDisc.setText(mProduct.getProductDescription());

        mConfirmation.setOnClickListener(this);
       // no = (Button) findViewById(R.id.btn_no);

       // no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_ok:
                mOnFragmentChange.onFragmentChange(mFragment);
                dismiss();
                break;
//            case R.id.btn_no:
//                dismiss();
//                break;
            default:
                break;
        }
        dismiss();
    }
}