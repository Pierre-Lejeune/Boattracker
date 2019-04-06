package fr.caill0u.boat_tracker.controllerForLayout;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;

public class controllerInscription {
    private MainActivity mainActivity;
    private EditText login;
    private EditText mdp;
    private EditText verif;

    public controllerInscription(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void LoadInscription(){
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.inscription_view, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
        alert.setTitle("Inscription");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        login = alertLayout.findViewById(R.id.Loginins);
        mdp = alertLayout.findViewById(R.id.mdpins);
        verif = alertLayout.findViewById(R.id.verif);

        alert.setPositiveButton("Inscription", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (String.valueOf(verif.getText()).equals(String.valueOf(mdp.getText())) && String.valueOf(mdp.getText()).length() >= 6){
                    mainActivity.createAccount(String.valueOf(login.getText()), String.valueOf(mdp.getText()));
                    Log.w("tag","Inscription success");
                    Toast.makeText(mainActivity, "Inscription réussie", Toast.LENGTH_LONG).show();
                }
                else if(String.valueOf(mdp.getText()).length() < 6){
                    Log.d("tag", "Mot de passe pas bon");
                    Toast.makeText(mainActivity, "Mot de passe trop court", Toast.LENGTH_LONG).show();
                }
                else{
                    Log.d("tag", "Mot de passe pas bon");
                    Toast.makeText(mainActivity, "Mot de passe différents", Toast.LENGTH_LONG).show();
                }

            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        alert.show();

    }
}