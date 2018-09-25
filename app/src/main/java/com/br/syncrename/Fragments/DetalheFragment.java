package com.br.syncrename.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.br.syncrename.R;
import butterknife.ButterKnife;

public class DetalheFragment extends Fragment {

    public static DetalheFragment newInstance() {
        DetalheFragment fragment = new DetalheFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfc, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

}
