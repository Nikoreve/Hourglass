package com.example.hourglass;

import android.view.View;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

public class Subject {
    private String name;
    private int imageIDgeneral;
    private int imageID1;
    private int imageID2;
//    private CardView cardView;
//    private View view;
    private boolean isClicked;

    public Subject(String name, int imageID1, int imageID2, boolean isClicked) {
        this.name = name;
        this.imageID1 = imageID1;
        this.imageID2 = imageID2;
        this.isClicked = isClicked;
    }


    public Subject(String name, int imageIDgeneral, boolean isClicked){
        this.name = name;
        this.imageIDgeneral = imageIDgeneral;
        this.isClicked = isClicked;
    }

    public int getImageIDgeneral() {
        return imageIDgeneral;
    }

    public void setImageIDgeneral(int imageIDgeneral) {
        this.imageIDgeneral = imageIDgeneral;
    }

    public String getName() {
        return name;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public int getImageID1() {
        return imageID1;
    }

    public int getImageID2() {
        return imageID2;
    }
}

