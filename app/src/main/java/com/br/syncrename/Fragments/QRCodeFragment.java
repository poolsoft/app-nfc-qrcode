package com.br.syncrename.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.br.syncrename.Activities.ConfirmacaoActivity;
import com.br.syncrename.Models.Arquivo;
import com.br.syncrename.R;
import com.br.syncrename.Utils.ArquivoTxt;
import com.br.syncrename.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRCodeFragment extends Fragment {

    private static final int REQUEST_CODE_QR_SCAN = 101;
    private static final String LOGTAG = "QRCODE";
    private List<Arquivo> arquivos = new ArrayList<>();

    public static QRCodeFragment newInstance() {
        QRCodeFragment fragment = new QRCodeFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qrc, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.img_qrc) void lerQrCode(){

        arquivos = ArquivoTxt.listaArquivos(getResources().getString(R.string.file_name));

        if(arquivos.size() != 0){
            Intent i = new Intent(getContext(),QrCodeActivity.class);
            startActivityForResult( i,REQUEST_CODE_QR_SCAN);
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setMessage(getResources().getString(R.string.not_file));
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle(getResources().getString(R.string.qrc_erro));
                alertDialog.setMessage(getResources().getString(R.string.qrc_erro_msg));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == REQUEST_CODE_QR_SCAN)
        {
            if(data==null)
                return;
            //Getting the passed result
            String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            Log.d(LOGTAG,"Have scan result in your app activity :"+ result);
            Intent i = new Intent(getContext(), ConfirmacaoActivity.class);
            i.putExtra(Constantes.ATUAL_TXT, arquivos.get(0).getNome());
            i.putExtra(Constantes.IS_QRCODE, false);
            i.putExtra(Constantes.CODE_CODE, result);
            startActivity(i);
        }
    }
}
