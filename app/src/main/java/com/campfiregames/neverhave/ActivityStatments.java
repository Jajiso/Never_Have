package com.campfiregames.neverhave;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.AdRequest;

import static com.campfiregames.neverhave.PreferenceHelper.getIsAgreeShowDialog;
import static com.campfiregames.neverhave.PreferenceHelper.setAppLaunchedTimes;


public class ActivityStatments extends AppCompatActivity {

    private TextView campoTexto;
    private Button btnNext;
    private AdView mAdView;
    private ProgressBar progressBarAds;

    private int countStaments;
    private StatmentsList statmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //OCULTAR LA BARRA DE ESTATUS
        // Si la versión android es menor que Jellybean, usa este llamado para esconder la barra de estatus.
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_statments);

        campoTexto = findViewById(R.id.preguntasTextView);
        btnNext = findViewById(R.id.btnNext);
        mAdView = findViewById(R.id.adView);
        progressBarAds = findViewById(R.id.progressBarAds);
        setCountStaments();

        //Le suma 1 a las veces que se ha creado esta activity
        setAppLaunchedTimes(this, 1);

        //ANUNCIO
        new LoadAds().execute();

        //OBTENIENDO FRASES DEL ARRAY XML
        statmentList = new StatmentsList(getResources().getStringArray(R.array.statments), getResources().getStringArray(R.array.statmentsWithIP) );
        campoTexto.setText(statmentList.getStatment());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoTexto.setText( statmentList.getStatment() );
                countToShowPopUp();
            }
        });
    }

    private void setCountStaments() {
        if (getIsAgreeShowDialog(this)) {
            countStaments = 1;
        }
    }

    private void countToShowPopUp() {
        if(countStaments < 13){
            countStaments ++;
        }else if (getIsAgreeShowDialog(this)) {
            setCountStaments();
            showRatePopUp();
        }
    }

    private void showRatePopUp(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_rate_app, null);

        AppRate.with(this)
                .setView(view)
                .setInstallDays(0)
                .setLaunchTimes(0)
                .setRemindInterval(0)
                .setAppLaunchedTimesRemindInterval(3)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
    }

    private class LoadAds extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            progressBarAds.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            MobileAds.initialize(ActivityStatments.this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }
}
