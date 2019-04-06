package fr.caill0u.boat_tracker.objects;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Container {
    private int id;
    private int lenght=  0;
    private int height= 0;
    private int width=0;


    private static ArrayList<Container> allContainers = new ArrayList<>();

    public Container(int lenght, int height, int width, int id) {
        this.lenght = lenght;
        this.height = height;
        this.width = width;
        this.id = id;
        allContainers.add(this);
    }
    public Container(int lenght, int height, int width) {
        this.lenght = lenght;
        this.height = height;
        this.width = width;
        allContainers.add(this);
        this.id = allContainers.size();
        pushToFirestore();
    }

    public int getId() {
        return id;
    }

    public int getLenght() {
        return lenght;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setId(int id) {
        this.id = id;
    }


    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public static ArrayList<Container> getAllContainers(){
        return allContainers;
    }
    private void pushToFirestore(){
        Map container = new HashMap();
        container.put("lenght", this.lenght);
        container.put("width", this.width);
        container.put("height", this.height);
        container.put("id", this.id);
        FirebaseFirestore.getInstance().document("Container/container" + this.id).set(container);
    }
}
