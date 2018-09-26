package com.br.syncrename.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.br.syncrename.Fragments.BackgroundFragment;
import com.br.syncrename.Fragments.BotaoFragment;
import com.br.syncrename.Fragments.ListaFragment;
import com.br.syncrename.Fragments.NFCFragment;
import com.br.syncrename.Fragments.QRCodeFragment;
import com.br.syncrename.Fragments.TimestampFragment;
import com.br.syncrename.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends SyncActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.father_relative)
    RelativeLayout relativeFather;


    private int sectionNumer = 0;

    private Fragment fragment;
    private NFCFragment nfcFragment;
    private QRCodeFragment qrCodeFragment;
    private TimestampFragment timestampFragment;
    private ListaFragment listaFragment;
    private BackgroundFragment backgroundFragment;
    private BotaoFragment botaoFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        }catch (NumberFormatException e){
            Toast.makeText(this, getResources().getString(R.string.cor_erro), Toast.LENGTH_SHORT).show();
        }catch (IllegalArgumentException e){
            Toast.makeText(this, getResources().getString(R.string.cor_erro), Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
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
        }

        return null;
    }
}
