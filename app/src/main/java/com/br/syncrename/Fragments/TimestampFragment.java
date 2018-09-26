package com.br.syncrename.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.br.syncrename.R;

import org.joda.time.DateTime;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimestampFragment extends Fragment {

    @BindView(R.id.text_timestamp)
    TextView timestamp;

    @BindView(R.id.text_date)
    TextView dateText;


    public static TimestampFragment newInstance() {
        TimestampFragment fragment = new TimestampFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timestamp, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        DateTime date = new DateTime();
        dateText.setText("qqqqqq");
        timestamp.setText("aaaaaa");



    }
}
