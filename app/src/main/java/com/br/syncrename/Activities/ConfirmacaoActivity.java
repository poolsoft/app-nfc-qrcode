package com.br.syncrename.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.br.syncrename.Models.Tags;
import com.br.syncrename.R;
import com.br.syncrename.Utils.ArquivoTxt;
import com.br.syncrename.Utils.Constantes;
import com.br.syncrename.Utils.ImageUtil;
import com.br.syncrename.Utils.PreferenceHandler;
import com.br.syncrename.Utils.ServerHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @BindView(R.id.background_img)
    ImageView backgroundImg;

    private String arquivoAtual;
    private String date1;
    private String millius;
    private String code;
    // TODO : ALETERAR A FUNCÇÃO revomeEncapsulamento()
    private final Pattern PATTERN = Pattern
            .compile("XPTO.*XPTO");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);
        getSupportActionBar().hide();
//        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
//        getActionBar().hide();

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
            Log.e("COR_ERRO", getResources().getString(R.string.cor_erro));
        }catch (IllegalArgumentException e){
            Log.e("COR_ERRO", getResources().getString(R.string.cor_erro));
        }

        if(!PreferenceHandler.getFundoCor()){
            backgroundImg.setImageBitmap(ImageUtil.loadImageFromStorage(this,"background.jpg"));
        }
    }

    public void preencherTela(){
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        date1 = df.format(Calendar.getInstance().getTime());
        millius = String.valueOf(df.getCalendar().getTimeInMillis()/1000);
        dateText.setText(date1);
        timestamp.setText(millius);

        if(getIntent().getBooleanExtra(Constantes.IS_QRCODE,false)){
            separarQRCode();
        }else{
            separarNFC();
        }

        arquivoAtual = getIntent().getStringExtra(Constantes.ATUAL_TXT);
        userCode.setText(code);
    }

    public void separarQRCode(){
        code = getIntent().getStringExtra(Constantes.CODE_CODE);

        try {
            JSONObject jsonDefault = new JSONObject(code);
            if(jsonDefault.has("qrcode")){
                jsonDefault = jsonDefault.getJSONObject("qrcode");
                if(jsonDefault.has("Id")){
                    int codeint = jsonDefault.getInt("Id");
                    code = String.valueOf(codeint);
                }else {
                    code = lerEncapsulamento(code);
                }
            }else{
                code = lerEncapsulamento(code);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            try{
                JSONObject jsonDefault = new JSONObject(code);
                if(jsonDefault.has("qrcode")){
                    jsonDefault = jsonDefault.getJSONObject("qrcode");
                    if(jsonDefault.has("Id")){
                        code = jsonDefault.getString("Id");
                    }else {
                        code = lerEncapsulamento(code);
                    }
                }else{
                    code = lerEncapsulamento(code);
                }
            }catch (Exception ex){
                code = lerEncapsulamento(code);
            }
        }
    }

    public void separarNFC(){
        code = getIntent().getStringExtra(Constantes.CODE_CODE);
    }

    @Override
    public void onBackPressed() {
        voltarMain();
    }

    @OnClick(R.id.button_nao) void voltarMain(){
        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setAction("android.nfc.action.TAG_DISCOVERED");
        finish();
        startActivity(i);
    }

    @OnClick(R.id.button_sim) void confirmaTag(){
            String novoTag = code+";"+millius;
            ArquivoTxt.gravarArquivo(this, novoTag, arquivoAtual);
        voltarMain();
    }

    public String lerEncapsulamento(String code){

        List<String> listId = new ArrayList<String>();
        Matcher matcher = PATTERN.matcher(code);
        while (matcher.find()) {
            listId.add(matcher.group());
        }

        if(listId.size() > 0){
            return revomeEncapsulamento(listId.get(0));
        }

        return "Sem ID";

    }

    public String revomeEncapsulamento(String code){
        code = code.substring(4,code.length());
        code = code.substring(0,code.length()-4);
        return code;
    }
}
