package fr.caill0u.boattrackerapp.objects;

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
}
