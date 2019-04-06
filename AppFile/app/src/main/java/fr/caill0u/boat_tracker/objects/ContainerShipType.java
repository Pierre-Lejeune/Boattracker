package fr.caill0u.boat_tracker.objects;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContainerShipType {
    private ArrayList<ContainerShipType> allContainerShipType = new ArrayList<>();
    private int id = 0;
    private int lenght =0;
    private int height =0;
    private int width = 0;
    private String name = "";

    public ContainerShipType(int lenght, int height, int width, String name, int id) {
        this.lenght = lenght;
        this.height = height;
        this.width = width;
        this.name = name;
        this.id = id;
        allContainerShipType.add(this);
    }
    public ContainerShipType(int lenght, int height, int width, String name) {
        this.lenght = lenght;
        this.height = height;
        this.width = width;
        this.name = name;
        allContainerShipType.add(this);
        this.id = allContainerShipType.size();
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
        pushToFireBase();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
        pushToFireBase();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        pushToFireBase();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        pushToFireBase();
    }
    public int getId(){
        return this.id;
    }
    public void pushToFireBase(){
        Map type = new HashMap();
        type.put("lenght", this.lenght);
        type.put("height", this.height);
        type.put("width", this.width);
        type.put("name", this.name);
        type.put("id", this.id);
        FirebaseFirestore.getInstance().document("containerShipType/ContainerShipType" + this.id).set(type);
    }
}
