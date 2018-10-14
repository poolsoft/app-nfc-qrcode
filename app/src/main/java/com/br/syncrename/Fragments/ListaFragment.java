package com.br.syncrename.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.br.syncrename.Activities.MainActivity;
import com.br.syncrename.Adapters.ListaAdapter;
import com.br.syncrename.Models.Arquivo;
import com.br.syncrename.R;
import com.br.syncrename.Utils.ArquivoTxt;
import com.br.syncrename.Utils.PreferenceHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListaFragment extends Fragment {

    @BindView(R.id.lista)
    RecyclerView Lista;
    @BindView(R.id.button_back)
    CardView button_back;

    private ListaAdapter listaAdapter;

    public static ListaFragment newInstance() {
        ListaFragment fragment = new ListaFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        button_back.setCardBackgroundColor(Color.parseColor("#" + PreferenceHandler.getBotao()));
        listaAdapter = new ListaAdapter(getActivity(), ArquivoTxt.listaArquivos(getResources().getString(R.string.file_name)));

        Lista.setHasFixedSize(true);
        Lista.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        Lista.setAdapter(listaAdapter);
    }


    @OnClick(R.id.button_back) void backPressed(){
        ((MainActivity) getActivity()).onBackPressed();
    }
}
