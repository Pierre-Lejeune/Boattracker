package fr.caill0u.boat_tracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.caill0u.boat_tracker.Utils.Util;

public class StartActivity extends AppCompatActivity {

    ImageView boatanimationimage;
    LinearLayout startLayout;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startLayout = (LinearLayout) findViewById(R.id.startLayout);
        boatanimationimage = (ImageView) findViewById(R.id.boatanimationimage);
        AnimationDrawable animation = (AnimationDrawable) boatanimationimage.getBackground();
        animation.start();
        if(isNetworkConnected() && internetIsConnected()){
            db= FirebaseFirestore.getInstance();
            Util.createPort(db);
            Util.createBoat(db);
            Button button = new Button(this);
            button.setText("Lancer l'application");
            startLayout.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            TextView textView = new TextView(this);
            textView.setText("Vous n'êtes pas connecter, activé internet et relancer l'application");
            startLayout.addView(textView);
        }

    }
    public boolean internetIsConnected() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() < 100);
        } catch (Exception e) {
            return false;
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
