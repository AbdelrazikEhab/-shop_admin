package com.example.admin;

import android.net.Uri;

import com.google.firebase.firestore.Exclude;

public class product_data {

    String model;
    int price;
    int quantati;
    String Image;
    @Exclude
    private String id;


    public product_data() {
    }



    public product_data(String model, int price, int quantati, String image, String id) {
        this.model = model;
        this.price = price;
        this.quantati = quantati;
        Image = image;
        this.id=id;

    }

    public product_data(String image) {
        Image = image;
    }

    public product_data(String model, int price, int quantati, String image) {
        this.model = model;
        this.price = price;
        this.quantati = quantati;
        Image = image;
    }

    public product_data(String model, int price, int quantati) {
        this.model = model;
        this.price = price;
        this.quantati = quantati;
    }

    public void setModel(String model) {
        this.model = model;
    }
    public String getModel() {
        return model;
    }



    public int getQuantati() {
        return quantati;
    }

    public void setQuantati(int image) {
        this.quantati = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int salary) {
        this.price = salary;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }


    @Override
    public String toString() {
        return "product_data{" +
                "model='" + model + '\'' +
                ", price=" + price +
                ", quantati=" + quantati +
                ", Image='" + Image + '\'' +
                '}';
    }
}

