package com.br.syncrename.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.br.syncrename.Activities.MainActivity;
import com.br.syncrename.R;
import com.br.syncrename.Utils.PreferenceHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BotaoFragment extends Fragment {

    @BindView(R.id.edit_hexadecimal)
    EditText editHexadecimal;
    @BindView(R.id.view_color)
    LinearLayout viewColor;
    @BindView(R.id.button_back)
    Button button_back;

    public static BotaoFragment newInstance() {
        BotaoFragment fragment = new BotaoFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_botao, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        button_back.setBackgroundColor(Color.parseColor("#"+PreferenceHandler.getBotao()));

        editHexadecimal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {trocarCorView(); }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { trocarCorView(); }

            @Override
            public void afterTextChanged(Editable s) { trocarCorView(); }
        });

    }

    @OnClick(R.id.button_hexadecimal) void trocarCor(){
        editHexadecimal.setFocusable(false);
        String cor = editHexadecimal.getText().toString();
        try{
            button_back.setBackgroundColor(Color.parseColor("#"+cor));
            PreferenceHandler.saveBotao(cor);
            Toast.makeText(getContext(), getResources().getString(R.string.cor_sucesso), Toast.LENGTH_SHORT).show();

        }catch (NumberFormatException e){
            Toast.makeText(getContext(), getResources().getString(R.string.cor_erro), Toast.LENGTH_SHORT).show();
        }catch (IllegalArgumentException e){
            Toast.makeText(getContext(), getResources().getString(R.string.cor_erro), Toast.LENGTH_SHORT).show();
        }

    }

    public void trocarCorView(){
        try{
            viewColor.setBackgroundColor(Color.parseColor("#"+editHexadecimal.getText().toString()));
        }catch( Exception e ){
            Log.e("COR","Cor não existe");
        }
    }

}
