package com.br.syncrename.Activities;

import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.br.syncrename.R;

public class SplashActivity extends SyncActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setAction("android.nfc.action.NDEF_DISCOVERED");
        i.addCategory("android.intent.category.DEFAULT");
        i.setData(Uri.parse("text/plain"));
        finish();
        startActivity(i);

        leituraNFC(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        leituraNFC(intent);
    }


}
