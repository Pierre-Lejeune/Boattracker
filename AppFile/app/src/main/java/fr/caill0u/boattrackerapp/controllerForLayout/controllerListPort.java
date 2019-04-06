package fr.caill0u.boattrackerapp.controllerForLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import fr.caill0u.boattrackerapp.MainActivity;
import fr.caill0u.boattrackerapp.R;
import fr.caill0u.boattrackerapp.Utils.Util;
import fr.caill0u.boattrackerapp.objects.Port;

/**
 * Created by Caill0u on 08/03/2019.
 */

public class controllerListPort {
    private MainActivity mainActivity;
    public controllerListPort(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void loadListPort(){
        mainActivity.getLayoutMain().removeAllViews();
        mainActivity.getLayoutMain().addView(LayoutInflater.from(mainActivity).inflate(R.layout.list_port_layout, null, false));
        displayBoat();
    }
    private void displayBoat() {
        LinearLayout listBoat = mainActivity.findViewById(R.id.listPort);
        for (final Port port : Port.getAllPorts()) {
            LinearLayout layoutForContainerShip = Util.createLayoutForMyPort(port, mainActivity);
            listBoat.addView(layoutForContainerShip);
        }
    }
}
