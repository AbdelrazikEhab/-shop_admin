package com.example.admin;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.Map;

public class Gategory_data extends product_data implements Serializable {

    String name;
    String key;
    @Exclude
    private String id;



    public Gategory_data(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public Gategory_data() {
    }

    @Override
    public String toString() {
        return "Gategory_data{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Gategory_data(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

}
