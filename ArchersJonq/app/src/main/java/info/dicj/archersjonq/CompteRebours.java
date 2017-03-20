package info.dicj.archersjonq;

import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    private Button debuter;
    private Button arret;
    /*ptivate string[] prochain["AB", "CD", "CD", "AB"];*/
    int voleeEnCours = 0;
    private CountDownTimer CountDowntimer;
    private long temps = 120;
    private boolean enCours = false;
    private Boolean Danger = false;
    String[] prochainGroupeDeTir = {
            "AB",
            "CD",
            "AB",
            "CD",
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
                    {
                        ((TextView) findViewById(R.id.Temps)).setText("" + "AB");
                        enCours = false;
                        //si le compte à rebours a été initialisé à 10 seconndes, il faut démarrer le compte à rebours de tir
                        if (CompteDepuis == 10) {
                            Tirer(v);
                            return;
                        }
                    }
                    if (temps > 0 ) {

                        handler.postDelayed(this, 1000);
                        if (temps == 30) {
                            findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(100,255 , 168, 8));
                        }

                    } else {
                        temps = 0;
                        findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(255, 255, 0, 0));
                        enCours = false;
                    }
                    ((TextView) findViewById(R.id.Temps)).setText("" + temps);

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
        setContentView(R.layout.activity_compte_rebours);
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
  if (enCours == false) {

     /* détermine si on doit amorcer le compte à rebours pour aller sur la ligne de tir ou démarrer le tir*/
      if (Danger == false)
      {
          if (voleeEnCours % 2 == 0)
          {
              AllerSurLigneDeTir(view1);
          }
          else{
              Tirer(view1);
          }
      }


      enCours = true;


      Log.d("timer", "timer stating");


      Danger = false;

  }

    else
  {
      if (Danger == false)
      {
          Log.d ("timer", "fonction d'erret");
          temps = 0;


      }


    }

}

public void AllerSurLigneDeTir(View view1)
{
    handler.postDelayed(runnable.get(), 0);
    temps = 11;
    CompteDepuis = 10;

    findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(255, 255, 0, 0));
    changeVolee(view1);

    Toast.makeText(getApplicationContext(), "fin de la preparation du tir", Toast.LENGTH_LONG).show();



}

public void Tirer(View view1)
{
    enCours = true;
    handler.postDelayed(runnable.get(), 0);
    temps = 121;
    CompteDepuis = 120;
    findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(100, 0, 255, 24));
    changeVolee(view1);

}

    public void interrompreTimer(View view1)
    {
        enCours = false;
        Danger = true;

    }
    public void terminerTimer (View view1){
       Log.d ("timer", "fonction d'arret");
        temps = 0;
    }

    public void start(View v) {
        timer.setText("30");
        countDownTimer:
        new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("" + millisUntilFinished / 1000);


                Toast.makeText(getApplicationContext(), "fin de la preparation du tir", Toast.LENGTH_LONG).show();
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
        volee.setText("" + Math.ceil(voleeEnCours/2));

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
        AppIndex.AppIndexApi.start(client, viewAction);
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
