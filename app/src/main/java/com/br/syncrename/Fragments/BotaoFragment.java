package com.br.syncrename.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.br.syncrename.R;
import butterknife.ButterKnife;

public class BotaoFragment extends Fragment {

    public static BotaoFragment newInstance() {
        BotaoFragment fragment = new BotaoFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfc, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

}
