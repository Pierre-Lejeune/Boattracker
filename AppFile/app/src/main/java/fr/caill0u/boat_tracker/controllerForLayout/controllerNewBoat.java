package fr.caill0u.boat_tracker.controllerForLayout;

import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.security.spec.ECField;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;
import fr.caill0u.boat_tracker.Utils.Util;
import fr.caill0u.boat_tracker.objects.Container;
import fr.caill0u.boat_tracker.objects.ContainerShipType;
import fr.caill0u.boat_tracker.objects.Containership;

/**
 * Created by Administrateur on 08/03/2019.
 */

public class controllerNewBoat {
    private MainActivity mainActivity;
    public controllerNewBoat(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void loadnewBoat(){
        mainActivity.getLayoutMain().removeAllViews();
        mainActivity.getLayoutMain().addView(LayoutInflater.from(mainActivity).inflate(R.layout.addboat_layout, null, false));
        displayBoat();
        mainActivity.setMainView("Boat");
    }
    private void displayBoat() {
        final EditText proprietaire = new TextInputEditText(mainActivity);
        final EditText longitude = new TextInputEditText(mainActivity);
        final EditText latitude = new TextInputEditText(mainActivity);
        final EditText name = new TextInputEditText(mainActivity);
        final EditText type = new TextInputEditText(mainActivity);
        final EditText largeur = new TextInputEditText(mainActivity);
        final EditText longueur = new TextInputEditText(mainActivity);
        final EditText hauteur = new TextInputEditText(mainActivity);

        LinearLayout editArea = mainActivity.findViewById(R.id.editAreaContainer);
        final ImageButton confirmEdit = (ImageButton) mainActivity.findViewById(R.id.confirmeNewBoat);
        editArea.addView(Util.createLayoutForEditThisParameter("Nom du bateau :", mainActivity, name));
        editArea.addView(Util.createLayoutForEditThisParameter("Nom du propriétaire :", mainActivity, proprietaire));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Longitude :", mainActivity, longitude));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Latitude :", mainActivity, latitude));
        editArea.addView(Util.createLayoutForEditThisParameter("Type :", mainActivity, type));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Largeur :", mainActivity, largeur));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Longeur :", mainActivity, longueur));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Hauteur :", mainActivity, hauteur));
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Cedit(name) && Cedit(proprietaire) && Cedit(longitude) && Cedit(latitude) && Cedit(type) && Cedit(largeur) && Cedit(longueur) && Cedit(hauteur)){
                    try{
                        ContainerShipType containerShipType = new ContainerShipType(Integer.valueOf(largeur.getText().toString()), Integer.valueOf(hauteur.getText().toString()), Integer.valueOf(longueur.getText().toString()), type.getText().toString(), Containership.getAllContainerShips().size()+1);
                        containerShipType.pushToFireBase();
                        Containership containership = new Containership(name.getText().toString(), proprietaire.getText().toString(), Float.valueOf(latitude.getText().toString()), Float.valueOf(longitude.getText().toString()), containerShipType);
                        containership.pushToFirestore();
                        mainActivity.setContainershipToShow(containership);
                    }catch (Exception e){

                    }

                }else{
                    Toast.makeText(mainActivity, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT);
                }
            }
        });
    }
    public boolean Cedit(EditText editText){
        if(!editText.getText().toString().isEmpty()) return true;
        else return false;
    }
}
