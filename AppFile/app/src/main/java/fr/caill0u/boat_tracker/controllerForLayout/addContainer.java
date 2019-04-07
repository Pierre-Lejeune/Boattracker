package fr.caill0u.boat_tracker.controllerForLayout;

import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;
import fr.caill0u.boat_tracker.Utils.Util;
import fr.caill0u.boat_tracker.objects.Container;
import fr.caill0u.boat_tracker.objects.Containership;

/**
 * Created by Caill0u on 10/03/2019.
 */

public class addContainer {
    private MainActivity mainActivity;
    public addContainer(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void loadNewContainer(Containership containership){
        mainActivity.getLayoutMain().removeAllViews();
        mainActivity.getLayoutMain().addView(LayoutInflater.from(mainActivity).inflate(R.layout.addcontainer, null, false));
        displayContainer(containership);
        mainActivity.setMainView("Boat");
    }
    private void displayContainer(final Containership containership) {
        final EditText largeur = new TextInputEditText(mainActivity);
        final EditText longueur = new TextInputEditText(mainActivity);
        final EditText hauteur = new TextInputEditText(mainActivity);

        LinearLayout editArea = mainActivity.findViewById(R.id.editAreaContainer);
        final ImageButton confirmEdit = (ImageButton) mainActivity.findViewById(R.id.confirmeNewContainer);
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Largeur :", mainActivity, largeur));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Longeur :", mainActivity, longueur));
        editArea.addView(Util.createLayoutForEditThisParameterForNumber("Hauteur :", mainActivity, hauteur));
        confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!largeur.getText().toString().isEmpty() && !longueur.getText().toString().isEmpty() && !hauteur.getText().toString().isEmpty()){
                    Container container = new Container(Integer.valueOf(largeur.getText().toString()), Integer.valueOf(hauteur.getText().toString()), Integer.valueOf(longueur.getText().toString()));
                    if(containership.addContainerAfterStart(container)){
                        Toast t = Toast.makeText(mainActivity, "Container ajouté au bateau " + containership.getName() + " avec succés", Toast.LENGTH_SHORT);
                        t.show();
                    }else{
                        Toast t = Toast.makeText(mainActivity, "Le container prends trop de place, il à été placé dans le port le plus proche", Toast.LENGTH_LONG);
                        t.show();
                    }
                    mainActivity.setContainershipToShow(containership);
                }else{
                    Toast.makeText(mainActivity, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
