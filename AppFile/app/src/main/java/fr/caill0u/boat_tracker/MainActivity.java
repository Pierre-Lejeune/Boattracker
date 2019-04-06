package fr.caill0u.boat_tracker;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.caill0u.boat_tracker.Utils.Util;
import fr.caill0u.boat_tracker.controllerForLayout.*;
import fr.caill0u.boat_tracker.objects.Containership;
import fr.caill0u.boat_tracker.objects.Port;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private LinearLayout layoutMain;
    private GoogleMap mMap;
    private View MapView;
    private mapController mapController1;
    private controllerListBoat controllerListBoat1;
    private controllerNewBoat controllerNewBoat1;
    private controllerForEditView controllerForEditView1;
    private controllerConnexion controllerConnexion1;
    private controllerInscription controllerInscription1;
    private controllerBoat controllerBoat1;
    private controllerListPort controllerListPort1;
    private addContainer addContainer1;

    private Containership containershipToShow = null;

    private String mainView = null;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        Util.createPort(db);
        Util.createBoat(db);
        setDrawer();
        mapController1 = new mapController(this);
        controllerListBoat1 = new controllerListBoat(this);
        controllerNewBoat1 = new controllerNewBoat(this);
        controllerBoat1 = new controllerBoat(this);
        controllerForEditView1 = new controllerForEditView(this);
        controllerInscription1 = new controllerInscription(this);
        controllerListPort1 = new controllerListPort(this);
        addContainer1 = new addContainer(this);
        controllerConnexion1 = new controllerConnexion(this);
        mapController1.setMap();
        mapController1.loadMap();
        mAuth = FirebaseAuth.getInstance();
    }

    public controllerBoat getControllerBoat1() {
        return controllerBoat1;
    }
    public controllerForEditView getControllerForEditView1(){
        return controllerForEditView1;
    }
    public addContainer getAddContainer1(){
        return addContainer1;
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUi();
    }
    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    Log.d("tag", "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                }else{
                    Log.w("tag", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentification failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "signInWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Vous êtes connecté", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Mauvais mot de passe ou login", Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }


    public void Access(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
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

    public void updateUi() {
        MenuItem item = (MenuItem) findViewById(R.id.action_settings);
        if (null != item) {
            item.setTitle("Connexion");
            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                item.setTitle("Deconnexion");
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if(FirebaseAuth.getInstance().getCurrentUser() != null){
                Log.d("MAIN", "User already connected");
                mAuth.signOut();
                item.setTitle("Connexion");
            }else{
                controllerConnexion1.LoadConnexion(item);
                Log.d("MAIN", "User connected");
                if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Log.d("MAIN", "User pouf connected");
                    item.setTitle("Deconnexion");
                }
            }

        }else if(id == R.id.inscription_setting){
            controllerInscription1.LoadInscription();
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.AjoutBoatItem) {
            controllerNewBoat1.loadnewBoat();
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

    public void setContainershipToShow(Containership containershipToShow) {
        this.containershipToShow = containershipToShow;
        this.mapController1.loadMap();
    }

    public void setMainView(String mainView) {
        this.mainView = mainView;
    }
}
