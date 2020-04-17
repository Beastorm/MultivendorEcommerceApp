package com.kplar.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.kplar.R;
import com.kplar.utilities.MyPrefernces;

public class CreateReviewDialogFragment extends DialogFragment {

    onSomeEventListener someEventListener;

    private RatingBar ratingBar;
    private Context context;
    private AwesomeValidation awesomeValidation;
    private EditText message;

    private Button submitBtn;
    private TextView userR, errorInfo;
    MyPrefernces myPreferences;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_create_review_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        userR = view.findViewById(R.id.userReview);
        submitBtn = view.findViewById(R.id.submitBtnId);
        message = view.findViewById(R.id.review_message);
        errorInfo = view.findViewById(R.id.error_info);
        ratingBar = view.findViewById(R.id.ratingBar);


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        someEventListener = (onSomeEventListener) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myPreferences = new MyPrefernces(context);
        userR.setText(myPreferences.readName());

        awesomeValidation.addValidation(getActivity(), R.id.review_message, RegexTemplate.NOT_EMPTY, R.string.emptyONE);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating;
                String msg;
                if (awesomeValidation.validate()) {
                    rating = ratingBar.getRating();
                    msg = message.getText().toString().trim();
                    int getLen = msg.length();
                    if (getLen < 16) {
                        Toast.makeText(context, "Your review is not saved: At least 16 Characters Require Please Try Again", Toast.LENGTH_LONG).show();

                    } else {

                        someEventListener.someEvent(myPreferences.readName(), msg, rating);

                    }


                }

                dismiss();

            }

        });

    }















    public interface onSomeEventListener {
        public void someEvent(String userName, String message, float rating);
    }

}
