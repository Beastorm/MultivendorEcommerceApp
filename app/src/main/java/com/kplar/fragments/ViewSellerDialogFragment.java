package com.kplar.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.kplar.R;

public class ViewSellerDialogFragment extends DialogFragment {

    private TextView aboutSellerTxt;
    private Context context;
    private Button okBtn;
    private String aboutSeller;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.fragment_view_seller_dialog, null);
        aboutSellerTxt = view.findViewById(R.id.aboutSellerTxt);


        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.MyDialogTheme);
        builder.setView(view);



        okBtn = view.findViewById(R.id.ok_btn_Id);
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        aboutSellerTxt.setText(aboutSeller);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public void getSellerData(@NonNull String aboutSeller1) {
        aboutSeller = aboutSeller1;
        Log.i("mmm", "SellerINfo: " + aboutSeller);


    }


}
