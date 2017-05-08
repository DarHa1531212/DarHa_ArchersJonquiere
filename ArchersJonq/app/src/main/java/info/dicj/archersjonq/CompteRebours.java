package info.dicj.archersjonq;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class CompteRebours extends AppCompatActivity {
    private TextView timer;
    private int CompteDepuis;
    private View v;
    private TextView volee;
    private TextView ProchainGroupeDeTir;
    private Button debuter;
    private Button arret;
    /*ptivate string[] prochain["AB", "CD", "CD", "AB"];*/
    int voleeEnCours = 4;
    private CountDownTimer CountDowntimer;
    private long temps = 120;
    private boolean enCours = false;
    private Boolean Danger = false;
    private int i=0;
    SoundPool deuxcoups;
    int deuxcoupsID;

    SoundPool uncoup;
    int uncoupID;

    SoundPool troiscoups;
    int troiscoupsID;

    SoundPool danger;
    int dangerID;

    String[] prochainGroupeDeTir = {
            "AB",
            "CD",
            "CD",
            "AB",
    };
    final ThreadLocal<Runnable> runnable = new ThreadLocal<Runnable>() {
        @Override
        protected Runnable initialValue() {

            return new Runnable() {
                @Override
                public void run() {
                    Log.d("timer", "timer running");
                    if (enCours == true && Danger == false)
                    temps--;


                    if (temps == 0)
                    {                            changeVolee(v);
                        if ( CompteDepuis ==120 )
                        {troiscoups.play(troiscoupsID,1,1,1,0,1);

                            if (voleeEnCours == 44)
                            {
                                ((TextView) findViewById(R.id.Temps)).setText("fini");
                                ((TextView) findViewById(R.id.ProchainGroupeDeTir)).setText("" );
                                ((TextView) findViewById(R.id.volee)).setText("" );
                            }
                            else {
                                i++;
                                if (i == 4) {
                                    i = 0;
                                }

                                temps = 0;
                                ((TextView) findViewById(R.id.ProchainGroupeDeTir)).setText("" + prochainGroupeDeTir[i]);
                                ((TextView) findViewById(R.id.Temps)).setText("" + prochainGroupeDeTir[i]);

                            }
                        }
                        enCours = false;
                        //si le compte à rebours a été initialisé à 10 seconndes, il faut démarrer le compte à rebours de tir
                        if (CompteDepuis == 10) {
                            Tirer(v);
                            return;
                        }
                    }
                    if (temps > 0 ) {
                        int tempsRestant = (int)temps% 60;
                        ((TextView) findViewById(R.id.Temps)).setText("" +(int)Math.floor(temps/60)+":" +String.format("%02d", tempsRestant) );

                        handler.postDelayed(this, 1000);/*remettre a 1000*/
                        if (temps == 30) {
                            findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(100,255 , 166, 0));
                        }

                    } else {
                        temps = 0;
                        findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(255, 255, 0, 0));
                        enCours = false;
                    }


                }
            };
        }
    };

    private Handler handler;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_compte_rebours);

        deuxcoups = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
        deuxcoupsID = deuxcoups.load(this, R.raw.deuxcoups, 1);

        uncoup = new SoundPool (1, AudioManager.STREAM_ALARM, 0);
        uncoupID = uncoup.load (this, R.raw.uncoup, 1);

        troiscoups = new SoundPool (1, AudioManager.STREAM_ALARM, 0);
        troiscoupsID = troiscoups.load(this, R.raw.troiscoups,1);

        danger = new SoundPool(1, AudioManager.STREAM_ALARM, 0);
        dangerID = danger.load(this, R.raw.danger, 1);

        timer = (TextView) findViewById(R.id.Temps);
        volee = (TextView) findViewById(R.id.volee);

        /*debuter.setOnClickListener(btn);*/

        /*timer avec hanndler https://www.youtube.com/watch?v=NXT_ULrFLaA*/
        handler = new Handler();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
public void DemarrerTimer(View view1)
{
 if(voleeEnCours < 44) {

     if (enCours == false) {

     /* détermine si on doit amorcer le compte à rebours pour aller sur la ligne de tir ou démarrer le tir*/
         if (Danger == false) {
             if (voleeEnCours % 2 == 0) {
                 AllerSurLigneDeTir(view1);
             } else {
                 Tirer(view1);
             }
         }


         enCours = true;


         Log.d("timer", "timer stating");


         Danger = false;

     } else {
         if (Danger == false) {
             troiscoups.play(troiscoupsID,1,1,1,0,1);
             Log.d("timer", "fonction d'arret");
             if (CompteDepuis == 120) {
                 i++;
                 if (i == 4) {
                     i = 0;
                 }
                 changeVolee(v);
                 temps = 0;
                 ((TextView) findViewById(R.id.ProchainGroupeDeTir)).setText("" +prochainGroupeDeTir[i]);
                 ((TextView) findViewById(R.id.Temps)).setText("" + prochainGroupeDeTir[i]);
             }


         }


     }
 }
}

public void AllerSurLigneDeTir(View view1)
{

    deuxcoups.play(deuxcoupsID,1,1,1,0,1);
    handler.postDelayed(runnable.get(), 0);
    temps = 11;
    CompteDepuis = 10;

    findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(255, 255, 0, 0));
    // changeVolee(view1);

 //   Toast.makeText(getApplicationContext(), "fin de la preparation du tir", Toast.LENGTH_LONG).show();



}

public void Tirer(View view1)
{
    uncoup.play(uncoupID,1,1,1,0,1);
    enCours = true;
    handler.postDelayed(runnable.get(), 0);
    temps = 121;
    CompteDepuis = 120;
    findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(100, 0, 255, 0));
 //   changeVolee(view1);

}

    public void interrompreTimer(View view1)
    {
        enCours = false;
        Danger = true;
//faire jouer le sond
        danger.play(dangerID,1,1,1,0,1);

    }
    public void terminerTimer (View view1){
       Log.d ("timer", "fonction d'arret");
        temps = 0;
    }
//le start ici
    public void start(View v) {
     //   timer.setText("30");
        countDownTimer:
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timer.setText("Done!");
            }
        };

    }

    public void changeVolee(View view1)
    {
        voleeEnCours++;
       // volee.setText("" + (int)Math.ceil(voleeEnCours/4));
        ((TextView) findViewById(R.id.volee)).setText(""+(int)Math.ceil(voleeEnCours/4));

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CompteRebours Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://info.dicj.archersjonq/http/host/path")
        );
      //  AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "CompteRebours Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://info.dicj.archersjonq/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
