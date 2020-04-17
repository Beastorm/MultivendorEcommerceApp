package com.kplar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kplar.R;
import com.kplar.models.UserResponseData;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;
import com.kplar.utilities.MyPrefernces;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    ApiInterface myInterface;
    Call<UserResponseData> call1;
    TextView createAccount, fgtpwd;
    Button gobtn;
    EditText mobileEt, pwdEt;
    MyPrefernces myPrefernces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createAccount = findViewById(R.id.createAccount);
        fgtpwd = findViewById(R.id.fgtpwd);
        gobtn = findViewById(R.id.goBtn);
        mobileEt = findViewById(R.id.mobile1);
        pwdEt = findViewById(R.id.pwd1);


        myPrefernces = new MyPrefernces(this);


        gobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = mobileEt.getText().toString().trim();
                String pwd = pwdEt.getText().toString().trim();

                if (mobile.isEmpty() || pwd.isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Empty Value not Allowed", Toast.LENGTH_SHORT).show();

                } else {

                    myInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    call1 = myInterface.performSignIn(mobile, pwd);

                    call1.enqueue(new Callback<UserResponseData>() {
                        @Override
                        public void onResponse(Call<UserResponseData> call, Response<UserResponseData> response) {

                            if (response.body().getResponseCode().equals("ok")) {

                                myPrefernces.writeLoginStatus(true);
                                myPrefernces.writeName(response.body().getUserName());
                                myPrefernces.writeUserId(response.body().getId());
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();


                            } else if (response.body().getResponseCode().equals("false")) {

                                Toast.makeText(LoginActivity.this, "Wrong Mobile number or Password", Toast.LENGTH_SHORT).show();


                            }

                        }

                        @Override
                        public void onFailure(Call<UserResponseData> call, Throwable t) {

                        }
                    });


                }

            }
        });


        fgtpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
