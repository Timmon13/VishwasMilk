package vf.client.com.vishwasfarm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnSubscriptionListClickListener;
import vf.client.com.vishwasfarm.fragments.SubscriptionFragment;
import vf.client.com.vishwasfarm.model.VishwasMySubscription;
import vf.client.com.vishwasfarm.utility.ImageLoader;

/**
 * Created by Akash on 01-05-2017.
 */

public class MySubscriptionAdapter extends RecyclerView.Adapter<MySubscriptionAdapter.CustomViewHolder>  implements View.OnClickListener {

    Context context;
    SubscriptionFragment mFragment;
    List<VishwasMySubscription> mSubscritionList;
    public ImageLoader imageLoader;
    private String mCodeCust;
    private OnSubscriptionListClickListener mOnLicensePlateClickListener;

    public MySubscriptionAdapter(SubscriptionFragment fFragment, int resourceId,
                                 List<VishwasMySubscription> items, String fCodeCust) {
        this.context = fFragment.getActivity().getApplicationContext();
        mOnLicensePlateClickListener= (OnSubscriptionListClickListener) fFragment.getActivity();
        mFragment = fFragment;
        mSubscritionList = items;
        imageLoader = new ImageLoader(context);
        mCodeCust = fCodeCust;
    }

    @Override
    public void onClick(View fView) {
        if (fView.getId() == R.id.prod_delete) {
            String lSubscriptionId = (String) fView.getTag();
            mOnLicensePlateClickListener.onDeleteOrderClick(lSubscriptionId);
        }
        else         if (fView.getId() == R.id.prod_modify) {
            String lSubscriptionId = (String) fView.getTag();
            mOnLicensePlateClickListener.onEditOrderClick(lSubscriptionId);
        }

        else         if (fView.getId() == R.id.prod_pause) {
            String lSubscriptionId = (String) fView.getTag();
            mOnLicensePlateClickListener.onPauseOrderClick(lSubscriptionId);
        }

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_subscriptions_adapter, parent, false);
        return new CustomViewHolder(lItemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.txtDesc.setText(mSubscritionList.get(position).getmProd_price());
        holder.txtTitle.setText(mSubscritionList.get(position).getmProd_name());

        //holder.imageView.setImageResource();
        imageLoader.DisplayImage(mSubscritionList.get(position).getmProd_url(), holder.imageView);
        //imageLoader.DisplayImage(mSubscritionList.get(position).getmProd_url(),  holder.btnDelete);

          /*  imageView.setImageBitmap(BitmapFactory.decodeResource(
                    getResources(), R.drawable.ic_dairy));*/
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
       // holder.btnDelete.setOnClickListener(this);
        //holder.mSwipeLeft.setOnClickListener(this);
      /*  holder.btnDelete.setOnClickListener(this);
        holder.btnModify.setOnClickListener(this);
        holder.btnPause.setOnClickListener(this);*/

        holder.btnDelete.setTag(mSubscritionList.get(position).getmSubs_id());
        holder.btnModify.setTag(mSubscritionList.get(position).getmSubs_id());

        holder.btnPause.setTag(mSubscritionList.get(position).getmSubs_id());


    }

    @Override
    public int getItemCount() {
        return mSubscritionList.size();
    }

    /**
     * View lHolder for Adapter
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
        ImageView btnDelete;
        ImageView btnModify;
        ImageView btnPause;

        public CustomViewHolder(View fItemView) {
            super(fItemView);
            txtDesc = (TextView) fItemView.findViewById(R.id.prod_disc);
            txtTitle = (TextView) fItemView.findViewById(R.id.prod_title);
            imageView = (ImageView) fItemView.findViewById(R.id.prod_img);
            btnDelete = (ImageView) fItemView.findViewById(R.id.prod_delete);
            btnPause = (ImageView) fItemView.findViewById(R.id.prod_pause);

            btnModify = (ImageView) fItemView.findViewById(R.id.prod_modify);

        }
    }
}
