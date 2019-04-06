package fr.caill0u.boattrackerapp;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.caill0u.boattrackerapp.Utils.Util;
import fr.caill0u.boattrackerapp.controllerForLayout.*;
import fr.caill0u.boattrackerapp.objects.Containership;
import fr.caill0u.boattrackerapp.objects.Port;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private LinearLayout layoutMain;
    private GoogleMap mMap;
    private controllerListBoat controllerListBoat1;
    private controllerListPort controllerListPort1;
    private controllerBoat controllerBoat1;
    private mapController mapController1;

    private Containership containershipToShow = null;

    private String mainView = null;
    private int nbrBoat;


    public void setDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public LinearLayout getLayoutMain(){
        return this.layoutMain;
    }
    public controllerBoat getControllerBoat1(){
        return controllerBoat1;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layoutMain = (LinearLayout) findViewById(R.id.layoutMain);
        setDrawer();
        Util.createBoat();
        Util.createPort();
        controllerListBoat1 = new controllerListBoat(this);
        controllerListPort1 = new controllerListPort(this);
        controllerBoat1 = new controllerBoat(this);
        mapController1 = new mapController(this);
        mapController1.loadMap();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(mainView.equals("Boat")){
            controllerListBoat1.loadListBoat();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        for (Containership containership:Containership.getAllContainerShips()){
            if(containership!=containershipToShow){
                LatLng sydney = new LatLng(containership.getLatitude(), containership.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title(containership.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_boaticon32)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
        }
        for (Port port:Port.getAllPorts()){
            LatLng sydney = new LatLng(port.getLatitude(), port.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title(port.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_encreicon)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
        if(containershipToShow != null){
            LatLng sydney = new LatLng(containershipToShow.getLatitude(), containershipToShow.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title(containershipToShow.getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_boaticon120)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(6f));
            containershipToShow = null;

        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.AjoutBoatItem) {
            //controllerNewBoat1.loadnewBoat();
        } else if (id == R.id.ListeBoatItem) {
            controllerListBoat1.loadListBoat();
        } else if(id == R.id.ListePortItem){
            controllerListPort1.loadListPort();
        } else if (id == R.id.MapItem) {
            mapController1.loadMap();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void setContainershipToShow(Containership containershipToShow){
        this.containershipToShow = containershipToShow;
    }


    public void setMainView(String mainView) {
        this.mainView = mainView;
    }
}
