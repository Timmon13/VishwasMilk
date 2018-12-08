package vf.client.com.vishwasfarm.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnFragmentChange;
import vf.client.com.vishwasfarm.model.TopupProduct;
import vf.client.com.vishwasfarm.model.TopupProductList;
import vf.client.com.vishwasfarm.model.VishwasMySubscription;
import vf.client.com.vishwasfarm.model.VishwasMySubscriptionList;
import vf.client.com.vishwasfarm.model.VishwasProduct;
import vf.client.com.vishwasfarm.model.VishwasProductList;
import vf.client.com.vishwasfarm.model.VishwasUser;
import vf.client.com.vishwasfarm.service.SubscriptionService;
import vf.client.com.vishwasfarm.utility.ImageLoader;

import static vf.client.com.vishwasfarm.utility.VishwasConstants.ProductData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.SubscritpionData;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.UserData;


public class ProductDetailsFragment extends Fragment implements View.OnClickListener {

    OnFragmentChange mOnFragmentChange;

    private TextView mProductTitle,mProductMRP,mUserAddress,mProductDescription,mProductQnty, mDate;
    private ImageView mProdcutImage, mDatePicker,mMinusQty,mPlusQty;
    private int mProductIndex;
    private int mMin_order_qty;
    private int mOrder_qty;
    private VishwasProductList mVishwasProductList;
    private TopupProductList mTopUpProductList;
    private VishwasMySubscriptionList mVishwasMySubscriptionList;
    private Button mSubscribe;

    private VishwasUser mVishwasUser;

    private List<TopupProduct> mTopUpList;
    private List<VishwasProduct> mProductList;
    private List<VishwasMySubscription> mMySubscriptionList;

    public ImageLoader imageLoader;
    private Spinner mFrequencySpinner;
    private Spinner mTimeSpinner;

    public ProductDetailsFragment() {  }

    @SuppressLint("ValidFragment")
    public ProductDetailsFragment(int lIndex) { mProductIndex=lIndex; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.product_details, container, false);
        imageLoader = new ImageLoader(getActivity().getApplicationContext());

        mProductTitle= (TextView) rootView.findViewById(R.id.product_name);
        mProductMRP= (TextView) rootView.findViewById(R.id.product_mrp);
        mUserAddress= (TextView) rootView.findViewById(R.id.user_address);
        mProductDescription= (TextView) rootView.findViewById(R.id.product_desc);
        mProductQnty= (TextView) rootView.findViewById(R.id.product_qnty);
        mDate=(TextView) rootView.findViewById(R.id.date);

        mProdcutImage= (ImageView) rootView.findViewById(R.id.product_img);
        mDatePicker= (ImageView) rootView.findViewById(R.id.date_picker);
        mMinusQty= (ImageView) rootView.findViewById(R.id.minus_button);
        mPlusQty= (ImageView) rootView.findViewById(R.id.plus_button);
        mSubscribe= (Button) rootView.findViewById(R.id.subscribe);

        mSubscribe.setOnClickListener(this);
        mMinusQty.setOnClickListener(this);
        mPlusQty.setOnClickListener(this);

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
            if (lProductBundle.getParcelable(UserData) != null) {
                mVishwasUser = lProductBundle.getParcelable(UserData);
            }
        }

        mMin_order_qty=Integer.parseInt(mProductList.get(mProductIndex).getMin_order_qty());
        mOrder_qty=mMin_order_qty;
        mProductTitle.setText(""+mProductList.get(mProductIndex).getProductName());
        mProductMRP.setText("₹ "+mProductList.get(mProductIndex).getMRP());
        mUserAddress.setText("NULL");
        mProductDescription.setText(""+mProductList.get(mProductIndex).getProductDescription());
        mProductQnty.setText(""+mOrder_qty);//mProductList.get(mProductIndex).getProductUnit_Quantity()

        imageLoader.DisplayImage(mProductList.get(mProductIndex).getProductImage_URL(), mProdcutImage);

        mDatePicker.setOnClickListener(this);

        mFrequencySpinner = (Spinner) rootView.findViewById(R.id.frequency_spinner);
        mTimeSpinner = (Spinner) rootView.findViewById(R.id.time_slot);

        String[] frequency = {"Daily","Monthly","Weekends"};
        String[] timeSlot = {"Morning","Evening"};
        ArrayAdapter<CharSequence> FrequncyAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner_text, frequency );
        ArrayAdapter<CharSequence> TimeAdapter = new ArrayAdapter<CharSequence>(getActivity(), R.layout.spinner_text, timeSlot );

        FrequncyAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        TimeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);

        mFrequencySpinner.setAdapter(FrequncyAdapter);
        mTimeSpinner.setAdapter(TimeAdapter);


        return rootView;
    }

    public void popupwindow() {
        try {
            // We need to get the instance of the LayoutInflater

            LayoutInflater inflater = (LayoutInflater) getActivity()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout = inflater.inflate(R.layout.calender_popup,
                    (ViewGroup) getActivity().findViewById(R.id.myLayout));

            final PopupWindow pwindo = new PopupWindow(layout, 1200, 900, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            CalendarView calendar = (CalendarView) layout.findViewById(R.id.calendarID);

            // sets whether to show the week number.
            calendar.setShowWeekNumber(false);

            // sets the first day of week according to Calendar.
            // here we set Monday as the first day of the Calendar
            calendar.setFirstDayOfWeek(2);

                     //sets the listener to be notified upon selected date change.
            calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                //show the selected date as a toast
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                    mDate.setText("" + day + "/" + month + "/" + year);
                    pwindo.dismiss();
                }
            });
    } catch (Exception e) {
        e.printStackTrace();
    }

}


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.minus_button:
                if(mOrder_qty>mMin_order_qty){
                    mOrder_qty=mOrder_qty-mMin_order_qty;
                    mProductQnty.setText(""+mOrder_qty);
                }
                break;
            case R.id.plus_button:
                mOrder_qty=mOrder_qty+mMin_order_qty;
                mProductQnty.setText(""+mOrder_qty);
                break;
            case R.id.date_picker:
                popupwindow();
                break;
            case R.id.subscribe:
                Date date = null;
                try {
                    date  = new SimpleDateFormat("dd/MM/yyyy").parse(mDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String lDate=new SimpleDateFormat("yyyy-MM-dd").format(date).toString();
                new SubscriptionService(getActivity(),mVishwasUser.getmCustId(),mVishwasUser.getmUserMobile(),mProductList.get(mProductIndex).getProductId(),
                        mProductQnty.getText().toString(),mFrequencySpinner.getSelectedItem().toString(),lDate,mTimeSpinner.getSelectedItem().toString()).execute();
                break;


        }
    }
}