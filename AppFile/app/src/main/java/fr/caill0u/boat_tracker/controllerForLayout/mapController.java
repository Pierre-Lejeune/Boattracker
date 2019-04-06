package fr.caill0u.boat_tracker.controllerForLayout;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;

/**
 * Created by Caill0u on 08/03/2019.
 */

public class mapController {
    private MainActivity mainActivity;
    private View MapView;
    public mapController(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        MapView = LayoutInflater.from(mainActivity).inflate(R.layout.map_layout, null, false);
    }
    public void setMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) mainActivity.getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(mainActivity);
    }
    public void loadMap(){
        mainActivity.getLayoutMain().removeAllViews();
        mainActivity.getLayoutMain().addView(MapView);
        setMap();
    }
}
