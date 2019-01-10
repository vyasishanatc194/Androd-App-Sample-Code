package com.mysafetynet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;

import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyButton;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.ApiConstants;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : Payment Confirmation Screen
 * Description : This file have payment confirmation.
 */
public class PaymentConfirmScreen extends AppCompatActivity {

    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;
    @BindView(R.id.txtMessage)
    MySafetyText txtMessage;
    @BindView(R.id.txtTime)
    MySafetyText txtTime;
    @BindView(R.id.txtOrderId)
    MySafetyText txtOrderId;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.btnSubmit)
    MySafetyButton btnSubmit;
    @BindView(R.id.txtAmount)
    MySafetyText txtAmount;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_confirm_screen);
        ButterKnife.bind(this);
        hideItems();
        mBundle = getIntent().getExtras();


//        txtAmount.setText(String.format(Locale.getDefault(), "$ %s", mBundle.getString(ApiConstants.TAGS.amount)));
        txtMessage.setText(mBundle.getString(ApiConstants.TAGS.message));
//        txtTime.setText(mBundle.getString(ApiConstants.TAGS.created_at));
//        txtOrderId.setText(mBundle.getString(ApiConstants.TAGS.transaction_id));
    }

    private void hideItems() {
        imbgBack.setVisibility(View.INVISIBLE);
        imbgDone.setVisibility(View.INVISIBLE);
        txtTitle.setText("PAYMENT CONFIRMATION");

    }

    @OnClick({R.id.imbgBack, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imbgBack:
                break;
            case R.id.btnSubmit:
                Intent intent = null;
                intent = new Intent(PaymentConfirmScreen.this,ParentTabActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }
}
