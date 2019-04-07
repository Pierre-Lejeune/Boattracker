package fr.caill0u.boat_tracker.controllerForLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;
import fr.caill0u.boat_tracker.Utils.Util;
import fr.caill0u.boat_tracker.objects.Container;
import fr.caill0u.boat_tracker.objects.Containership;
import fr.caill0u.boat_tracker.objects.Port;

/**
 * Created by Caill0u on 08/03/2019.
 */

public class controllerListContainer {
    private MainActivity mainActivity;
    public controllerListContainer(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }
    public void loadListContainer(Containership containership){
        mainActivity.getLayoutMain().removeAllViews();
        mainActivity.getLayoutMain().addView(LayoutInflater.from(mainActivity).inflate(R.layout.list_container_view, null, false));
        displayContainer(containership);
    }
    public void loadListContainer(Port port){
        mainActivity.getLayoutMain().removeAllViews();
        mainActivity.getLayoutMain().addView(LayoutInflater.from(mainActivity).inflate(R.layout.list_container_view, null, false));
        displayContainer(port);
    }
    private void displayContainer(final Containership containership) {
        LinearLayout listBoat = mainActivity.findViewById(R.id.listPort);
        for (final Container container : containership.getContainers()) {
            LinearLayout layoutForContainerShip = Util.createLayoutForMyContainer(container, mainActivity);
            layoutForContainerShip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.getControllerMoveContainer1().MoveContainerTo(containership, container);
                    mainActivity.getControllerBoat1().loadListBoat(containership);
                }
            });
            listBoat.addView(layoutForContainerShip);
        }
    }
    private void displayContainer(final Port port) {
        LinearLayout listBoat = mainActivity.findViewById(R.id.listPort);
        for (final Container container : port.getContainers()) {
            LinearLayout layoutForContainerShip = Util.createLayoutForMyContainer(container, mainActivity);
            layoutForContainerShip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.getControllerMoveContainer1().MoveContainerTo(port, container);
                    mainActivity.onBackPressed();
                }
            });
            listBoat.addView(layoutForContainerShip);
        }
    }
}
