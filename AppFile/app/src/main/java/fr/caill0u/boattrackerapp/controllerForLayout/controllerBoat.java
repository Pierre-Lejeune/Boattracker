package fr.caill0u.boattrackerapp.controllerForLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import fr.caill0u.boattrackerapp.MainActivity;
import fr.caill0u.boattrackerapp.R;
import fr.caill0u.boattrackerapp.objects.Containership;

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
        mainActivity.setContainershipToShow(containership);
    }
    private void displayBoat(final Containership containership) {
        TextView nomBateau = (TextView) mainActivity.findViewById(R.id.nomBateau);
        TextView nomProprio = (TextView) mainActivity.findViewById(R.id.nomProprio);
        TextView idBateau = (TextView) mainActivity.findViewById(R.id.idBateau);
        TextView position = (TextView) mainActivity.findViewById(R.id.position);
        TextView type = (TextView) mainActivity.findViewById(R.id.type);
        final TextView port = (TextView) mainActivity.findViewById(R.id.port);
        TextView place = (TextView) mainActivity.findViewById(R.id.place);
        nomBateau.setText("Nom : " + containership.getName());
        nomProprio.setText("Propriétaire : " + containership.getCaptainName());
        idBateau.setText("Id : " + containership.getId());
        position.setText("Longitude : "+containership.getLongitude() + "  |  Latitude : " + containership.getLatitude());
        type.setText("Type : " + containership.getType().getName());
        place.setText("Largeur : " + containership.getType().getLenght() + ", Longueur : " + containership.getType().getWidth()+", Hauteur : " + containership.getType().getHeight() + ", Nbr container:" + containership.getContainers().size());
        if(containership.getPort() == null) port.setText("Port : Ne semble pas ammaré");
        else port.setText("Port : " + containership.getPort().getName());
    }

}
