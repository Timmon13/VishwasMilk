package vf.client.com.vishwasfarm.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnPauseSubscriptionDateChange;

/**
 * Created by amhule on 5/2/2017.
 */
public class CustomPauseSubscriptionDialog extends Dialog implements
        View.OnClickListener {

    private Activity mActivity;
    private Dialog mDialog;

    private TextView mStartDate, mEndDate;
    private Button mDone,mCancel;

    private final OnPauseSubscriptionDateChange mOnPauseChange;

    private ImageView mStartDatePicker;
    private ImageView mEndDatePicker;
    private String mSubscriptionId;

    LinearLayout mCalenderView;

    public CustomPauseSubscriptionDialog(Activity lActivity, String fSubscriptionId) {
        super(lActivity);
        // TODO Auto-generated constructor stub
        this.mActivity = lActivity;
        mOnPauseChange= (OnPauseSubscriptionDateChange) lActivity;
        mSubscriptionId=fSubscriptionId;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_subscriptions_pause_dialog);
        mStartDatePicker= (ImageView) findViewById(R.id.date_picker);
        mEndDatePicker= (ImageView) findViewById(R.id.date_picker2);
        mStartDate= (TextView) findViewById(R.id.date);
        mEndDate= (TextView) findViewById(R.id.date2);

        mCalenderView= (LinearLayout) findViewById(R.id.Calender_view);
        mDone= (Button) findViewById(R.id.done);
        mCancel= (Button) findViewById(R.id.cancel);

        mDone.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        mStartDatePicker.setOnClickListener(this);
        mEndDatePicker.setOnClickListener(this);
       // no = (Button) findViewById(R.id.btn_no);

       // no.setOnClickListener(this);

    }


    public void popupwindow(final boolean fCheck) {
        try {
            // We need to get the instance of the LayoutInflater

           /* LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
*/
            mCalenderView.setVisibility(View.VISIBLE);
            CalendarView calendar = (CalendarView) mCalenderView.findViewById(R.id.calendarID);

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
                    if(fCheck){
                        mStartDate.setText("" + day + "-" + (month+1) + "-" + year);
                    }
                    else{
                        mEndDate.setText("" + day + "-" + (month+1) + "-" + year);
                    }
                    mCalenderView.setVisibility(View.GONE);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_picker:

              popupwindow(true);
                break;
            case R.id.date_picker2:
                popupwindow(false);
                break;
            case R.id.done:
                mOnPauseChange.onPauseSubscriptionDateModified(mSubscriptionId,mStartDate.getText().toString(),mEndDate.getText().toString());
                dismiss();
                break;
            case R.id.cancel:
               dismiss();
                break;
//            case R.id.btn_no:
//                dismiss();
//                break;
            default:
                break;
        }
        //dismiss();
    }
}