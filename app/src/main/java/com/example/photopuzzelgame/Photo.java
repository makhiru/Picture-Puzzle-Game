package com.example.photopuzzelgame;

public class Photo {

    int img,tag;

    public Photo(int img, int tag) {
        this.img = img;
        this.tag = tag;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
