package com.br.syncrename.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.br.syncrename.Fragments.BackgroundFragment;
import com.br.syncrename.Fragments.BotaoFragment;
import com.br.syncrename.Fragments.DetalheFragment;
import com.br.syncrename.Fragments.ListaFragment;
import com.br.syncrename.Fragments.NFCFragment;
import com.br.syncrename.Fragments.QRCodeFragment;
import com.br.syncrename.Fragments.TimestampFragment;
import com.br.syncrename.Models.Arquivo;
import com.br.syncrename.R;
import com.br.syncrename.Utils.ArquivoTxt;
import com.br.syncrename.Utils.Constantes;
import com.br.syncrename.Utils.ImageUtil;
import com.br.syncrename.Utils.PreferenceHandler;
import com.br.syncrename.Utils.ServerHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends SyncActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.father_relative)
    RelativeLayout relativeFather;
    @BindView(R.id.background_img)
    ImageView backgroundImg;


    private int sectionNumer = 0;

    private Fragment fragment;
    private NFCFragment nfcFragment;
    private QRCodeFragment qrCodeFragment;
    private TimestampFragment timestampFragment;
    private ListaFragment listaFragment;
    private BackgroundFragment backgroundFragment;
    private BotaoFragment botaoFragment;
    private DetalheFragment detalheFragment;

    private EditText nomeArquivo;
    private String nomeDetahles;
    private int DETALHES = 2540;
    protected IntentFilter mIntentFilter;
    private String image64 = "";
    private Uri picUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        PreferenceHandler.init(this);
        mIntentFilter = new IntentFilter("android.nfc.action.TAG_DISCOVERED");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setTitle("");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        verificarPermissoes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        trocarFundoCor(PreferenceHandler.getBackground());
        if(PreferenceHandler.getIdLeitura() != Constantes.VALUE_LEITURA){
            inicializaPrincipal(PreferenceHandler.getIdLeitura());
        }else{
            inicializaPrincipal(R.id.nav_nfc);
        }

        if(!PreferenceHandler.getFundoCor()){
            backgroundImg.setImageBitmap(ImageUtil.loadImageFromStorage(this,"background.jpg"));
            backgroundImg.setVisibility(View.VISIBLE);
        }else{
            backgroundImg.setVisibility(View.GONE);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            inicializaPrincipal(PreferenceHandler.getIdLeitura());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void trocarFundoCor(String cor){
        try{
            relativeFather.setBackgroundColor(Color.parseColor("#"+cor));
            PreferenceHandler.saveBackground("#"+cor);
            PreferenceHandler.saveFundoCor(true);
            backgroundImg.setVisibility(View.GONE);
            //Toast.makeText(this, getResources().getString(R.string.cor_sucesso), Toast.LENGTH_SHORT).show();

        }catch (NumberFormatException e){
           // Toast.makeText(this, getResources().getString(R.string.cor_erro), Toast.LENGTH_SHORT).show();
        }catch (IllegalArgumentException e){
            Toast.makeText(this, getResources().getString(R.string.cor_erro), Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        sectionNumer = item.getItemId();
        if(getFragmentBySection(sectionNumer) != null) {
            fragment = getFragmentBySection(sectionNumer);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, "MAIN_FRAGMENT_" + sectionNumer)
                    .addToBackStack("MAIN_FRAGMENT_" + sectionNumer)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void inicializaPrincipal(int item){
        sectionNumer = item;
        if(getFragmentBySection(sectionNumer) != null) {
            fragment = getFragmentBySection(sectionNumer);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, "MAIN_FRAGMENT_" + sectionNumer)
                    .addToBackStack("MAIN_FRAGMENT_" + sectionNumer)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public Fragment getFragmentBySection(int sectionNumer) {
        if (sectionNumer == R.id.nav_timestamp) {
            if(timestampFragment == null){
                timestampFragment = TimestampFragment.newInstance();
            }

            return timestampFragment;

        } else if (sectionNumer == R.id.nav_acessar) {
            if(listaFragment == null){
                listaFragment = ListaFragment.newInstance();
            }

            return listaFragment;

        } else if (sectionNumer == R.id.nav_novo) {
            modalNovoArquivo();

        } else if (sectionNumer == R.id.nav_background) {
            if(backgroundFragment == null){
                backgroundFragment = BackgroundFragment.newInstance();
            }

            return backgroundFragment;

        } else if (sectionNumer == R.id.nav_botao) {
            if(botaoFragment == null){
                botaoFragment = BotaoFragment.newInstance();
            }

            return botaoFragment;
        } else if( sectionNumer == R.id.nav_qrc){
            if(qrCodeFragment == null){
                qrCodeFragment = QRCodeFragment.newInstance();
                PreferenceHandler.saveIdLeitura(R.id.nav_qrc);
            }

            return qrCodeFragment;
        } else if( sectionNumer == DETALHES ){
            if( detalheFragment == null ){
                detalheFragment = DetalheFragment.newInstance();
            }

            return detalheFragment;
        }else if( sectionNumer == R.id.nav_nfc){
            if(nfcFragment == null){
                nfcFragment = NFCFragment.newInstance();
                PreferenceHandler.saveIdLeitura(R.id.nav_nfc);
            }

            return nfcFragment;
        }

        return null;
    }

    public void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public void modalNovoArquivo(){
        ArquivoTxt.criarPasta();

        final View inflator = getLayoutInflater().inflate(R.layout.modal_novo, null);

        nomeArquivo = (EditText)inflator.findViewById(R.id.code_delivered);

        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(this);
        alertDialog.setView(inflator);
        alertDialog.setPositiveButton(getString(R.string.file_create), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                criarNovoArquivo(nomeArquivo.getText().toString());
            }
        });
        alertDialog.setNegativeButton(getString(R.string.file_cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        final android.app.AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public void criarNovoArquivo(String nome){

        try {
            Arquivo arquivo = new Arquivo();
            arquivo.setNome(nome);
            arquivo.setData(DateTime.now());

            String novoArquivo = ServerHandler.getJsonConverter().writeValueAsString(arquivo);
            ArquivoTxt.gravarArquivo(this, novoArquivo, getResources().getString(R.string.file_name));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void chamaDetalhes(String nome){
        nomeDetahles = nome;

        fragment = getFragmentBySection(DETALHES);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, "MAIN_FRAGMENT_" + sectionNumer)
                .addToBackStack("MAIN_FRAGMENT_" + sectionNumer)
                .commit();
    }

    public String getNomeDetahles() {
        return nomeDetahles;
    }

    public boolean modalArquivo(List<Arquivo> arquivos){
        if(arquivos.size() == 0){
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(this).create();
            alertDialog.setMessage(getResources().getString(R.string.not_file));
            alertDialog.setButton(android.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            return false;
        }else
            return true;
    }


    public void backgroudPick(){
        CropImage.activity(picUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Crop")
                .setScaleType(CropImageView.ScaleType.FIT_CENTER)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setAspectRatio(9, 16)
                .setAutoZoomEnabled(true)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Bitmap background = null;
                    try {
                        background = ImageUtil.decodeUri(this, result.getUri());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    base64(new File(ImageUtil.saveToInternalSorage(this,background,"background.jpg")));
                    backgroundImg.setImageBitmap(background);
                    PreferenceHandler.saveFundoCor(false);
                    backgroundImg.setVisibility(View.VISIBLE);

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {}
                break;
        }
    }

    private void base64(File file) {
        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
//        image64 = "data:image/png;base64," + encodedImage;
    }
}
