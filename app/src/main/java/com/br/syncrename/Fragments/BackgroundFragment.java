package com.br.syncrename.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.br.syncrename.Activities.MainActivity;
import com.br.syncrename.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackgroundFragment extends Fragment {

    @BindView(R.id.edit_hexadecimal)
    EditText editHexadecimal;

    public static BackgroundFragment newInstance() {
        BackgroundFragment fragment = new BackgroundFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.button_hexadecimal) void trocarCor(){
        String cor = editHexadecimal.getText().toString();
        ((MainActivity) getActivity()).trocarFundoCor(cor);


    }

}
