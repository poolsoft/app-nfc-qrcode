package com.br.syncrename.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.br.syncrename.R;
import com.br.syncrename.Utils.Constantes;
import com.br.syncrename.Utils.PreferenceHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;

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
        arquivoAtual = getIntent().getStringExtra(Constantes.ATUAL_TXT);
        userCode.setText(getIntent().getStringExtra(Constantes.CODE_QRCODE));
    }

}
