package fr.caill0u.boattrackerapp.objects;

import java.util.ArrayList;

public class Port {
    private static ArrayList<Port> allPorts = new ArrayList<>();
    private int id;
    private String name = "";
    private float latitude = Float.valueOf(0);
    private float longitude = Float.valueOf(0);
    private ArrayList<Container> containers = new ArrayList<>();

    public Port(String name, float latitude, float longitude, int id) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        allPorts.add(this);
    }
    public Port(String name, float latitude, float longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        allPorts.add(this);
        this.id = allPorts.size();
    }

    public void addContainer(Container container){
        containers.add(container);
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

    public static Port getPortById(int id){
        Port PortToReturn = null;
        for (Port port: allPorts){
            if(port.getId() == id) PortToReturn = port;
        }
        return PortToReturn;
    }
}
