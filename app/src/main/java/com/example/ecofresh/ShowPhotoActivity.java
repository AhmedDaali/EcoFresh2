package com.example.ecofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ShowPhotoActivity extends AppCompatActivity {
    private ImageView imageView;
    private Bitmap photo;

    private Bitmap foto;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);

        imageView = findViewById(R.id.imageView);
        photo = getIntent().getParcelableExtra("photo");
        imageView.setImageBitmap(photo);

        foto = photo;

        Button btnSave = findViewById(R.id.btnSave);
        // Con esta línea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();

        // Obtenemos una instancia de FirebaseStorage y una referencia al almacenamiento
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto();
            }
        });

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePhoto();
            }
        });
    }
    private void deletePhoto() {
        // Aquí puedes agregar el código para borrar la foto de Firebase
        VentaAguardar.photoToSave = null;

        // Una vez que hayas borrado la foto, puedes volver a la actividad principal
        Intent intent = new Intent(ShowPhotoActivity.this, VentaAguardar.class);
        startActivity(intent);
    }


    private void savePhoto() {
        // Generar un nombre único para el archivo
        String fileName = "image_" + System.currentTimeMillis() + ".jpg";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageData = baos.toByteArray();

        if (VentaAguardar.photoToSave == null) {
            VentaAguardar.photoToSave = new ArrayList<>();
        }

        VentaAguardar.photoToSave.add(imageData);

        // Crea una referencia al almacenamiento de Firebase donde deseas guardar la imagen
        StorageReference imageRef = storageRef.child("images/" + fileName);

        // Carga la imagen en Firebase Storage
        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // La imagen se cargó exitosamente en Firebase Storage
            // Aquí puedes obtener la URL de descarga de la imagen y realizar otras acciones necesarias
            // por ejemplo, guardar la URL en una base de datos Firestore
            imageRef.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                // URL de descarga de la imagen
                String imageUrl = downloadUri.toString();

                // Continúa con la lógica adicional después de cargar la imagen
                Intent intent = new Intent(ShowPhotoActivity.this, VentaAguardar.class);
                // Pasa la imágen y la URL de descarga de imágen como datos extra
                intent.putExtra("photoUrl", imageUrl);
                intent.putExtra("photo", photo);
                startActivity(intent);
            });
        }).addOnFailureListener(e -> {
            // Ocurrió un error al cargar la imagen en Firebase Storage
        });
    }
}