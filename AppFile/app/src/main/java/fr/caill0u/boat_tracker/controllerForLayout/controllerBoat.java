package fr.caill0u.boat_tracker.controllerForLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;
import fr.caill0u.boat_tracker.objects.Containership;

/**
 * Created by Administrateur on 10/03/2019.
 */

public class controllerBoat {
    private MainActivity mainActivity;
    private boolean aDejaClicker = false;
    public controllerBoat(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void loadListBoat(Containership containership){
        aDejaClicker = false;
        mainActivity.getLayoutMain().removeAllViews();
        mainActivity.getLayoutMain().addView(LayoutInflater.from(mainActivity).inflate(R.layout.containership_view, null, false));
        displayBoat(containership);
        mainActivity.setMainView("Boat");
    }
    private void displayBoat(final Containership containership) {
        TextView nomBateau = (TextView) mainActivity.findViewById(R.id.nomBateau);
        TextView nomProprio = (TextView) mainActivity.findViewById(R.id.nomProprio);
        TextView idBateau = (TextView) mainActivity.findViewById(R.id.idBateau);
        TextView position = (TextView) mainActivity.findViewById(R.id.position);
        TextView type = (TextView) mainActivity.findViewById(R.id.type);
        final TextView port = (TextView) mainActivity.findViewById(R.id.port);
        TextView place = (TextView) mainActivity.findViewById(R.id.place);
        final TextView textDistance = (TextView) mainActivity.findViewById(R.id.textDistance);
        final ImageButton consultOnMap = (ImageButton) mainActivity.findViewById(R.id.consultOnMap);
        ImageButton edit = (ImageButton) mainActivity.findViewById(R.id.edit);
        ImageButton container = (ImageButton) mainActivity.findViewById(R.id.addContainer);
        final ImageButton calcdistance = (ImageButton) mainActivity.findViewById(R.id.calcdistance);
        nomBateau.setText("Nom : " + containership.getName());
        nomProprio.setText("Propriétaire : " + containership.getCaptainName());
        idBateau.setText("Id : " + containership.getId());
        position.setText("Longitude : "+containership.getLongitude() + "  |  Latitude : " + containership.getLatitude());
        type.setText("Type : " + containership.getType().getName());
        place.setText("Largeur : " + containership.getType().getLenght() + ", Longueur : " + containership.getType().getWidth()+", Hauteur : " + containership.getType().getHeight() + ", Nbr container:" + containership.getContainers().size());
        if(containership.getPort() == null) port.setText("Port : Ne semble pas ammaré");
        else port.setText("Port : " + containership.getPort().getName());
        consultOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.setContainershipToShow(containership);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getControllerForEditView1().loadListBoat(containership);
            }
        });
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.getAddContainer1().loadNewContainer(containership);
            }
        });
        calcdistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(aDejaClicker){
                    containership.setPort(containership.getPortLeplusProche());
                    mainActivity.getLayoutMain().removeView(calcdistance);
                    textDistance.setText("Bateau ammaré au port " + containership.getPort().getName());
                    port.setText("Port : " + containership.getPort().getName());
                }else{
                    textDistance.setText(containership.getPortLeplusProcheText() + " de distance");
                    calcdistance.setImageResource(R.drawable.ic_encreicon);
                    aDejaClicker = true;
                }
            }
        });

    }

}
