package fr.caill0u.boat_tracker.controllerForLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;
import fr.caill0u.boat_tracker.Utils.Util;
import fr.caill0u.boat_tracker.objects.Containership;

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
            layoutForContainerShip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.getControllerBoat1().loadListBoat(containership);
                }
            });
            listBoat.addView(layoutForContainerShip);
        }
    }
}
