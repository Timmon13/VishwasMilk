package vf.client.com.vishwasfarm.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnFragmentChange;
import vf.client.com.vishwasfarm.dialogs.CustomProductDialog;
import vf.client.com.vishwasfarm.fragments.ProductDetailsFragment;
import vf.client.com.vishwasfarm.model.VishwasProduct;
import vf.client.com.vishwasfarm.utility.ImageLoader;

/**
 * Created by Akash on 01-05-2017.
 */

public class ProductsAdapter extends ArrayAdapter<VishwasProduct> implements View.OnClickListener {

    Activity context;
    List<VishwasProduct> mProductList;
    public ImageLoader imageLoader;
    OnFragmentChange mOnFragmentChange;
    CustomProductDialog mCustomDialog;

    public ProductsAdapter(Activity context, int resourceId,
                                                              List<VishwasProduct> items) {
        super(context, resourceId, items);
        this.context = context;
        mProductList =items;
        imageLoader=new ImageLoader(context);
        mOnFragmentChange= (OnFragmentChange) context;
    }



    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.products_adapter, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.prod_title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.prod_img);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.imageView.setTag(position);
        //mProductImageView.setPadding(2, 2, 2, 2);

        imageLoader.DisplayImage(mProductList.get(position).getProductImage_URL(),  holder.imageView);
          /*  imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.drawable.ic_dairy));*/
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //mProductImageView.setBackgroundColor(R.color.colorPrimary);
        holder.txtTitle.setPadding(2, 2, 2, 2);
        holder.txtTitle.setText(""+mProductList.get(position).getProductName());
        holder.imageView.setOnClickListener(this);
        //holder.imageView.setImageResource();

        return convertView;
    }

    @Override
    public void onClick(View view) {
        mCustomDialog=new CustomProductDialog(mProductList.get(Integer.parseInt(view.getTag().toString())),context,new ProductDetailsFragment(Integer.parseInt(view.getTag().toString())));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mCustomDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        mCustomDialog.show();
        mCustomDialog.getWindow().setAttributes(lp);
        //mOnFragmentChange.onFragmentChange(new ProductDetailsFragment());

    }

}