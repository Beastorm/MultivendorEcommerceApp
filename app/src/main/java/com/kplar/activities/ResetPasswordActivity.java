package com.kplar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.kplar.R;
import com.kplar.models.UpdatePassword;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText newPass, confirmPass;
    Button doneBn;
    private String conPassStr;
    AwesomeValidation mAwesomeValidation;
    String mobileNo;
    String regexPassword;
    ApiInterface apiInterface;
    Call<UpdatePassword> newPasswordCall;
    ImageView backBn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        newPass = findViewById(R.id.newPassId);
        confirmPass = findViewById(R.id.confirmPass);
        doneBn = findViewById(R.id.doneBtn);
        backBn = findViewById(R.id.backId);
        backBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ForgetPasswordActivity.class));
                finish();
            }
        });

        final String newPasswor = newPass.getText().toString();
        conPassStr = confirmPass.getText().toString();
        mobileNo = getIntent().getStringExtra("mn");

        mAwesomeValidation = new AwesomeValidation(BASIC);
        regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        mAwesomeValidation.addValidation(ResetPasswordActivity.this, R.id.newPassId, regexPassword, R.string.password_info);

        doneBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAwesomeValidation.validate()) {
                    if (confirmPass.getText().toString().equals(newPass.getText().toString())) {
                        callApi();
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Password do not match ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(ResetPasswordActivity.this, "Setting new Password ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void callApi() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        newPasswordCall = apiInterface.setNewPass(mobileNo, newPass.getText().toString().trim());
        newPasswordCall.enqueue(new Callback<UpdatePassword>() {
            @Override
            public void onResponse(Call<UpdatePassword> call, Response<UpdatePassword> response) {
                if (response.isSuccessful()){
                    Toast.makeText(ResetPasswordActivity.this, "Response " + response.body(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }

            }

            @Override
            public void onFailure(Call<UpdatePassword> call, Throwable t) {
            }
        });
    }
}
