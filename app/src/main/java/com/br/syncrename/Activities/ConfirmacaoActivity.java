package com.br.syncrename.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.br.syncrename.Models.Tags;
import com.br.syncrename.R;
import com.br.syncrename.Utils.ArquivoTxt;
import com.br.syncrename.Utils.Constantes;
import com.br.syncrename.Utils.PreferenceHandler;
import com.br.syncrename.Utils.ServerHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmacaoActivity extends SyncActivity {

    @BindView(R.id.text_timestamp)
    TextView timestamp;
    @BindView(R.id.text_tag)
    TextView userCode;
    @BindView(R.id.father_relative)
    RelativeLayout relativeFather;
    @BindView(R.id.text_data)
    TextView dateText;

    private String arquivoAtual;
    private String date1;
    private String millius;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        trocarFundoCor(PreferenceHandler.getBackground());
        preencherTela();
    }

    public void trocarFundoCor(String cor){
        try{
            relativeFather.setBackgroundColor(Color.parseColor("#"+cor));
        }catch (NumberFormatException e){
            Toast.makeText(this, getResources().getString(R.string.cor_erro), Toast.LENGTH_SHORT).show();
        }catch (IllegalArgumentException e){
            Toast.makeText(this, getResources().getString(R.string.cor_erro), Toast.LENGTH_SHORT).show();
        }
    }

    public void preencherTela(){
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        date1 = df.format(Calendar.getInstance().getTime());
        millius = String.valueOf(df.getCalendar().getTimeInMillis());
        dateText.setText(date1);
        timestamp.setText(millius);
        separarQRCode();
        arquivoAtual = getIntent().getStringExtra(Constantes.ATUAL_TXT);
        userCode.setText(code);
    }

    public void separarQRCode(){
        code = getIntent().getStringExtra(Constantes.CODE_QRCODE);

        try {
            JSONObject jsonDefault = new JSONObject(code);
            if(jsonDefault.has("qrcode")){
                jsonDefault = jsonDefault.getJSONObject("qrcode");
                if(jsonDefault.has("Id")){
                    int codeint = jsonDefault.getInt("Id");
                    code = String.valueOf(codeint);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            try{
                JSONObject jsonDefault = new JSONObject(code);
                if(jsonDefault.has("qrcode")){
                    jsonDefault = jsonDefault.getJSONObject("qrcode");
                    if(jsonDefault.has("Id")){
                        code = jsonDefault.getString("Id");
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        voltarMain();
    }

    @OnClick(R.id.button_nao) void voltarMain(){
        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finish();
        startActivity(i);
    }

    @OnClick(R.id.button_sim) void confirmaTag(){
        Tags tags = new Tags();
        tags.setCode(code);
        tags.setTimeStamp(millius);
        try {
            String novoTag = ServerHandler.getJsonConverter().writeValueAsString(tags);
            ArquivoTxt.gravarArquivo(this, novoTag, arquivoAtual);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        voltarMain();
    }


}
