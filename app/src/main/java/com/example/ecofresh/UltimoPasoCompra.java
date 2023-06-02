package com.example.ecofresh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecofresh.modelo.entidad.Compra;
import com.example.ecofresh.modelo.entidad.DireccionEnvio;
import com.example.ecofresh.modelo.entidad.Producto;
import com.example.ecofresh.modelo.entidad.Usuario;
import com.example.ecofresh.modelo.entidad.Venta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UltimoPasoCompra extends AppCompatActivity {

    // Variable que guardará la referencia del botón Confirmar
    private Button botonConfirm;

    private EditText cantidadEditText, calleEditText, localidadEditext, cpEditext;
    private TextView productoTextView, vendedorTextView, precioTextView, localidadTextView;

    private String nombreProducto, vendedor, comprador, localidad, calle, cp,localidadEnvio, imageUrl, email, categoria;

    private double total, precio, cantidad;

    private FirebaseUser currentUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimo_paso_compra);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();

        // Obtén la instancia de FirebaseFirestore
        db = FirebaseFirestore.getInstance();

        // Obtener usuario actual
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();


        // Referencias a los elementos de la interfaz
        cantidadEditText = findViewById(R.id.EditTextCantidad);
        calleEditText = findViewById(R.id.textDireccion);
        localidadEditext = findViewById(R.id.textLugar);
        cpEditext = findViewById(R.id.textCp);
        productoTextView = findViewById(R.id.textProducto);
        vendedorTextView = findViewById(R.id.textVendedor);
        precioTextView = findViewById(R.id.textPrecio);
        localidadTextView = findViewById(R.id.textLocalidad);

        botonConfirm = findViewById(R.id.btnMenu);

        // Obtener los datos de la venta de la venta del Intent.
        nombreProducto = getIntent().getStringExtra("producto");
        localidad = getIntent().getStringExtra("localidad");
        precio = getIntent().getDoubleExtra("precio", 0.0d);
        vendedor = getIntent().getStringExtra("vendedor");
        categoria = getIntent().getStringExtra("categoria");
        imageUrl = getIntent().getStringExtra("photoUrls");
        cp = getIntent().getStringExtra("cp");

        //Colocar los datos de la venta en los textView
        productoTextView.setText("Producto:   " + nombreProducto);
        precioTextView.setText("Precio€/kg:  " + precio );
        localidadTextView.setText("Localidad:  " + localidad);
        vendedorTextView.setText("Vendedor:    "+vendedor);


        // Obtener los datos del usuario desde Firestore
        obtenerDatosCompra();

        botonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (cantidadEditText != null&& calleEditText != null && cpEditext != null && localidadEditext !=null) {

                    guardarCompra();

                } else {
                    Toast.makeText(UltimoPasoCompra.this, "Rellene los campos antes de guardar la compra", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void obtenerDatosCompra() {
        DocumentReference usuarioRef = db.collection("usuarios").document(currentUser.getEmail());

        usuarioRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Obtener los datos del documento y actualizar los TextView correspondientes
                    email = document.getString("email");
                    comprador = document.getString("nombre")+" "+document.getString("apellidos");
                }
            } else {
                Toast.makeText(UltimoPasoCompra.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarCompra() {


        // Comprobar si la colección "comprasRealizadas" existe, y crearla si no existe
        db.collection("comprasRealizadas").document("dummy").get().addOnFailureListener(e -> {
            if (e.getMessage() != null && e.getMessage().contains("No document exists")) {
                // La colección "usuarios" no existe, se crea el documento dummy
                db.collection("comprasRealizadas").document("dummy").set(new Usuario()).addOnSuccessListener(aVoid -> {
                    // El documento dummy se creó correctamente
                    Toast.makeText(UltimoPasoCompra.this, "Se creó la colección 'comprasRealizadas'", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(error -> {
                    // Error al crear el documento dummy
                    Toast.makeText(UltimoPasoCompra.this, "Error al crear la colección 'comprasRealizadas'", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Otro error al acceder a la colección "usuarios"
                Toast.makeText(UltimoPasoCompra.this, "Error al acceder a la colección 'comprasRealizadas'", Toast.LENGTH_SHORT).show();
            }
        });

        // Obtener los nuevos datos del usuario desde los EditText y TextView
        //nombreProducto = productoTextView.getText().toString().trim();
        //localidad = localidadTextView.getText().toString().trim();
        //vendedor = vendedorTextView.getText().toString().trim();
        cantidad = Double.parseDouble(cantidadEditText.getText().toString().trim());
        calle = calleEditText.getText().toString().trim();
        cp = cpEditext.getText().toString().trim();
        localidadEnvio = localidadEditext.getText().toString().trim();


        // Crea un objeto DireccionEnvio
        DireccionEnvio direccionEnvio = new DireccionEnvio(calle, localidadEnvio,cp );

        // Crea un objeto Producto
        Producto producto = new Producto(nombreProducto, precio, categoria, localidad,imageUrl);

        // Crea un objeto Compra con los datos de la compra
        total = precio * cantidad;
        Compra compra = new Compra(cantidad, total, producto, comprador, vendedor, direccionEnvio);


        // Obtener referencia a la colección de comprasRealizadas en Firestore
        CollectionReference compraRef = db.collection("comprasRealizadas");

        // Agregar la nueva compra a la colección comprasasRealizadas
        compraRef.add(compra)
                .addOnSuccessListener(documentReference -> {

                    Toast.makeText(UltimoPasoCompra.this, "compra guardada exitosamente", Toast.LENGTH_SHORT).show();

                    // Obtener el ID del documento de la venta recién guardada
                    String ventaId = documentReference.getId();

                    // Pasar los datos de la venta como dato extra en el Intent
                    Intent intent = new Intent(UltimoPasoCompra.this, ConfirmCompra.class);

                    intent.putExtra("comprador", comprador);
                    intent.putExtra("vendedor", vendedor);
                    intent.putExtra("total", total);
                    intent.putExtra("cantidad", cantidad);
                    intent.putExtra("producto", nombreProducto);
                    intent.putExtra("localidad", localidadEnvio);
                    intent.putExtra("calle", calle);
                    intent.putExtra("cp:", cp);
                    intent.putExtra("precio", precio);
                    startActivity(intent);

                    // Finalizar la actividad actual
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Error al guardar la compra
                    Toast.makeText(UltimoPasoCompra.this, "Error al guardar la venta", Toast.LENGTH_SHORT).show();
                });
    }


}