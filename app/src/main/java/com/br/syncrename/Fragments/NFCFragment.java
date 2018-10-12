package com.br.syncrename.Fragments;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.br.syncrename.R;
import butterknife.ButterKnife;

public class NFCFragment extends Fragment {

    private NfcAdapter nfcAdapter;

    public static NFCFragment newInstance() {
        NFCFragment fragment = new NFCFragment();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nfc, container, false);
        ButterKnife.bind(this, view);


        nfcAdapter = NfcAdapter.getDefaultAdapter(getContext());
        if(nfcAdapter == null){
            Toast.makeText(getContext(),
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
        }else if(!nfcAdapter.isEnabled()){
            Toast.makeText(getContext(),
                    "NFC NOT Enabled!",
                    Toast.LENGTH_LONG).show();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getActivity().getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Toast.makeText(getContext(),
                    "onResume() - ACTION_TAG_DISCOVERED",
                    Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if(tag == null){
//                textViewInfo.setText("tag == null");
                Log.i("NFC", "TAG NULL");
            }else{
                String tagInfo = tag.toString() + "\n";

                tagInfo += "\nTag Id: \n";
                byte[] tagId = tag.getId();
                tagInfo += "length = " + tagId.length +"\n";
                for(int i=0; i<tagId.length; i++){
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";
                }
                tagInfo += "\n";

                String[] techList = tag.getTechList();
                tagInfo += "\nTech List\n";
                tagInfo += "length = " + techList.length +"\n";
                for(int i=0; i<techList.length; i++){
                    tagInfo += techList[i] + "\n ";
                }

//                textViewInfo.setText(tagInfo);
                Log.i("NFC", tagInfo);
            }
        }else{
            Toast.makeText(getContext(),
                    "onResume() : " + action,
                    Toast.LENGTH_SHORT).show();
        }
    }
}
