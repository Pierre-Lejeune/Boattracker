package fr.caill0u.boat_tracker.objects;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Port {
    private static ArrayList<Port> allPorts = new ArrayList<>();
    private int id;
    private String name = "";
    private float latitude = Float.valueOf(0);
    private float longitude = Float.valueOf(0);
    private ArrayList<Container> containers = new ArrayList<>();
    private List<String> containersString = new ArrayList<>();

    public Port(String name, float latitude, float longitude, int id) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        allPorts.add(this);
        pushToFireBase();
    }

    public void addContainer(Container container){
        containers.add(container);
        containersString.add("Container/container" + container.getId());
        pushToFireBase();
    }
    public ArrayList<Container> getContainers(){
        return containers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public static ArrayList<Port> getAllPorts() {
        return allPorts;
    }

    public void pushToFireBase(){
        Map port = new HashMap();
        port.put("name", this.name);
        port.put("longitude", this.longitude);
        port.put("latitude", this.latitude);
        port.put("id", this.id);
        port.put("container", this.containersString);
        FirebaseFirestore.getInstance().document("Port/Port" + this.id).set(port);
    }
    public static Port getPortById(int id){
        Port PortToReturn = null;
        for (Port port: allPorts){
            if(port.getId() == id) PortToReturn = port;
        }
        return PortToReturn;
    }

    public void removeContainer(Container container){
        containers.remove(container);
        containersString.remove("Container/container"+container.getId());
        pushToFireBase();
    }
}
