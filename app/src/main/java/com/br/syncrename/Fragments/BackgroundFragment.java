package com.br.syncrename.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.br.syncrename.Activities.MainActivity;
import com.br.syncrename.R;
import com.br.syncrename.Utils.ImageUtil;
import com.br.syncrename.Utils.PreferenceHandler;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class BackgroundFragment extends Fragment {

    @BindView(R.id.edit_hexadecimal)
    EditText editHexadecimal;
    @BindView(R.id.view_color)
    CardView viewColor;
    @BindView(R.id.button_back)
    CardView button_back;


    public static BackgroundFragment newInstance() {
        BackgroundFragment fragment = new BackgroundFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        button_back.setCardBackgroundColor(Color.parseColor(PreferenceHandler.getBotao()));
        editHexadecimal.getText().clear();
        editHexadecimal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {trocarCorView(); }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { trocarCorView(); }

            @Override
            public void afterTextChanged(Editable s) { trocarCorView(); }
        });

    }

    @OnClick(R.id.edit_arquivo) void arquivo(){
        ((MainActivity) getActivity()).backgroudPick();
    }
    @OnClick(R.id.button_arquivo) void buttonArquivo(){arquivo();};

    @OnClick(R.id.button_hexadecimal) void trocarCor(){
        button_back.setCardBackgroundColor(Color.parseColor(PreferenceHandler.getBotao()));
        String cor = editHexadecimal.getText().toString();
        ((MainActivity) getActivity()).trocarFundoCor(cor);
        ((MainActivity) getContext()).hideKeyboard();

    }

    public void trocarCorView(){
        try{
            if(editHexadecimal.getText().toString().length() != 0)
                viewColor.setCardBackgroundColor(Color.parseColor("#"+editHexadecimal.getText().toString()));
            else
                viewColor.setCardBackgroundColor(Color.parseColor(PreferenceHandler.getBotao()));
        }catch( Exception e ){
            Log.e("COR","Cor não existe");
        }
    }

    @OnClick(R.id.button_back) void backPressed(){
        ((MainActivity) getActivity()).onBackPressed();
    }


}
