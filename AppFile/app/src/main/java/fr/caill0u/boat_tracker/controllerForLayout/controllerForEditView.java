package fr.caill0u.boat_tracker.controllerForLayout;

import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;
import fr.caill0u.boat_tracker.Utils.Util;
import fr.caill0u.boat_tracker.objects.Containership;

/**
 * Created by Administrateur on 10/03/2019.
 */

public class controllerForEditView {
    private MainActivity mainActivity;
    public controllerForEditView(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void loadListBoat(Containership containership){
        mainActivity.getLayoutMain().removeAllViews();
        mainActivity.getLayoutMain().addView(LayoutInflater.from(mainActivity).inflate(R.layout.editboat_view, null, false));
        displayBoat(containership);
        mainActivity.setMainView("Boat");
    }
    private void displayBoat(final Containership containership) {
        final EditText nomBateau = new TextInputEditText(mainActivity);
        final EditText nomProprio = new TextInputEditText(mainActivity);
        final EditText longitude = new TextInputEditText(mainActivity);
        final EditText latitude = new TextInputEditText(mainActivity);
        final EditText type = new TextInputEditText(mainActivity);
        final EditText largeur = new TextInputEditText(mainActivity);
        final EditText longueur = new TextInputEditText(mainActivity);
        final EditText hauteur = new TextInputEditText(mainActivity);

        LinearLayout editArea = mainActivity.findViewById(R.id.editArea);
        final ImageButton confirmEdit = (ImageButton) mainActivity.findViewById(R.id.confirmedit);
        editArea.addView(Util.createLayoutForEditThisParameter("Nom Bateau :", mainActivity, nomBateau));
        editArea.addView(Util.createLayoutForEditThisParameter("Nom Propriétaire:", mainActivity, nomProprio));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Longitude : ", mainActivity, longitude));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Latitude :", mainActivity, latitude));
        editArea.addView(Util.createLayoutForEditThisParameter("Type :", mainActivity, type));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Largeur :", mainActivity, largeur));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Longeur :", mainActivity, longueur));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Hauteur :", mainActivity, hauteur));
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nomProprio.getText().toString().isEmpty()) containership.setCaptainName(nomProprio.getText().toString());
                if(!latitude.getText().toString().isEmpty()) containership.setLatitude(Float.valueOf(latitude.getText().toString()));
                if(!longitude.getText().toString().isEmpty()) containership.setLongitude(Float.valueOf(longitude.getText().toString()));
                if(!nomBateau.getText().toString().isEmpty()) containership.setName(nomBateau.getText().toString());
                if(!type.getText().toString().isEmpty()) containership.getType().setName(type.getText().toString());
                if(!largeur.getText().toString().isEmpty()) containership.getType().setLenght(Integer.valueOf(largeur.getText().toString()));
                if(!longueur.getText().toString().isEmpty()) containership.getType().setWidth(Integer.valueOf(longueur.getText().toString()));
                if(!hauteur.getText().toString().isEmpty()) containership.getType().setHeight(Integer.valueOf(hauteur.getText().toString()));
                containership.pushToFirestore();
                mainActivity.setContainershipToShow(containership);
            }
        });
    }
}
