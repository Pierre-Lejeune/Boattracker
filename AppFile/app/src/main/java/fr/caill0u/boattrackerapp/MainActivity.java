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

import fr.caill0u.boattrackerapp.Utils.Util;
import fr.caill0u.boattrackerapp.controllerForLayout.*;
import fr.caill0u.boattrackerapp.objects.Containership;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private LinearLayout layoutMain;
    private View MapView;
    private controllerListBoat controllerListBoat1;
    private controllerListPort controllerListPort1;

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
        controllerListBoat1.loadListBoat();
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
            //mapController1.loadMap();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setMainView(String mainView) {
        this.mainView = mainView;
    }
}
