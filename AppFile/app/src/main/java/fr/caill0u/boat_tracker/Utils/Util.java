package fr.caill0u.boat_tracker.Utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.caill0u.boat_tracker.MainActivity;
import fr.caill0u.boat_tracker.R;
import fr.caill0u.boat_tracker.objects.Container;
import fr.caill0u.boat_tracker.objects.ContainerShipType;
import fr.caill0u.boat_tracker.objects.Containership;
import fr.caill0u.boat_tracker.objects.Port;

/**
 * Created by Caill0u on 08/03/2019.
 */

public class Util {
    public static Float getRandomFloat(float min, float max) {
        Random rand = new Random();
        float result = rand.nextFloat() * (max - min) + min;
        return result;
    }
    public static void createBoat(final FirebaseFirestore db){
        db.collection("containerShip")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            try{
                                for (final QueryDocumentSnapshot boat : task.getResult()) {
                                    final Containership containership = new Containership(boat.getString("name"), boat.getString("captainName"), Float.valueOf(boat.get ("latitude") .toString()), Float.valueOf(boat.get("longitude").toString()), Integer.valueOf(boat.get("id").toString()));
                                    DocumentReference typeRef = db.document(boat.getString("type"));
                                    typeRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot type) {
                                            try{
                                                ContainerShipType containerShipType = new ContainerShipType(Integer.valueOf(type.get("lenght").toString()), Integer.valueOf(type.get("height").toString()), Integer.valueOf(type.get("width").toString()), type.getString("name"), Integer.valueOf(type.get("id").toString()));
                                                containership.setType(containerShipType);
                                            }catch (Exception e){

                                            }

                                        }
                                    });
                                    if(boat.getString("port") != null){
                                        DocumentReference portRef = db.document(boat.getString("port"));
                                        portRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot portDoc) {
                                                Log.w("Port Firebase","Bateau " + containership.getId());
                                                try {
                                                    containership.setPort(Port.getPortById(Integer.valueOf(portDoc.get("id").toString())));
                                                }catch (Exception e){
                                                    Log.e("Port Firebase", "Erreur sur le bateau " + containership.getId() + "Port : " + portDoc.get("id").toString());
                                                }
                                            }
                                        });
                                    }else{
                                        Log.e("Port Firebase", "Le port n'existe pas");
                                    }
                                    List<String> group = (List<String>) boat.get("container");
                                    for(String container:group){
                                        DocumentReference containerRef = db.document(container);
                                        containerRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot containerdoc) {
                                                try{
                                                    Container container = new Container(Integer.valueOf(containerdoc.get("lenght").toString()),Integer.valueOf(containerdoc.get("height").toString()),Integer.valueOf(containerdoc.get("width").toString()), Integer.valueOf(containerdoc.get("id").toString()));
                                                    containership.addContainer(container);
                                                }catch(Exception e){
                                                    Log.e("Boat-Tracker", "Erreur lors de la création du container");
                                                }

                                            }
                                        });
                                    }
                                    Log.w("Container Firebase", "bateau " + containership.getId() + " ok ");
                                }
                            }catch (Exception e){
                                Log.e("Boat-Tracker", "Erreur général d'accès à la base de donnée, vérifier votre connexion internet");
                            }
                        } else {
                            Log.d("Firestore", "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
    public static void createPort(final FirebaseFirestore db){
        db.collection("Port")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot portdoc : task.getResult()) {
                                final Port port = new Port(portdoc.get("name").toString(), Float.valueOf(portdoc.get("latitude").toString()), Float.valueOf(portdoc.get("longitude").toString()), Integer.valueOf(portdoc.get("id").toString()));
                                List<String> group = (List<String>) portdoc.get("container");
                                for(String container:group){
                                    DocumentReference containerRef = db.document(container);
                                    containerRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot containerdoc) {
                                            Container container = new Container(Integer.valueOf(containerdoc.get("lenght").toString()),Integer.valueOf(containerdoc.get("height").toString()),Integer.valueOf(containerdoc.get("width").toString()), Integer.valueOf(containerdoc.get("id").toString()));
                                            port.addContainer(container);
                                        }
                                    });
                                }
                            }
                        } else {
                            Log.d("Port Firebase", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    public static LinearLayout createLayoutForMyContainerShip(Containership containership, MainActivity mainActivity) {
        LinearLayout ficheBateau = new LinearLayout(mainActivity);
        ficheBateau.setOrientation(LinearLayout.HORIZONTAL);
        ficheBateau.setBackgroundResource(R.drawable.border_bottom);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ficheBateau.setElevation(10);
        }
        ficheBateau.setPadding(10,10,10,20);
        LinearLayout.LayoutParams paramsForFicheBateau = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForFicheBateau.gravity = Gravity.CENTER;
        paramsForFicheBateau.topMargin=25;
        ficheBateau.setLayoutParams(paramsForFicheBateau);
        ImageView img = new ImageView(mainActivity);
        img.setBackgroundResource(R.drawable.ic_boaticon120);
        LinearLayout.LayoutParams paramsForImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForImage.gravity = Gravity.CENTER;
        paramsForImage.leftMargin = 15;
        img.setLayoutParams(paramsForImage);
        ficheBateau.addView(img);
        LinearLayout layoutTextBateau = new LinearLayout(mainActivity);
        layoutTextBateau.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramForLayoutOfText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramForLayoutOfText.gravity = Gravity.CENTER;
        layoutTextBateau.setLayoutParams(paramForLayoutOfText);
        layoutTextBateau.addView(createText("Nom : " + containership.getName(), 13, mainActivity));
        layoutTextBateau.addView(createText("Id : " + containership.getId(), 15, mainActivity));
        ficheBateau.addView(layoutTextBateau);
        return ficheBateau;
    }
    public static LinearLayout createLayoutForMyContainer(Container container, MainActivity mainActivity) {
        LinearLayout ficheBateau = new LinearLayout(mainActivity);
        ficheBateau.setOrientation(LinearLayout.HORIZONTAL);
        ficheBateau.setBackgroundResource(R.drawable.border_bottom);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ficheBateau.setElevation(10);
        }
        ficheBateau.setPadding(10,10,10,20);
        LinearLayout.LayoutParams paramsForFicheBateau = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForFicheBateau.gravity = Gravity.CENTER;
        paramsForFicheBateau.topMargin=25;
        ficheBateau.setLayoutParams(paramsForFicheBateau);
        ImageView img = new ImageView(mainActivity);
        img.setBackgroundResource(R.drawable.ic_container);
        LinearLayout.LayoutParams paramsForImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForImage.gravity = Gravity.CENTER;
        paramsForImage.leftMargin = 15;
        img.setLayoutParams(paramsForImage);
        ficheBateau.addView(img);
        LinearLayout layoutTextBateau = new LinearLayout(mainActivity);
        layoutTextBateau.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramForLayoutOfText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramForLayoutOfText.gravity = Gravity.CENTER;
        layoutTextBateau.setLayoutParams(paramForLayoutOfText);
        layoutTextBateau.addView(createText("Hauteur : " + container.getHeight(), 13, mainActivity));
        layoutTextBateau.addView(createText("Longueur : " + container.getWidth(), 13, mainActivity));
        layoutTextBateau.addView(createText("Largeur : " + container.getLenght(), 13, mainActivity));
        ficheBateau.addView(layoutTextBateau);
        return ficheBateau;
    }
    public static LinearLayout createLayoutForMyPort(Port port, MainActivity mainActivity) {
        LinearLayout ficheBateau = new LinearLayout(mainActivity);
        ficheBateau.setOrientation(LinearLayout.HORIZONTAL);
        ficheBateau.setBackgroundResource(R.drawable.border_bottom);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ficheBateau.setElevation(10);
        }
        ficheBateau.setPadding(10,10,10,20);
        LinearLayout.LayoutParams paramsForFicheBateau = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForFicheBateau.gravity = Gravity.CENTER;
        paramsForFicheBateau.topMargin=25;
        ficheBateau.setLayoutParams(paramsForFicheBateau);
        ImageView img = new ImageView(mainActivity);
        img.setBackgroundResource(R.drawable.ic_encreicon);
        LinearLayout.LayoutParams paramsForImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForImage.gravity = Gravity.CENTER;
        paramsForImage.leftMargin = 15;
        img.setLayoutParams(paramsForImage);
        ficheBateau.addView(img);
        LinearLayout layoutTextBateau = new LinearLayout(mainActivity);
        layoutTextBateau.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramForLayoutOfText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramForLayoutOfText.gravity = Gravity.CENTER;
        layoutTextBateau.setLayoutParams(paramForLayoutOfText);
        layoutTextBateau.addView(createText("Nom : " + port.getName(), 13, mainActivity));
        layoutTextBateau.addView(createText("Id : " + port.getId(), 15, mainActivity));
        ficheBateau.addView(layoutTextBateau);
        return ficheBateau;
    }
    public static TextView createText(String s, int size, MainActivity mainActivity) {
        TextView textView = new TextView(mainActivity);
        LinearLayout.LayoutParams paramForText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramForText.gravity = Gravity.CENTER_HORIZONTAL;
        textView.setLayoutParams(paramForText);
        textView.setText(s);
        textView.setTextSize(size);
        return textView;
    }
    public static LinearLayout createLayoutForEditThisParameter(String s, MainActivity mainActivity, EditText editText){
        LinearLayout lnl = new LinearLayout(mainActivity);
        lnl.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramsForFicheBateau = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForFicheBateau.gravity = Gravity.CENTER;
        paramsForFicheBateau.topMargin=25;
        lnl.setLayoutParams(paramsForFicheBateau);
        lnl.addView(createText(s, 10, mainActivity));
        lnl.addView(editText);
        return lnl;
    }

    public static LinearLayout createLayoutForEditThisParameterForNumber(String s, MainActivity mainActivity, EditText editText){
        LinearLayout lnl = new LinearLayout(mainActivity);
        lnl.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams paramsForFicheBateau = new LinearLayout.LayoutParams(800, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsForFicheBateau.gravity = Gravity.CENTER;
        paramsForFicheBateau.topMargin=25;
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        lnl.setLayoutParams(paramsForFicheBateau);
        lnl.addView(createText(s, 10, mainActivity));
        lnl.addView(editText);
        return lnl;
    }
}
