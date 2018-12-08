package vf.client.com.vishwasfarm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.fragments.DiaryFragment;

/**
 * Created by Akash on 01-05-2017.
 */

public class MyDiaryAdapter extends RecyclerView.Adapter<MyDiaryAdapter.CustomViewHolder>   {

    Context context;

    public MyDiaryAdapter(DiaryFragment fFragment, int resourceId) {
        this.context = fFragment.getActivity().getApplicationContext();
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View lItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_diary_adapter, parent, false);
        return new CustomViewHolder(lItemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        int p=position;
        holder.txtItemName1.setText("1234");
        holder.txtItemName2.setText("1234");
        holder.txtItemName3.setText("1234");

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    /**
     * View lHolder for Adapter
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate;
        TextView txtMonth;
        TextView txtItemName1,txtItemName2,txtItemName3,txtItemName4;
        TextView txtItemQty1,txtItemQty2,txtItemQty3,txtItemQty4;
        TextView txtItemPrice1,txtItemPrice2,txtItemPrice3,txtItemPrice4,txtFinalItemPrice;

        public CustomViewHolder(View fItemView) {
            super(fItemView);
            txtDate = (TextView) fItemView.findViewById(R.id.diary_date);
            txtMonth = (TextView) fItemView.findViewById(R.id.dairy_month);

            txtItemName1 = (TextView) fItemView.findViewById(R.id.item_name1);
            txtItemName2 = (TextView) fItemView.findViewById(R.id.item_name2);
            txtItemName3 = (TextView) fItemView.findViewById(R.id.item_name3);

            txtItemQty1 = (TextView) fItemView.findViewById(R.id.item_qty1);
            txtItemQty2 = (TextView) fItemView.findViewById(R.id.item_qty2);
            txtItemQty3 = (TextView) fItemView.findViewById(R.id.item_qty3);

            txtItemPrice1 = (TextView) fItemView.findViewById(R.id.item_total1);
            txtItemPrice2 = (TextView) fItemView.findViewById(R.id.item_total2);
            txtItemPrice3 = (TextView) fItemView.findViewById(R.id.item_total3);

            txtFinalItemPrice = (TextView) fItemView.findViewById(R.id.final_item_total);

        }
    }
}
