package vf.client.com.vishwasfarm.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import vf.client.com.vishwasfarm.R;

import static vf.client.com.vishwasfarm.R.color.colorAccent;

/**
 * Created by amhule on 5/2/2017.
 */
public class CustomLoader extends Dialog {

    private Activity mActivity;
    private TextView status;
    private ImageView gifImg;
    private RelativeLayout layout;


    public CustomLoader(Activity lActivity) {
        super(lActivity);
        // TODO Auto-generated constructor stub
        this.mActivity = lActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.milk_loader);
        layout=findViewById(R.id.main_loader);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCancelable(false);

        gifImg= (ImageView) findViewById(R.id.loader_img);
        status= (TextView) findViewById(R.id.loading_status);
        Glide.with(mActivity)
                .load(R.drawable.loading)
                .into(gifImg);

    }

    public void setmState(boolean fState) {
        if(fState){
            Glide.with(mActivity)
                    .load(R.drawable.loading)
                    .into(gifImg);
            status.setText("Please Wait.. Loading!");
        }
        else{

            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                layout.setBackgroundDrawable(ContextCompat.getDrawable(mActivity, R.drawable.circular_plain_loader) );
            } else {
                layout.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.circular_plain_loader));
            }

            Glide.with(mActivity)
                    .load(R.drawable.loading_fail)
                    .into(gifImg);
            status.setTextColor(mActivity.getResources().getColor(colorAccent));
            status.setText("Something went wrong.. Please try later!");
            setCancelable(true);
        }
    }
}