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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class CompteRebours extends AppCompatActivity {
    private TextView timer;
    private Button debuter;
    private Button arret;
    private CountDownTimer CountDowntimer;
    private long temps = 120;
    private Boolean activ = true;
    final ThreadLocal<Runnable> runnable = new ThreadLocal<Runnable>() {
        @Override
        protected Runnable initialValue() {
            return new Runnable() {
                @Override
                public void run() {
                    Log.d("timer", "timer running");
                    if (activ == true)
                    temps--;

                    if (temps > 0 ) {

                        handler.postDelayed(this, 1000);
                        if (temps == 30) {
                            findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(100,255 , 168, 8));
                        }

                    } else {
                        temps = 0;
                        findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(255, 255, 0, 0));
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
        debuter = (Button) findViewById(R.id.Debut);
        /*debuter.setOnClickListener(btn);*/
        arret = (Button) findViewById(R.id.Arret);

        /*timer avec hanndler https://www.youtube.com/watch?v=NXT_ULrFLaA*/
        handler = new Handler();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
public void DemarrerTimer(View view1)
{
    if (activ == true) {
        findViewById(R.id.arrierePlan).setBackgroundColor(Color.argb(100,0 , 255, 24));

        Log.d("timer", "timer stating");
        temps = 35;
        handler.postDelayed(runnable.get(), 1000);
    }
    else
    {
        activ = true;
    }

}

    public void interrompreTimer(View view1)
    {
        activ = false;
    }
    public void terminerTimer (View view1){
       Log.d ("timer", "fonction d'erret");
        temps = 0;
    }

    public void start() {
        timer.setText("30");
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
