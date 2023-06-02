package com.example.ecofresh.modelo.entidad;

import android.graphics.Bitmap;

public class Photo {
    private Bitmap photo;

    public Photo(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return photo;
    }
}

