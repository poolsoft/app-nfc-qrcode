package com.br.syncrename.Activities;

import android.os.Bundle;
import android.widget.TextView;

import com.br.syncrename.R;
import com.br.syncrename.Utils.Constantes;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmacaoActivity extends SyncActivity {

    @BindView(R.id.text_timestamp)
    TextView timestamp;

    @BindView(R.id.text_tag)
    TextView userCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacao);
        ButterKnife.bind(this);

        String a = getIntent().getStringExtra(Constantes.ATUAL_TXT);
        String b = getIntent().getStringExtra(Constantes.CODE_QRCODE);
        userCode.setText(b);
    }

}
