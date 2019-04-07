package fr.caill0u.boat_tracker.controllerForLayout;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;

public class controllerConnexion {
    private MainActivity mainActivity;
    private Button buttonCon;
    private EditText login;
    private EditText mdp;
    private Button googleConnect;
    public controllerConnexion(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public boolean LoadConnexion (final MenuItem item){
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.connexion_view, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
        alert.setTitle("Connexion");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        login = alertLayout.findViewById(R.id.Login);
        mdp = alertLayout.findViewById(R.id.mdp);
        googleConnect = alertLayout.findViewById(R.id.googleButtonConnect);
        googleConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODOLOUIS
            }
        });
        alert.setPositiveButton("Connexion", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mainActivity.login(String.valueOf(login.getText()), String.valueOf(mdp.getText()));
                item.setTitle("Deconnexion");
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();

        return FirebaseAuth.getInstance().getCurrentUser() != null;

    }
}
