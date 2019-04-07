package fr.caill0u.boat_tracker.objects;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.caill0u.boat_tracker.Utils.Util;

public class Containership {
    private static ArrayList<Containership> allContainerShips = new ArrayList<>();
    private int id;
    private String name ="";
    private String captainName = "";
    private Float latitude = Float.valueOf(0);
    private Float longitude = Float.valueOf(0);
    private Port port = null;
    private ContainerShipType type;
    private ArrayList<Container> containers = new ArrayList<>();
    private List<String> containersString = new ArrayList<>();
    private String portstr = null;

    public Containership(String name, String captainName, Float latitude, Float longitude, ContainerShipType type) {
        this.name = name;
        this.captainName = captainName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        allContainerShips.add(this);
        this.id = allContainerShips.size();
        pushToFirestore();
    }
    public Containership(String name, String captainName, Float latitude, Float longitude, int id) {
        this.name = name;
        this.captainName = captainName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        allContainerShips.add(this);
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public String getName() {
        return name;
    }

    public void addContainer(Container container){
        containers.add(container);
        containersString.add("Container/container"+container.getId());
    }
    public boolean addContainerAfterStart(Container container){
        if(checkIfThisContainerIsNotTooMuch(container)){
            containers.add(container);
            containersString.add("Container/container"+container.getId());
            pushToFirestore();
            return true;
        }else{
            Log.w("Container Boat Add", "Le container était trop grand pour le bateau, celui ci ce trouve désormais dans le port le plus proche");
            DansLePortLePlusProche(container);
            return false;
        }
    }
    public void removeContainer(Container container){
        containers.remove(container);
        containersString.remove("Container/container"+container.getId());
        pushToFirestore();
    }
    private boolean checkIfThisContainerIsNotTooMuch(Container container){
        double placecont=0;
        for (Container container1 : containers){
            placecont += container1.getLenght()*container1.getWidth()*container1.getHeight();
        }
        placecont += container.getWidth()*container.getHeight()*container.getLenght();
        if(((this.getType().getHeight()*this.getType().getWidth()*this.getType().getLenght()))-placecont < 0){
            return false;
        }else{
            return true;
        }
    }
    public Port getPortLeplusProche(){
        Port portleplusProche = null;
        float distance = 1000;
        for (Port port: Port.getAllPorts()){
            double calcdistance = Math.sqrt(
                    ((getLongitude()-port.getLongitude())*(getLongitude()-port.getLongitude()))
                            +
                            ((getLatitude()-port.getLatitude())*(getLatitude()-port.getLatitude()))
            );
            if(calcdistance < distance){
                distance = (float) calcdistance;
                portleplusProche = port;
            }
        }
        return portleplusProche;
    }
    public String getPortLeplusProcheText(){
        Port portleplusProche = null;
        float distance = 1000;
        for (Port port: Port.getAllPorts()){
            double calcdistance = Math.sqrt(
                    ((getLongitude()-port.getLongitude())*(getLongitude()-port.getLongitude()))
                            +
                            ((getLatitude()-port.getLatitude())*(getLatitude()-port.getLatitude()))
            );
            if(calcdistance < distance){
                distance = (float) calcdistance;
                portleplusProche = port;
            }
        }
        return String.valueOf(distance);
    }
    public void setName(String name) {
        this.name = name;
        pushToFirestore();
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
        pushToFirestore();
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
        pushToFirestore();
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
        pushToFirestore();
    }
    public void DansLePortLePlusProche(Container container){
        getPortLeplusProche().addContainer(container);
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
        portstr = "Port/Port" + port.getId();
        pushToFirestore();
    }

    public ContainerShipType getType() {
        return type;
    }

    public void setType(ContainerShipType type) {
        this.type = type;
        this.type.pushToFireBase();
    }

    public void pushToFirestore(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map boat = new HashMap();
        boat.put("name", this.name);
        boat.put("captainName", this.captainName);
        boat.put("latitude", this.latitude);
        boat.put("longitude", this.longitude);
        boat.put("id", this.id);
        boat.put("port", portstr);
        boat.put("type", "containerShipType/ContainerShipType" + type.getId());
        boat.put("container", containersString);
        FirebaseFirestore.getInstance().document("containerShip/containerShip" + this.id).set(boat);
    }

    public int getId(){
        return this.id;
    }
    public static ArrayList<Containership> getAllContainerShips() {
        return allContainerShips;
    }

    public static Containership findById(Integer integer) {
        for(Containership containership : getAllContainerShips()){
            if(containership.getId() == integer) return containership;
        }
        return null;
    }
}
