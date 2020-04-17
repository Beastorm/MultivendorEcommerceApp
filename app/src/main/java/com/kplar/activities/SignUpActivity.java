package com.kplar.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.kplar.R;
import com.kplar.models.UserResponseData;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class SignUpActivity extends AppCompatActivity {
    EditText userNmaeEt, mobileEt, pwdEt;
    Button doneBtn;
    TextView loginTv;
    AwesomeValidation mAwesomeValidation;
    String regexPassword;
    ApiInterface myInterface;
    Call<UserResponseData> call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        userNmaeEt = findViewById(R.id.mobile1);
        mobileEt = findViewById(R.id.mobile);
        pwdEt = findViewById(R.id.pwd1);
        doneBtn = findViewById(R.id.goBtn);
        loginTv = findViewById(R.id.loginbtn);
        mAwesomeValidation = new AwesomeValidation(BASIC);
        regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";


        mAwesomeValidation.addValidation(SignUpActivity.this, R.id.mobile1, "^[A-Za-z0-9_-]{3,15}$", R.string.err_name);
        mAwesomeValidation.addValidation(SignUpActivity.this, R.id.mobile, "^[6-9]\\d{9}$", R.string.err_tel);
        mAwesomeValidation.addValidation(SignUpActivity.this, R.id.pwd1, regexPassword, R.string.password_info);


        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAwesomeValidation.validate()) {
                    String userName = userNmaeEt.getText().toString().trim();
                    String mobile = mobileEt.getText().toString().trim();
                    String pwd = pwdEt.getText().toString().trim();


                    //Log.i("Info","Checking1");
                    myInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    call = myInterface.performSignUp(userName, mobile, pwd);

                    call.enqueue(new Callback<UserResponseData>() {

                        @Override
                        public void onResponse(Call<UserResponseData> call, Response<UserResponseData> response) {


                            if (response.body().getResponseCode().equals("ok")) {
                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(SignUpActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                finish();


                            } else if (response.body().getResponseCode().equals("Exist")) {
                                Toast.makeText(SignUpActivity.this, "Already Exist", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onFailure(Call<UserResponseData> call, Throwable t) {
                            t.printStackTrace();

                            Toast.makeText(SignUpActivity.this, "Server Error" + t.toString(), Toast.LENGTH_SHORT).show();


                        }
                    });

                    userNmaeEt.setText("");
                    mobileEt.setText("");
                    pwdEt.setText("");


                } else {


                }
            }
        });


    }

    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(SignUpActivity.this, "Destroyed Activity", Toast.LENGTH_SHORT).show();
        call.cancel();
    }
}
