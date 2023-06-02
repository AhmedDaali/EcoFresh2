package com.example.ecofresh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImagenCompra extends AppCompatActivity {

    private Button botonComprar;

    private Button botonRegresar;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    private String comprador;
    private String vendedor;
    private String nombreProducto;
    private String localidad;
    private double precio;
    private String emailUsuario;
    private String imageUrl;

    private ImageView imageProducto;

    private List<String> listaImagenes;

    private ArrayAdapter<String> mAdapterImagenes;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen_compra);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();

        //Inicializamos la imágen
        imageProducto = findViewById(R.id.imageView3);

        // Aquí inicializo las instancias de Firebase

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();

        // Obtener los datos de la compra del Intent.
        comprador = getIntent().getStringExtra("comprador");
        vendedor = getIntent().getStringExtra("vendedor");
        nombreProducto = getIntent().getStringExtra("producto");
        localidad = getIntent().getStringExtra("localidad");
        precio = getIntent().getDoubleExtra("precio",0.0d);
        imageUrl = getIntent().getStringExtra("photoUrls");

        // 1 Guardamos la referencia del botón de confirmar
        botonComprar = findViewById(R.id.boton_confirmar_compra);

        // Inicializar la imagen
        imageProducto = findViewById(R.id.imageView3);

        // Obtener la URL de la imagen del Intent
        String imageUrl = getIntent().getStringExtra("photoUrls");

        // Cargar la imagen utilizando Picasso
        Picasso.get().load(imageUrl).into(imageProducto);


        botonComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ImagenCompra.this, UltimoPasoCompra.class);

                intent.putExtra("producto", nombreProducto);
                intent.putExtra("localidad", localidad);
                intent.putExtra("precio", precio);
                intent.putExtra("vendedor", vendedor);
                intent.putExtra("photoUrls", imageUrl);

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 2 Guardamos la referencia del botón de regreso

        botonRegresar = findViewById(R.id.boton_regreso_imagen);

        botonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ImagenCompra.this, Selection.class);

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });


    }
}


