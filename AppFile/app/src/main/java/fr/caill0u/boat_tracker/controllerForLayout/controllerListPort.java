package fr.caill0u.boat_tracker.controllerForLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;
import fr.caill0u.boat_tracker.Utils.Util;
import fr.caill0u.boat_tracker.objects.Port;

/**
 * Created by Caill0u on 08/03/2019.
 */

public class controllerListPort {
    private MainActivity mainActivity;
    public controllerListPort(MainActivity mainActivity){
        this.mainActivity = mainActivity;
        mainActivity.setMainView("Boat");
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
            layoutForContainerShip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                        if(port.getContainers().size()>0){
                            mainActivity.getControllerListContainer1().loadListContainer(port);
                        }else{
                            Toast t = Toast.makeText(mainActivity, "Ce port ne possède pas de containers", Toast.LENGTH_SHORT);
                            t.show();
                        }
                    }else{
                        Toast t = Toast.makeText(mainActivity, "Connectez vous pour pouvoir modifier le port", Toast.LENGTH_LONG);
                        t.show();
                    }

                }
            });
            listBoat.addView(layoutForContainerShip);
        }
    }
}
