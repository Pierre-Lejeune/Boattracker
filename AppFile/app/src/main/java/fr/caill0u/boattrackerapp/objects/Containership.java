package fr.caill0u.boattrackerapp.objects;

import java.util.ArrayList;

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

    public Containership(String name, String captainName, Float latitude, Float longitude, ContainerShipType type) {
        this.name = name;
        this.captainName = captainName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        allContainerShips.add(this);
        this.id = allContainerShips.size();
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
    }
    public void removeContainer(Container container){
        containers.remove(container);
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
    }

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    public void DansLePortLePlusProche(Container container){
        getPortLeplusProche().addContainer(container);
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public ContainerShipType getType() {
        return type;
    }

    public void setType(ContainerShipType type) {
        this.type = type;
    }

    public int getId(){
        return this.id;
    }
    public static ArrayList<Containership> getAllContainerShips() {
        return allContainerShips;
    }
}
