package com.example.ecofresh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class Muestrario extends AppCompatActivity {

    Button botonRegreso;

    List<String> listaVentas;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String emailUsuario, vendedor, nombre, localidad, urlImagen, categoria  ;
    private Double cantidad;
    private Double precio;

    private ListView listViewProductos;

    private List<String> listaIdProductos = new ArrayList<>();
    private ArrayAdapter<String> mAdapterProductos;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestrario);

        // Obtener los datos de la venta de la venta del Intent.
        String nombreProducto = getIntent().getStringExtra("producto");
        ;

        listViewProductos = findViewById(R.id.listViewProductos);
        listViewProductos.setAdapter(mAdapterProductos);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();



        // 1 Guardamos la referencia del botón de regreso
        botonRegreso = findViewById(R.id.volver);


        botonRegreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Muestrario.this,Selection.class);

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });



        // Aquí inicializo las instancias de Firebase

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();
        listViewProductos = findViewById(R.id.listViewProductos);


        // Una vez que entra el usuario a esta activity debemos actualizar la interfaz de usuario
        actualizarUI( nombreProducto);


    }


    private void actualizarUI(String nombreProducto) {

        db.collection("VentasRealizadas")
                .whereEqualTo("producto.nombre", nombreProducto)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            // Manejar el error aquí
                            return;
                        }


                        listaVentas = new ArrayList<>();

                        //listaImagenes.clear(); // Limpiar la lista de imágenes antes de agregar las nuevas



                        for (QueryDocumentSnapshot doc : value) {
                            listaIdProductos.add(doc.getId());





                            // Obtiene los datos del producto directamente del documento actual
                            precio =  doc.getDouble("producto.precio");
                            nombre = doc.getString("producto.nombre");
                            localidad = doc.getString("producto.localidad");
                            urlImagen = doc.getString("producto.photoUrls");
                            cantidad =  doc.getDouble("cantidad");
                            vendedor = doc.getString("vendedor");
                            categoria = doc.getString("categoria");

                            // Combina los datos en una sola cadena
                            String venta = "  Producto:     " + nombre + "\n" +
                                    "  Vendedor:    " + vendedor + "\n" +
                                    "  Localidad:    " + localidad + "\n" +
                                    "  Stock:           " + cantidad + "\n" +
                                    "  Precio:          " + precio;

                            // Agrega la cadena a la lista
                            listaVentas.add(venta);
                        }

                        if (listaVentas.size() == 0) {
                            listViewProductos.setAdapter(null);
                        } else {
                            mAdapterProductos = new ArrayAdapter<>(Muestrario.this, R.layout.item_muestrario, R.id.textViewProducto, listaVentas);
                            listViewProductos.setAdapter(mAdapterProductos);
                        }

                    }

                });

    }


    public void mostrarProducto (View view){

        // Así obtenemos acceso al padre del textView, en este caso es el listView

        View parent = (View) view.getParent();

        // Obtenemos el textView. Guardamos en esta variable el TextView a través del padre

        //TextView ProductoTextView = parent.findViewById(R.id.textViewMuestraProducto);

        // Ahora necesitamos el contenido del anterior TextView, que guardaremoms en la variable siguiente

        //String producto = ProductoTextView.getText().toString();

        TextView ProductosTextView = parent.findViewById(R.id.textViewProducto);
        Intent intent = new Intent(Muestrario.this, ImagenCompra.class);

        //intent.putExtra("cantidad", cantidad);
        intent.putExtra("producto", nombre);
        intent.putExtra("localidad", localidad);
        intent.putExtra("precio", precio);
        intent.putExtra("vendedor", vendedor);
        intent.putExtra("photoUrls",urlImagen);
        intent.putExtra("categoria",categoria);

        // Arrancamos el evento que acabamos de crear
        startActivity(intent);



    }


}
/*package com.example.ecofresh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Muestrario extends AppCompatActivity {

    Button botonRegreso;

    List<String> listaVentas;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String emailUsuario, vendedor, nombre, localidad, urlImagen ;
    private double cantidad;
    private double precio;

    private ListView listViewProductos;

    private List<String> listaIdProductos = new ArrayList<>();
    private ArrayAdapter<String> mAdapterProductos;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muestrario);

        // Obtener los datos de la venta de la venta del Intent.
        String nombreProducto = getIntent().getStringExtra("producto");


        listViewProductos = findViewById(R.id.listViewProductos);
        listViewProductos.setAdapter(mAdapterProductos);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();



        // 1 Guardamos la referencia del botón de regreso
        botonRegreso = findViewById(R.id.volver);


        botonRegreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Muestrario.this,Selection.class);

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });



        // Aquí inicializo las instancias de Firebase

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();
        listViewProductos = findViewById(R.id.listViewProductos);


        // Una vez que entra el usuario a esta activity debemos actualizar la interfaz de usuario con sus propias ventas, del usuario logueado
        actualizarUI( nombreProducto);


    }

    private void actualizarUI(String nombreProducto) {

        db.collection("VentasRealizadas")
                .whereEqualTo("producto.nombre", nombreProducto)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            // Manejar el error aquí
                            return;
                        }

                       listaVentas = new ArrayList<>();


                        for (QueryDocumentSnapshot doc : value) {
                            listaIdProductos.add(doc.getId());
                            Object cantidadObj = doc.get("cantidad");
                            Object precioObj = doc.get("precio");
                            if (cantidadObj instanceof Number && precioObj instanceof Number) {
                                cantidad = ((Number) cantidadObj).doubleValue();
                                precio = ((Number) precioObj).doubleValue();
                                //cantidad = doc.getDouble("cantidad");
                                vendedor = doc.getString("vendedor");

                                // Obtiene los datos del producto directamente del documento actual
                                precio = doc.getDouble("producto.precio");
                                nombre = doc.getString("producto.nombre");
                                localidad = doc.getString("producto.localidad");
                                urlImagen = doc.getString("producto.photoUrls");

                                // Combina los datos en una sola cadena
                                String venta = "  Producto:     " + nombre + "\n" +
                                        "  Vendedor:    " + vendedor + "\n" +
                                        "  Localidad:    " + localidad + "\n" +
                                        "  Stock:           " + cantidad + "\n" +
                                        "  Precio:          " + precio;

                                // Agrega la cadena a la lista
                                listaVentas.add(venta);
                            } else {

                            }
                        }

                        if (listaVentas.size() == 0) {
                            listViewProductos.setAdapter(null);
                        } else {
                            mAdapterProductos = new ArrayAdapter<>(Muestrario.this, R.layout.item_muestrario, R.id.textViewProducto, listaVentas);
                            listViewProductos.setAdapter(mAdapterProductos);
                        }

                    }
                });
    }


    public void mostrarProducto (View view){

    // Así obtenemos acceso al padre del textView, en este caso es el listView

    View parent = (View) view.getParent();

    // Obtenemos el textView. Guardamos en esta variable el TextView a través del padre

    //TextView ProductoTextView = parent.findViewById(R.id.textViewMuestraProducto);

    // Ahora necesitamos el contenido del anterior TextView, que guardaremoms en la variable siguiente

        //String producto = ProductoTextView.getText().toString();

        TextView ProductosTextView = parent.findViewById(R.id.textViewProducto);
        Intent intent = new Intent(Muestrario.this, ImagenCompra.class);

        intent.putExtra("cantidad", cantidad);
        intent.putExtra("producto", nombre);
        intent.putExtra("localidad", localidad);
        intent.putExtra("precio", precio);
        intent.putExtra("vendedor", vendedor);
        intent.putExtra("photoUrls",urlImagen);

        // Arrancamos el evento que acabamos de crear
        startActivity(intent);



    }


}*/