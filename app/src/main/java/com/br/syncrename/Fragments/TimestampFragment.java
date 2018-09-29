package com.br.syncrename.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.br.syncrename.Activities.MainActivity;
import com.br.syncrename.R;
import com.br.syncrename.Utils.PreferenceHandler;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimestampFragment extends Fragment {

    @BindView(R.id.text_timestamp)
    TextView timestamp;
    @BindView(R.id.text_date)
    TextView dateText;
//    @BindView(R.id.button_back)
//    Button button_back;


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
//        button_back.setBackgroundColor(Color.parseColor("#"+ PreferenceHandler.getBotao()));

        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        String date1 = df.format(Calendar.getInstance().getTime());

        dateText.setText(date1);
        timestamp.setText(String.valueOf(df.getCalendar().getTimeInMillis()));

    }

//    @OnClick(R.id.button_back) void backPressed(){
//        ((MainActivity) getActivity()).onBackPressed();
//    }
}
