package vf.client.com.vishwasfarm.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import vf.client.com.vishwasfarm.R;
import vf.client.com.vishwasfarm.ServiceListener.OnFragmentChange;
import vf.client.com.vishwasfarm.model.VishwasUser;
import vf.client.com.vishwasfarm.service.UpdateCustProfile;
import vf.client.com.vishwasfarm.utility.ImageLoader;

import static android.app.Activity.RESULT_OK;
import static vf.client.com.vishwasfarm.utility.VishwasConstants.UserData;




public class ProfileFragment extends Fragment  {
    private static final int RESULT_LOAD_IMAGE=1;

    Bundle lProductBundle;
    public ImageLoader imageLoader;

    TextView mUsrName, mMobileNo, mEmailId, mAddr;
    CheckBox mSMS, mEmail;
    ImageView mProfileImage;
    LinearLayout inflatedChild;
    private VishwasUser mVishwasUser;
    Button mSave, mCancel;
    String str_YES = "Y";

    OnFragmentChange mOnFragmentChange;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        imageLoader = new ImageLoader(getActivity().getApplicationContext());
        mOnFragmentChange= (OnFragmentChange) getActivity();

        Bundle lProductBundle = getActivity().getIntent().getExtras();
        if (lProductBundle != null) {
            if (lProductBundle.getParcelable(UserData) != null) {
                mVishwasUser = lProductBundle.getParcelable(UserData);
                mUsrName = (TextView) rootView.findViewById(R.id.username);
                mMobileNo = (TextView) rootView.findViewById(R.id.txtphoneNo);
                mEmailId = (TextView) rootView.findViewById(R.id.txtEmailId);
                mAddr = (TextView) rootView.findViewById(R.id.txtAddress);
                mSave = (Button) rootView.findViewById(R.id.btnSave);
                mCancel = (Button) rootView.findViewById(R.id.btnCancel);
                mProfileImage = (ImageView) rootView.findViewById(R.id.profile_image);

                mSMS = (CheckBox) rootView.findViewById(R.id.chkSMS);
                mEmail = (CheckBox) rootView.findViewById(R.id.chkEmail);

                mUsrName.setText(mVishwasUser.getmUserFirstName() + " " + mVishwasUser.getmUserLastName());
                mMobileNo.setText(mVishwasUser.getmUserMobile());
                mEmailId.setText(mVishwasUser.getmUserEmail());
                mAddr.setText(mVishwasUser.getmUserAddress());

                imageLoader.DisplayImage(mVishwasUser.getmUserImageURL(), mProfileImage);

                if(str_YES.equals(mVishwasUser.getmFlagSMSAlert())){
                    mSMS.setChecked(true);}
                else{
                    mSMS.setChecked(false);}

                if(str_YES.equals(mVishwasUser.getmFlagEmailAlert())){
                    mEmail.setChecked(true);}
                else{
                    mEmail.setChecked(false);}

                mProfileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                    }
                });

                mSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bitmap bitmap = ((BitmapDrawable) mProfileImage.getDrawable()).getBitmap();
                        String flagSMSAlert, flagEmailAlert;
                        if (mSMS.isChecked()) { flagSMSAlert = "Y";} else {flagSMSAlert = "N";}
                        if (mEmail.isChecked()) { flagEmailAlert = "Y";} else {flagEmailAlert = "N";}
                        new UpdateCustProfile(getActivity(),mVishwasUser.getmCustId(),bitmap,flagSMSAlert, flagEmailAlert).execute();
                    }
                });

            }
        }
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            mProfileImage.setImageURI(data.getData());
        }
    }


}