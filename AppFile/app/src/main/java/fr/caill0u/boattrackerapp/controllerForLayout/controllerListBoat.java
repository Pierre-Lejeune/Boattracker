package fr.caill0u.boattrackerapp.controllerForLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import fr.caill0u.boattrackerapp.MainActivity;
import fr.caill0u.boattrackerapp.R;
import fr.caill0u.boattrackerapp.Utils.Util;
import fr.caill0u.boattrackerapp.objects.Containership;

/**
 * Created by Caill0u on 08/03/2019.
 */

public class controllerListBoat {
    private MainActivity mainActivity;
    public controllerListBoat(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void loadListBoat(){
        mainActivity.getLayoutMain().removeAllViews();
        mainActivity.getLayoutMain().addView(LayoutInflater.from(mainActivity).inflate(R.layout.listboat_layout, null, false));
        displayBoat();
    }
    private void displayBoat() {
        LinearLayout listBoat = mainActivity.findViewById(R.id.listBoat);
        for (final Containership containership : Containership.getAllContainerShips()) {
            LinearLayout layoutForContainerShip = Util.createLayoutForMyContainer(containership, mainActivity);
            listBoat.addView(layoutForContainerShip);
        }
    }
}
