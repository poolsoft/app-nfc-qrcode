package com.br.syncrename.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.br.syncrename.Activities.ConfirmacaoActivity;
import com.br.syncrename.Activities.MainActivity;
import com.br.syncrename.Models.Arquivo;
import com.br.syncrename.R;
import com.br.syncrename.Utils.ArquivoTxt;
import com.br.syncrename.Utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class NFCFragment extends Fragment {

    private NfcAdapter nfcAdapter;
    private List<Arquivo> arquivos = new ArrayList<>();
    final Handler handler = new Handler();
    Runnable runnable = null;

    public static NFCFragment newInstance() {
        NFCFragment fragment = new NFCFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfc, container, false);
        ButterKnife.bind(this, view);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                leituraNFC();
                handler.postDelayed(this, 1000);
            }
        }, 1000);


        arquivos = ArquivoTxt.listaArquivos(getResources().getString(R.string.file_name));
        ((MainActivity) getActivity()).modalArquivo(arquivos);
    }

    public void leituraNFC(){
        Intent intent = ((MainActivity) getActivity()).getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag == null){
//                Toast.makeText(getActivity(), "Tag está em branco",Toast.LENGTH_SHORT).show();
            }else{

                String tagInfo = String.valueOf(tag.toString());
                Log.i("NFC", tagInfo);

                if(((MainActivity) getActivity()).modalArquivo(arquivos)){

                    Intent i = new Intent(getContext(), ConfirmacaoActivity.class);
                    i.putExtra(Constantes.ATUAL_TXT, arquivos.get(0).getNome());
                    i.putExtra(Constantes.IS_QRCODE, false);
                    i.putExtra(Constantes.CODE_CODE, tagInfo);
                    startActivity(i);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);

    }
}
