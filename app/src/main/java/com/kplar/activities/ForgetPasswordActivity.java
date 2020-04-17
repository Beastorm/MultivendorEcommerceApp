package com.kplar.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.chaos.view.PinView;
import com.kplar.R;
import com.kplar.utilities.ApiClient;
import com.kplar.utilities.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText mobileNoEdit;
    private static final String TAG = "ForgetPasswordActivity";
    ApiInterface apiInterface;
    String uMob;
    Call<ForgotPassword> callForget;
    PinView pinViewText;
    Button resendBn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        mobileNoEdit = findViewById(R.id.editText3);
        pinViewText = findViewById(R.id.pinView);
        resendBn = findViewById(R.id.resendBnId);
        uMob = mobileNoEdit.getText().toString();

        resendBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callapi(uMob);
                Toast.makeText(ForgetPasswordActivity.this, "Resending OTP, please wait", Toast.LENGTH_SHORT).show();
            }
        });

        mobileNoEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mobileNoEdit.length() == 10) {
                    uMob = mobileNoEdit.getText().toString();
                    callapi(uMob);
                    Toast.makeText(ForgetPasswordActivity.this, "Please wait..", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void callapi(final String uMob) {

        Toast.makeText(this, "Response " + uMob, Toast.LENGTH_SHORT).show();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        callForget = apiInterface.getForgetPassword(uMob);
        callForget.enqueue(new Callback<ForgotPassword>() {

            @Override
            public void onResponse(Call<ForgotPassword> call, Response<ForgotPassword> response) {

                //Activate this command codes when sms pack is available

                //if (response.isSuccessful()) {
//                    String s = response.body().getOtp();
//                    pinViewText.setText(s);
                //Toast.makeText(ForgetPasswordActivity.this, "Getting OTP"+s, Toast.LENGTH_SHORT).show();
                //if (s.length() == 4) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                intent.putExtra("mn",uMob);
                startActivity(intent);
                Toast.makeText(ForgetPasswordActivity.this, "Welcome To My Shopping Free", Toast.LENGTH_SHORT).show();
                // }
                //}
                //else {
                try {
                    Toast.makeText(ForgetPasswordActivity.this, "success" + response.body(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: CallResponse" + response.body());
                }catch (Exception e){
                    e.printStackTrace();
                }
                //}
            }

            @Override
            public void onFailure(Call<ForgotPassword> call, Throwable t) {
                Toast.makeText(ForgetPasswordActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });
    }
}
