package com.br.syncrename.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.br.syncrename.Models.Arquivo;
import com.br.syncrename.R;
import com.br.syncrename.Utils.ArquivoTxt;
import com.br.syncrename.Utils.Constantes;
import com.br.syncrename.Utils.PreferenceHandler;

import java.util.ArrayList;
import java.util.List;

public class SyncActivity extends AppCompatActivity {

    private static final int REQUEST = 2424;
    private NfcAdapter nfcAdapter;
    private List<Arquivo> arquivos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceHandler.init(this);

        verificarPermissoes();
        leituraNFC(getIntent());
    }

    public void verificarPermissoes(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.NFC) != PackageManager.PERMISSION_GRANTED
                ) {
            String[] PERMS = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.NFC};
            ActivityCompat.requestPermissions(this, PERMS, REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch(requestCode) {
            case REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED) {

                }
                return;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

       leituraNFC(intent);
    }

    public void leituraNFC(Intent intent){

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,
                    "Aparelho não tem suporte a NFC",
                    Toast.LENGTH_LONG).show();
        }else if(!nfcAdapter.isEnabled()){
            Toast.makeText(this,
                    "NFC Desligado",
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_TAG);
            if (rawMessages != null) {
                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    messages[i] = (NdefMessage) rawMessages[i];
                }

                if(modalArquivo(arquivos)){

                    Intent i = new Intent(this, ConfirmacaoActivity.class);
                    i.putExtra(Constantes.ATUAL_TXT, arquivos.get(0).getNome());
                    i.putExtra(Constantes.IS_QRCODE, false);
                    i.putExtra(Constantes.CODE_CODE, rawMessages.toString());
                    startActivity(i);
                }

                Log.i("DADOS",messages.toString());
                // Process the messages array.
            }
        }


    }

    public boolean modalArquivo(List<Arquivo> arquivos){
        if(arquivos.size() == 0){
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
            alertDialog.setMessage(getResources().getString(R.string.not_file));
            alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return false;
        }else
            return true;
    }


}
