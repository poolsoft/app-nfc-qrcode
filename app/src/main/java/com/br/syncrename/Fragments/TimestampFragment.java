package com.br.syncrename.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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

public class TimestampFragment extends Fragment  {

    @BindView(R.id.text_timestamp)
    TextView timestamp;
    @BindView(R.id.text_date)
    TextView dateText;
    @BindView(R.id.button_back)
    CardView button_back;
    private final static int INTERVAL = 1000 * 60 * 2; //2 minutes

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
        button_back.setCardBackgroundColor(Color.parseColor(PreferenceHandler.getBotao()));

        threadTime();
    }

    @OnClick(R.id.button_back) void backPressed(){
        ((MainActivity) getActivity()).onBackPressed();
    }

    public void threadTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                trocarValores();
                handler.postDelayed(this, 1000);
            }
        }, 1000);
    }


    public void trocarValores(){
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
        String date1 = df.format(Calendar.getInstance().getTime());

        dateText.setText(date1);
        timestamp.setText(String.valueOf(df.getCalendar().getTimeInMillis()/1000));
    }
}
