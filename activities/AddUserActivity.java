package com.example.userlistexample;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mysafetynet.Model.LogoutModel;
import com.mysafetynet.R;
import com.mysafetynet.customs.MySafetyButton;
import com.mysafetynet.customs.MySafetyText;
import com.mysafetynet.network.APIClient;
import com.mysafetynet.network.ApiConstants;
import com.mysafetynet.network.ApiService;
import com.mysafetynet.utils.AppPref;
import com.mysafetynet.utils.PermissionUtil;
import com.mysafetynet.utils.Util;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Citrusbug. 
 * User: Ishan Vyas 
 * Date: 10/01/19 
 * Time: 12:00 PM 
 * Title : Add User Activity 
 * Description : This file have all basic information of user.
 */

public class AddUserActivity extends AppCompatActivity {

    @BindView(R.id.txtUpload)
    MySafetyText txtUpload;
    @BindView(R.id.edtFirstName)
    TextInputEditText edtFirstName;
    @BindView(R.id.edtLastName)
    TextInputEditText edtLastName;
    @BindView(R.id.edtBirthdate)
    TextInputEditText edtBirthdate;
    @BindView(R.id.edtAge)
    TextInputEditText edtAge;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFemale)
    RadioButton rbFemale;
    @BindView(R.id.rgGender)
    RadioGroup rgGender;
    @BindView(R.id.edtSchoolName)
    TextInputEditText edtSchoolName;
    @BindView(R.id.edtSchoolDistrictNumber)
    TextInputEditText edtSchoolDistrictNumber;
    @BindView(R.id.edtPhoneNumber)
    TextInputEditText edtPhoneNumber;
    @BindView(R.id.edtUserName)
    TextInputEditText edtUserName;
    @BindView(R.id.edtPassword)
    TextInputEditText edtPassword;
    @BindView(R.id.edtConfirmPassword)
    TextInputEditText edtConfirmPassword;
    @BindView(R.id.cardview)
    CardView cardview;
    @BindView(R.id.imvProfile)
    CircleImageView imvProfile;
    @BindView(R.id.btnSubmit)
    MySafetyButton btnSubmit;
    @BindView(R.id.edtStateName)
    TextInputEditText edtStateName;
    @BindView(R.id.imbgBack)
    ImageButton imbgBack;
    @BindView(R.id.txtTitle)
    MySafetyText txtTitle;
    @BindView(R.id.imbgDone)
    ImageButton imbgDone;
    private String firstName = "", lastName = "", birthDate = "", childAge = "", schoolName = "",
            schoolDistrictNumber = "", phoneNumber = "", userName = "", password = "", confirmPassword = "",
            gender = "male", statename = "", imagePath = "";
    private int mYear, mMonth, mDay;
    private AlertDialog mAlertDialog;
    String screen_name = "", plan_id = "", amount = "", type = "", one_time_fee = "";

    private ApiService mApiService;
    private AppPref mAppPref;
    private boolean isExistsChild = false, isExistsPhone = false;
    private long mLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        ButterKnife.bind(this);
        hideItems();
        initDialog();
        mAppPref = new AppPref(this);
        mApiService = APIClient.getService();
        setRegisterListener();

        if (getIntent().getExtras() != null) {
            screen_name = getIntent().getStringExtra(ApiConstants.TAGS.Screen_name);
            plan_id = getIntent().getStringExtra(ApiConstants.TAGS.plan_id);
            amount = getIntent().getStringExtra(ApiConstants.TAGS.amount);
            type = getIntent().getStringExtra(ApiConstants.TAGS.type);
            one_time_fee = getIntent().getStringExtra(ApiConstants.TAGS.one_time_fee);
        }

    }

    private void setRegisterListener() {
        edtUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    doExistsChild(edtUserName.getText().toString().trim());
                }
            }
        });
        edtPhoneNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    doExistsPhone(edtPhoneNumber.getText().toString().trim());
                }
            }
        });
    }

    private void hideItems() {
        imbgDone.setVisibility(View.INVISIBLE);
        txtTitle.setText(getResources().getString(R.string.add_child));
    }

    private void initDialog() {
        mAlertDialog = new SpotsDialog.Builder().setContext(this).setCancelable(false).setTheme(R.style.SpotsDialog)
                .build();
    }

    /**
     * This function will apply click events on Screen Compoments
     */
    @OnClick({ R.id.txtUpload, R.id.btnSubmit, R.id.edtBirthdate, R.id.imbgBack, R.id.imvProfile })
    public void onViewClicked(View view) {
        switch (view.getId()) {
        case R.id.imbgBack:
            onBackPressed();
            break;
        case R.id.btnSubmit:
            firstName = edtFirstName.getText().toString().trim();
            lastName = edtLastName.getText().toString().trim();
            birthDate = edtBirthdate.getText().toString().trim();
            schoolName = edtSchoolName.getText().toString().trim();
            schoolDistrictNumber = edtSchoolDistrictNumber.getText().toString().trim();
            statename = edtStateName.getText().toString().trim();
            phoneNumber = edtPhoneNumber.getText().toString().trim();
            userName = edtUserName.getText().toString().trim();
            password = edtPassword.getText().toString().trim();
            confirmPassword = edtConfirmPassword.getText().toString().trim();
            gender = rgGender.getCheckedRadioButtonId() == R.id.rbMale ? "male" : "female";
            if (TextUtils.isEmpty(firstName)) {
                edtFirstName.requestFocus();
                edtFirstName.setError(getResources().getString(R.string.errFname));
            } else if (TextUtils.isEmpty(lastName)) {
                edtLastName.requestFocus();
                edtLastName.setError(getResources().getString(R.string.errLname));
            } else if (TextUtils.isEmpty(birthDate)) {
                edtBirthdate.requestFocus();
                edtBirthdate.setError(getResources().getString(R.string.errBdate));
            } else if (TextUtils.isEmpty(childAge)) {
                edtAge.requestFocus();
                edtAge.setError(getResources().getString(R.string.errAge));
            } else if (Integer.parseInt(childAge) > 18) {
                edtAge.requestFocus();
                edtAge.setError(getResources().getString(R.string.errValidAge));
            } else if (TextUtils.isEmpty(gender)) {
                rgGender.requestFocus();
            } else if (TextUtils.isEmpty(schoolName)) {
                edtSchoolName.requestFocus();
                edtSchoolName.setError(getResources().getString(R.string.errSchoolName));
            } else if (TextUtils.isEmpty(phoneNumber)) {
                edtPhoneNumber.requestFocus();
                edtPhoneNumber.setError(getResources().getString(R.string.errPhoneNumber));
            } else if (phoneNumber.length() < 6) {
                edtPhoneNumber.requestFocus();
                edtPhoneNumber.setError(getResources().getString(R.string.errAtleaastPhoneNumber));
            } else if (isExistsPhone) {
                edtPhoneNumber.requestFocus();
                edtPhoneNumber.setError(getResources().getString(R.string.errAlreadyPhoneNumber));
            } else if (TextUtils.isEmpty(schoolDistrictNumber)) {
                edtSchoolDistrictNumber.requestFocus();
                edtSchoolDistrictNumber.setError(getResources().getString(R.string.errSchoolDistrict));
            } else if (TextUtils.isEmpty(statename)) {
                edtStateName.requestFocus();
                edtStateName.setError(getResources().getString(R.string.errStateName));
            } else if (TextUtils.isEmpty(userName)) {
                edtUserName.requestFocus();
                edtUserName.setError(getResources().getString(R.string.errUserName));
            } else if (userName.length() < 3) {
                edtUserName.requestFocus();
                edtUserName.setError(getResources().getString(R.string.errUserNameMini));
            } else if (TextUtils.isEmpty(password)) {
                edtPassword.requestFocus();
                edtPassword.setError(getResources().getString(R.string.errPassword));
            } else if (!Util.isValidPassword(password)) {
                edtPassword.requestFocus();
                edtPassword.setError(getResources().getString(R.string.errValidPaszsword));
            } else if (TextUtils.isEmpty(confirmPassword)) {
                edtConfirmPassword.requestFocus();
                edtConfirmPassword.setError(getResources().getString(R.string.errCPassword));
            } else if (!password.equalsIgnoreCase(confirmPassword)) {
                edtConfirmPassword.requestFocus();
                edtConfirmPassword.setError(getResources().getString(R.string.errMatchPassword));
            } else if (imagePath.equalsIgnoreCase("")) {
                Util.showToast(AddUserActivity.this, "Please select image");
            } else {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 800) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                doPostData(phoneNumber, firstName, lastName, birthDate, childAge, gender, schoolName,
                        schoolDistrictNumber, statename, userName, password, confirmPassword);
            }

            break;
        case R.id.txtUpload:
            if (PermissionUtil.checkPermission(AddUserActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                pickImage();
            } else {
                PermissionUtil.requestPermission(AddUserActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, 120);
            }
            break;
        case R.id.edtBirthdate:
            openDatePicker();
            break;
        case R.id.imvProfile:
            if (PermissionUtil.checkPermission(AddUserActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                pickImage();
            } else {
                PermissionUtil.requestPermission(AddUserActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, 120);
            }
            break;

        }
    }

    /**
     * This function will check if user exitsts or not
     */

    private void doExistsUser(String userName) {
        if (!TextUtils.isEmpty(userName)) {
            mApiService.doExistsUser(mAppPref.getAuthToken(), mAppPref.getAuthToken(), userName)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            if (response.isSuccessful()) {
                                LogoutModel parentEditModel = new Gson().fromJson(response.body(), LogoutModel.class);
                                switch (parentEditModel.getStatus()) {
                                case "200":
                                    isExistsChild = false;
                                    edtUserName.setError(null);
                                    break;
                                default:
                                    edtUserName.requestFocus();
                                    edtUserName.setError(parentEditModel.getMessage());

                                    isExistsChild = true;
                                }
                            } else {
                                try {
                                    isExistsChild = true;
                                    edtUserName.requestFocus();
                                    edtUserName.setError(getResources().getString(R.string.errAlredyUsername));
                                    Log.e("onResponse: ", response.errorBody().string());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {

                            t.printStackTrace();
                        }
                    });
        }

    }

    private void doChecksChild(String userName, final Bundle bundle, final String screen_name) {
        if (!TextUtils.isEmpty(userName)) {
            mApiService.doExistsChild(mAppPref.getAuthToken(), mAppPref.getAuthToken(), userName)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            if (response.isSuccessful()) {
                                LogoutModel parentEditModel = new Gson().fromJson(response.body(), LogoutModel.class);
                                switch (parentEditModel.getStatus()) {
                                case "200":
                                    Intent intent = null;
                                    edtUserName.setError(null);
                                    if (screen_name.equalsIgnoreCase("Plan_list")) {
                                        intent = new Intent(AddUserActivity.this, PaymentDetailScreen.class);
                                    } else {
                                        intent = new Intent(AddUserActivity.this, PlanListActivity.class);
                                    }
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    break;
                                default:
                                    edtUserName.requestFocus();
                                    edtUserName.setError(parentEditModel.getMessage());

                                }
                            } else {
                                try {

                                    edtUserName.requestFocus();
                                    edtUserName.setError(getResources().getString(R.string.errAlredyUsername));
                                    Log.e("onResponse: ", response.errorBody().string());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {

                            t.printStackTrace();
                        }
                    });
        }

    }

    private void doExistsPhone(final String phone) {
        if (!TextUtils.isEmpty(phone)) {
            mApiService.doExistsMobile(mAppPref.getAuthToken(), mAppPref.getAuthToken(), phone)
                    .enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            if (response.isSuccessful()) {
                                LogoutModel parentEditModel = new Gson().fromJson(response.body(), LogoutModel.class);
                                switch (parentEditModel.getStatus()) {
                                case "200":
                                    isExistsPhone = false;
                                    edtPhoneNumber.setError(null);
                                    break;
                                default:
                                    isExistsPhone = true;
                                    edtPhoneNumber.requestFocus();
                                    edtPhoneNumber.setError(parentEditModel.getMessage());
                                    break;
                                }
                            } else {
                                try {
                                    isExistsPhone = true;
                                    edtPhoneNumber.requestFocus();
                                    edtPhoneNumber.setError(getResources().getString(R.string.errAlreadyPhoneNumber));
                                    Log.e("onResponse: ", response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }
    }

    private void pickImage() {
        new ImagePicker.Builder(AddUserActivity.this).mode(ImagePicker.Mode.GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM).directory(ImagePicker.Directory.DEFAULT)
                .extension(ImagePicker.Extension.PNG).scale(1000, 1000).allowMultipleImages(false)
                .enableDebuggingMode(false).build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
        case 120:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
        case ImagePicker.IMAGE_PICKER_REQUEST_CODE:
            switch (resultCode) {
            case RESULT_OK:
                List<String> mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);
                if (mPaths.size() > 0) {
                    displayImage(mPaths.get(0));
                }
                break;
            }
            break;
        }
    }

    private void displayImage(String imagePath) {
        this.imagePath = imagePath;
        Glide.with(AddUserActivity.this).asBitmap().load(imagePath)
                .apply(new RequestOptions().placeholder(R.drawable.girl).error(R.drawable.girl)).into(imvProfile);
    }

    private void openDatePicker() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                int age = getAge(year, monthOfYear, dayOfMonth);
                if (age > 18) {
                    edtBirthdate.requestFocus();
                    edtBirthdate.setError(getResources().getString(R.string.errValidAge));
                    Util.showToast(AddUserActivity.this, "You can add child only under 18 years", 5000);
                } else if (age == 0 || age < 0) {
                    childAge = "";
                    edtAge.setText("0 Year");
                    edtBirthdate.setText("Child Birthdate");
                    edtBirthdate.requestFocus();
                    edtBirthdate.setError(getResources().getString(R.string.errValid1Age));
                    Util.showToast(AddUserActivity.this, getResources().getString(R.string.errValid1Age), 5000);
                } else {
                    childAge = String.valueOf(age);
                    edtBirthdate.setText(Util.displayFormat(newDate.getTime(), Util.FORMAT_YYYYMMDD));
                    edtBirthdate.setError(null);
                    edtAge.setText(childAge + " Year");
                }
            }
        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private int getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    private void doPostData(String phone, String firstName, String lastName, String birthDate, String age,
            String gender, String schoolName, String schoolDistrictNumber, String statename, String userName,
            String password, String confirmPassword) {
        Bundle bundle = new Bundle();
        bundle.putString(ApiConstants.TAGS.phone, phone);
        bundle.putString(ApiConstants.TAGS.first_name, firstName);
        bundle.putString(ApiConstants.TAGS.last_name, lastName);
        bundle.putString(ApiConstants.TAGS.dob, birthDate);
        bundle.putString(ApiConstants.TAGS.age, age);
        bundle.putString(ApiConstants.TAGS.gender, gender);
        bundle.putString(ApiConstants.TAGS.school_name, schoolName);
        bundle.putString(ApiConstants.TAGS.school_district_no, schoolDistrictNumber);
        bundle.putString(ApiConstants.TAGS.state, statename);
        bundle.putString(ApiConstants.TAGS.username, userName);
        bundle.putString(ApiConstants.TAGS.password, password);
        bundle.putString(ApiConstants.TAGS.confirm_password, confirmPassword);
        bundle.putString(ApiConstants.TAGS.image, imagePath);
        if (screen_name.equalsIgnoreCase("Plan_list")) {
            bundle.putString(ApiConstants.TAGS.plan_id, plan_id);
            bundle.putString(ApiConstants.TAGS.amount, amount);
            bundle.putString(ApiConstants.TAGS.type, type);
            bundle.putString(ApiConstants.TAGS.one_time_fee, one_time_fee);
        }
        doChecksChild(userName, bundle, screen_name);

    }

}