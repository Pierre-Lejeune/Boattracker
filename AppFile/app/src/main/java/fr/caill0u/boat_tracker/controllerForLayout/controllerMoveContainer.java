package fr.caill0u.boat_tracker.controllerForLayout;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;
import fr.caill0u.boat_tracker.objects.Container;
import fr.caill0u.boat_tracker.objects.Containership;
import fr.caill0u.boat_tracker.objects.Port;

public class controllerMoveContainer {
    private MainActivity mainActivity;
    private EditText IdBateauToMove;

    public controllerMoveContainer(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void MoveContainerTo(final Containership containership, final Container container){
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.selectcontainer, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
        alert.setTitle("Déplacer un container");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        IdBateauToMove = alertLayout.findViewById(R.id.idBateauToMove);
        IdBateauToMove.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setPositiveButton("Déplacer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Containership yourTheLuckyContainerShipThatGotANewContainer;
                if((yourTheLuckyContainerShipThatGotANewContainer = Containership.findById(Integer.valueOf(IdBateauToMove.getText().toString()))) != null){
                    containership.removeContainer(container);
                    if(yourTheLuckyContainerShipThatGotANewContainer.addContainerAfterStart(container)){
                        Toast t = Toast.makeText(mainActivity, "Transfert Réussi", Toast.LENGTH_SHORT);
                        t.show();
                    }else{
                        Toast t = Toast.makeText(mainActivity, "Le bateau exist mais ne contient pas la place, le container est dans le port le plus proche", Toast.LENGTH_LONG);
                        t.show();
                    }
                }else{
                    Toast t = Toast.makeText(mainActivity, "Valeur Incorrect", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }

    public void MoveContainerTo(final Port port, final Container container){
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.selectcontainer, null);
        final AlertDialog.Builder alert = new AlertDialog.Builder(mainActivity);
        alert.setTitle("Déplacer un container");
        alert.setView(alertLayout);
        alert.setCancelable(false);
        IdBateauToMove = alertLayout.findViewById(R.id.idBateauToMove);
        IdBateauToMove.setInputType(InputType.TYPE_CLASS_NUMBER);
        alert.setPositiveButton("Déplacer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Containership yourTheLuckyContainerShipThatGotANewContainer;
                if((yourTheLuckyContainerShipThatGotANewContainer = Containership.findById(Integer.valueOf(IdBateauToMove.getText().toString()))) != null){
                    port.removeContainer(container);
                    if(yourTheLuckyContainerShipThatGotANewContainer.addContainerAfterStart(container)){
                        Toast t = Toast.makeText(mainActivity, "Transfert Réussi", Toast.LENGTH_SHORT);
                        t.show();
                    }else{
                        Toast t = Toast.makeText(mainActivity, "Le bateau exist mais ne contient pas la place, le container est dans le port le plus proche", Toast.LENGTH_LONG);
                        t.show();
                    }
                }else{
                    Toast t = Toast.makeText(mainActivity, "Valeur Incorrect", Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
        alert.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alert.show();
    }
}
