package com.br.syncrename.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.syncrename.Adapters.ListaAdapter;
import com.br.syncrename.Models.Arquivo;
import com.br.syncrename.R;
import com.br.syncrename.Utils.ArquivoTxt;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaFragment extends Fragment {

    @BindView(R.id.lista)
    RecyclerView Lista;

    private ListaAdapter listaAdapter;

    public static ListaFragment newInstance() {
        ListaFragment fragment = new ListaFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista, container, false);
        ButterKnife.bind(this, view);

        listaAdapter = new ListaAdapter(getActivity(), ArquivoTxt.listaArquivos(getResources().getString(R.string.file_name)));

        Lista.setHasFixedSize(true);
        Lista.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        Lista.setAdapter(listaAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        List<Arquivo> arquivos = ArquivoTxt.listaArquivos(getResources().getString(R.string.file_name));

    }
}
