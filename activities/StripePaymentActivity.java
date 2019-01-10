package com.example.userlistexample;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.gson.JsonElement;
import com.mysafetynet.R;
import com.mysafetynet.adapters.CustomSpinnerAdapter;
import com.mysafetynet.customs.CreditCardEdittext;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;
import com.mysafetynet.utils.OtherCardTextWatcher;
import com.mysafetynet.utils.Util;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : Payment Confirmation Screen
 * Description : This file have Checkout Payment Screen .
 */

public class StripePaymentActivity extends AppCompatActivity {

    private static final String TAG = StripePaymentActivity.class.getSimpleName();
    @BindView(R.id.edtCreditCardName)
    TextInputEditText edtCreditCardName;
    @BindView(R.id.edtCreditCardNumber)
    CreditCardEdittext edtCreditCardNumber;
    @BindView(R.id.imgbtnMonth)
    ImageButton imgbtnMonth;
    @BindView(R.id.spMonth)
    Spinner spMonth;
    @BindView(R.id.spYear)
    Spinner spYear;
    @BindView(R.id.edtCreditCardCvv)
    TextInputEditText edtCreditCardCvv;
    @BindView(R.id.btnCheckout)
    Button btnCheckout;
    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.emptyView)
    View emptyView;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;
    // private String publishableKey = "pk_test_K2ZO7DcZcQKuJ4PQjO9XOvRT";
    private String publishableKey = "pk_live_0KAGPmAFwdAh1rPRrHkDkCke";
    private String errorMessage = "";
    private ArrayList<String> monthList, yearList;
    private ApiService mApiService;
    private AlertDialog mAlertDialog;
    private Bundle mBundle;
    private String plan_id = "", amount = "", fname, lName, dob, age, gender, schoolName, schoolDistricNumber,
            statename, userName, password, cpassword, image, type, phone;
    private AppPref mAppPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_payment);
        ButterKnife.bind(this);
        mAppPref = new AppPref(this);
        monthList = new ArrayList<>();
        yearList = new ArrayList<>();
        mBundle = getIntent().getExtras();
        plan_id = getIntent().getStringExtra(ApiConstants.TAGS.plan_id);
        amount = getIntent().getStringExtra(ApiConstants.TAGS.amount);
        type = getIntent().getStringExtra(ApiConstants.TAGS.type);
        setInfo();
        initMonths();
        mApiService = APIClient.getService();
        initDialog();
        setRegisterListener();
        txtTitle.setText("PAYMENT");
    }

    private void setInfo() {
        fname = mBundle.getString(ApiConstants.TAGS.first_name);
        lName = mBundle.getString(ApiConstants.TAGS.last_name);
        dob = mBundle.getString(ApiConstants.TAGS.dob);
        age = mBundle.getString(ApiConstants.TAGS.age);
        phone = mBundle.getString(ApiConstants.TAGS.phone);
        gender = mBundle.getString(ApiConstants.TAGS.gender);
        schoolName = mBundle.getString(ApiConstants.TAGS.school_name);
        schoolDistricNumber = mBundle.getString(ApiConstants.TAGS.school_district_no);
        statename = mBundle.getString(ApiConstants.TAGS.state);
        userName = mBundle.getString(ApiConstants.TAGS.username);
        password = mBundle.getString(ApiConstants.TAGS.password);
        cpassword = mBundle.getString(ApiConstants.TAGS.confirm_password);
        image = mBundle.getString(ApiConstants.TAGS.image);

    }

    private void initDialog() {
        mAlertDialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).setMessage("Loading").build();
    }

    private void setRegisterListener() {
        edtCreditCardNumber.setTextWatcher(new OtherCardTextWatcher(edtCreditCardNumber));
    }

    private void initMonths() {
        for (int i = 1; i < 13; i++) {
            monthList.add(String.valueOf(i));
        }
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i < 2062; i++) {
            yearList.add(String.valueOf(i));
        }
        CustomSpinnerAdapter adap1 = new CustomSpinnerAdapter(this, R.layout.content_spinner_list_item, monthList,
                getResources().getString(R.string.fontOpenSansRegular));
        spMonth.setAdapter(adap1);
        CustomSpinnerAdapter adap = new CustomSpinnerAdapter(this, R.layout.content_spinner_list_item, yearList,
                getResources().getString(R.string.fontOpenSansRegular));
        spYear.setAdapter(adap);
    }

    private void generateToken(Card card) {
        Stripe stripe = new Stripe(this);
        try {
            stripe.createToken(card, publishableKey, new TokenCallback() {
                @Override
                public void onError(Exception error) {
                    error.printStackTrace();
                    Util.showToast(StripePaymentActivity.this, error.getMessage());
                    Log.e(TAG, "onError: " + error.getMessage());
                    mAlertDialog.dismiss();
                }

                @Override
                public void onSuccess(Token token) {
                    Log.e(TAG, "onSuccess: " + token.getId());
                    doPostAddChild(token.getId());
                }
            });
        } catch (Exception stripeEx) {
            errorMessage = stripeEx.getMessage();
        }
    }

    private void doPostAddChild(String tokenId) {
        // dob = Util.convertFormat(dob, Util.FORMAT_DDMMYYYY, Util.FORMAT_YYYYMMDD);
        File file = new File(image);
        if (file.exists()) {
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

            mApiService.doChildAdd(mAppPref.getAuthToken(), userName, fname, lName, age, gender, dob, phone, schoolName,
                    schoolDistricNumber, statename, ApiConstants.USER_CHILD, plan_id, amount, tokenId, "", password,
                    cpassword, body).enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            mAlertDialog.dismiss();
                            if (response.isSuccessful()) {
                                try {
                                    Log.e(TAG, "onResponse: " + response.body().getAsString());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                JsonElement body = response.body();
                                int status = body.getAsJsonObject().get(ApiConstants.TAGS.status).getAsInt();
                                String message = body.getAsJsonObject().get(ApiConstants.TAGS.message).getAsString();
                                switch (status) {
                                case 200:

                                    // String transaction_id =
                                    // body.getAsJsonObject().get(ApiConstants.TAGS.result).getAsJsonObject().get("order").getAsJsonObject().get("subscription_id").getAsString();
                                    // String amount =
                                    // body.getAsJsonObject().get(ApiConstants.TAGS.result).getAsJsonObject().get("order").getAsJsonObject().get("amount").getAsString();
                                    // String created_at =
                                    // body.getAsJsonObject().get(ApiConstants.TAGS.result).getAsJsonObject().get("order").getAsJsonObject().get("created_at").getAsString();

                                    Bundle bundle = new Bundle();
                                    bundle.putString(ApiConstants.TAGS.message, message);
                                    // bundle.putString(ApiConstants.TAGS.transaction_id, "");
                                    // bundle.putString(ApiConstants.TAGS.amount, amount);
                                    // bundle.putString(ApiConstants.TAGS.created_at, "");

                                    Intent intent = null;
                                    intent = new Intent(StripePaymentActivity.this, PaymentConfirmScreen.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    // Log.e(TAG, response.body().toString());
                                    break;
                                default:
                                    Util.showToast(StripePaymentActivity.this, message);
                                    break;
                                }
                            } else {
                                try {
                                    Log.e(TAG, "onResponse: " + response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Util.showToast(StripePaymentActivity.this, "Something went wrong Please try again");
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            t.printStackTrace();
                            mAlertDialog.dismiss();
                        }
                    });
        }

    }

    @OnClick({ R.id.btnCheckout, R.id.imbgBack })
    public void onViewClicked(View view) {
        switch (view.getId()) {
        case R.id.btnCheckout:
            String credit_name = edtCreditCardName.getText().toString().trim();
            String credit_card = edtCreditCardNumber.getText().toString().trim();
            String credit_month = spMonth.getSelectedItem().toString();
            String credit_year = spYear.getSelectedItem().toString();
            String credit_cvv = edtCreditCardCvv.getText().toString().trim();
            if (TextUtils.isEmpty(credit_name)) {
                edtCreditCardName.setError(getString(R.string.errCardName));
                edtCreditCardName.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            } else if (TextUtils.isEmpty(credit_card)) {
                edtCreditCardNumber.setError(getString(R.string.errCardNumber));
                edtCreditCardNumber.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            } else if (TextUtils.isEmpty(credit_cvv)) {
                edtCreditCardCvv.setError(getString(R.string.errCardCvv));
                edtCreditCardCvv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            } else if (!Util.isValidCardCvv(credit_cvv)) {
                edtCreditCardCvv.setError(getString(R.string.errCardValidCvv));
                edtCreditCardCvv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
            } else {
                edtCreditCardNumber.setError(null);
                edtCreditCardCvv.setError(null);
                Card card = new Card(credit_card, Integer.parseInt(credit_month), Integer.parseInt(credit_year),
                        credit_cvv);
                if (!card.validateNumber()) {
                    edtCreditCardNumber.setError(getString(R.string.errCardValidNumber));
                    edtCreditCardNumber.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
                } else if (!card.validateCVC()) {
                    edtCreditCardCvv.setError(getString(R.string.errCardValidCvvOriginal));
                    edtCreditCardCvv.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake_error));
                } else {
                    edtCreditCardNumber.setError(null);
                    edtCreditCardCvv.setError(null);
                    generateToken(card);
                    mAlertDialog.show();

                }

            }
            break;
        case R.id.imbgBack:
            onBackPressed();
            break;
        }

    }
}
