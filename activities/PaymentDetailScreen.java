package com.mysafetynet.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyButton;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;

/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : Payment Detail Screen
 * Description : This file have payment detail.
 */

public class PaymentDetailScreen extends AppCompatActivity {

    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;
    @BindView(R.id.txtChildName)
    MySafetyText txtChildName;
    @BindView(R.id.txtChildyear)
    MySafetyText txtChildyear;
    @BindView(R.id.txtChildGender)
    MySafetyText txtChildGender;
    @BindView(R.id.txtParentName)
    MySafetyText txtParentName;
    @BindView(R.id.txtChildBithdate)
    MySafetyText txtChildBithdate;
    @BindView(R.id.txtSchoolname)
    MySafetyText txtSchoolname;
    @BindView(R.id.txtSchoolDistrictNumber)
    MySafetyText txtSchoolDistrictNumber;
    @BindView(R.id.txtStatename)
    MySafetyText txtStatename;
    @BindView(R.id.txtAmount)
    MySafetyText txtAmount;
    @BindView(R.id.txtDollar)
    MySafetyText txtDollar;
    @BindView(R.id.txtType)
    MySafetyText txtType;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.imvProfile)
    CircleImageView imvProfile;
    @BindView(R.id.btnSubmit)
    MySafetyButton btnSubmit;
    @BindView(R.id.txtOneTimeAmount)
    MySafetyText txtOneTimeAmount;
    private AlertDialog mAlertDialog;
    private Bundle mBundle;
    private String plan_id = "", amount = "",OneTimeAmount="", fname, lName, dob, age, gender, schoolName, schoolDistricNumber, statename, userName, password, cpassword, image, type;
    private ApiService mApiService;
    private AppPref mAppPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_detail_screen);
        ButterKnife.bind(this);
        initDialog();
        mAppPref = new AppPref(this);
        mApiService = APIClient.getService();
        mBundle = getIntent().getExtras();
        plan_id = getIntent().getStringExtra(ApiConstants.TAGS.plan_id);
        amount = getIntent().getStringExtra(ApiConstants.TAGS.amount);
        OneTimeAmount = getIntent().getStringExtra(ApiConstants.TAGS.one_time_fee);
        type = getIntent().getStringExtra(ApiConstants.TAGS.type);
        setInfo();
        hideItems();
    }

    private void hideItems() {
        imbgDone.setVisibility(View.INVISIBLE);
        txtTitle.setText("PAYMENT DETAIL");
    }

    private void setInfo() {
        fname = mBundle.getString(ApiConstants.TAGS.first_name);
        lName = mBundle.getString(ApiConstants.TAGS.last_name);
        dob = mBundle.getString(ApiConstants.TAGS.dob);
        age = mBundle.getString(ApiConstants.TAGS.age);
        gender = mBundle.getString(ApiConstants.TAGS.gender);
        schoolName = mBundle.getString(ApiConstants.TAGS.school_name);
        schoolDistricNumber = mBundle.getString(ApiConstants.TAGS.school_district_no);
        statename = mBundle.getString(ApiConstants.TAGS.state);
        userName = mBundle.getString(ApiConstants.TAGS.username);
        password = mBundle.getString(ApiConstants.TAGS.password);
        cpassword = mBundle.getString(ApiConstants.TAGS.confirm_password);
        image = mBundle.getString(ApiConstants.TAGS.image);
        displayImage(image);
        txtChildName.setText(String.format(Locale.getDefault(), "%s %s", fname, lName));
        txtChildyear.setText(String.format(Locale.getDefault(), "%s Year", age));
        txtChildGender.setText(String.format(Locale.getDefault(), "%s", gender));
        txtParentName.setText(mAppPref.getName());
        txtChildBithdate.setText(String.format(Locale.getDefault(), "%s", dob));
        txtSchoolname.setText(String.format(Locale.getDefault(), "%s", schoolName));
        txtSchoolDistrictNumber.setText(String.format(Locale.getDefault(), "%s", schoolDistricNumber));
        txtStatename.setText(String.format(Locale.getDefault(), "%s", statename));
        txtAmount.setText(String.format(Locale.getDefault(), "%s", amount));
        txtOneTimeAmount.setText(String.format(Locale.getDefault(), "%s", OneTimeAmount));
        if (type.equalsIgnoreCase("yearly")) {
            txtType.setText("Per Year");
        } else if (type.equalsIgnoreCase("monthly")) {
            txtType.setText("Per Month");
        } else if (type.equalsIgnoreCase("free")) {
            txtType.setText("FREE");
        }


    }

    private void initDialog() {
        mAlertDialog = new SpotsDialog.Builder()
                .setContext(this)
                .setCancelable(false)
                .setMessage("Loading")
                .build();
    }

    private void displayImage(String imagePath) {

        Glide.with(PaymentDetailScreen.this)
                .asBitmap()
                .load(imagePath)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.girl)
                        .error(R.drawable.girl))
                .into(imvProfile);
    }

    @OnClick({R.id.imbgBack, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imbgBack:
                onBackPressed();
                break;
            case R.id.btnSubmit:
                Intent intent = new Intent(this, StripePaymentActivity.class);
                intent.putExtra(ApiConstants.TAGS.plan_id, plan_id);
                intent.putExtra(ApiConstants.TAGS.amount, amount);
                intent.putExtra(ApiConstants.TAGS.type, type);
                intent.putExtras(mBundle);
                startActivity(intent);
                break;
        }
    }
}
