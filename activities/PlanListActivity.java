package com.example.userlistexample;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mysafetynet.Model.PlanListModel;
import com.mysafetynet.R;
import com.mysafetynet.adapters.SubPlanListAdapter;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : Payment Confirmation Screen
 * Description : This file have payment plan list .
 */

public class PlanListActivity extends AppCompatActivity implements SubPlanListAdapter.OnSubPlanSelectListener {

    private static final String TAG = PlanListActivity.class.getSimpleName();
    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;
    @BindView(R.id.list)
    RecyclerView list;

    private AlertDialog mAlertDialog;
    private ArrayList<PlanListModel.Data> sectionModelArrayList;
    private SubPlanListAdapter mChildListAdapter;
    private ApiService mApiService;
    private AppPref mAppPref;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_list);
        ButterKnife.bind(this);
        sectionModelArrayList = new ArrayList<>();
        mAppPref = new AppPref(this);
        mApiService = APIClient.getService();
        mBundle = getIntent().getExtras();
        initDialog();
        initOrders();
        getOrders();
        hideItems();
    }

    private void hideItems() {
        imbgDone.setVisibility(View.INVISIBLE);
        txtTitle.setText("SUBSCRIPTION LIST");
    }

    private void initDialog() {
        mAlertDialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).setMessage("Loading").build();
    }

    private void getOrders() {
        mAlertDialog.show();
        mApiService.doPlanList(mAppPref.getAuthToken()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mAlertDialog.dismiss();
                if (response.isSuccessful()) {
                    PlanListModel model = new Gson().fromJson(response.body(), PlanListModel.class);
                    switch (model.getStatus()) {
                    case "200":
                        sectionModelArrayList.addAll(model.getResult().getData());
                        mChildListAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                    }
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                mAlertDialog.dismiss();
                t.printStackTrace();
            }
        });
    }

    private void initOrders() {
        list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        list.setLayoutManager(linearLayoutManager);
        mChildListAdapter = new SubPlanListAdapter(sectionModelArrayList, this);
        list.setAdapter(mChildListAdapter);
    }

    @OnClick(R.id.imbgBack)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onSubPlanSelect(int position, PlanListModel.Data data) {
        Intent intent = new Intent(this, PaymentDetailScreen.class);
        intent.putExtra(ApiConstants.TAGS.plan_id, data.getId());
        intent.putExtra(ApiConstants.TAGS.amount, data.getActual_amount());
        intent.putExtra(ApiConstants.TAGS.type, data.getType());
        intent.putExtra(ApiConstants.TAGS.one_time_fee, data.getOne_time_fee());
        intent.putExtras(mBundle);
        startActivity(intent);
    }
}
