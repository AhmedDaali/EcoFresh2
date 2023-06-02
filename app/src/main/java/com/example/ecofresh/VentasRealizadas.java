package com.example.ecofresh;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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

public class VentasRealizadas extends AppCompatActivity {

    Button botonRegreso;

    List<String> listaVentas;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String emailUsuario, comprador, cp, calle, localidad, nombreProducto  ;
    private Double cantidad, precio, total;


    private ListView listViewVentas;

    private List<String> listaIdventas = new ArrayList<>();
    private ArrayAdapter<String> mAdapterProductos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventas_realizadas);

        listViewVentas = findViewById(R.id.listViewVentasRealizadas);
        listViewVentas.setAdapter(mAdapterProductos);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();


        // Aquí inicializo las instancias de Firebase

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();

        // Una vez que entra el usuario a esta activity debemos actualizar la interfaz de usuario con sus propias ventas, del usuario logueado

        actualizarUI();


        // 1 Guardamos la referencia del botón de regreso
        botonRegreso = findViewById(R.id.volver3);


        botonRegreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(VentasRealizadas.this, CuentaUsuario.class);

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });
    }
    private void actualizarUI() {
        db.collection("comprasRealizadas")
                .whereEqualTo("emailVendedor", emailUsuario)
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
                            listaIdventas.add(doc.getId());

                            // Obtiene los datos del producto directamente del documento actual
                            precio = doc.getDouble("producto.precio");
                            nombreProducto = doc.getString("producto.nombre");
                            cantidad = doc.getDouble("cantidad");
                            total = doc.getDouble("total");
                            comprador = doc.getString("comprador");
                            localidad = doc.getString("direccionEnvio.localidad");
                            calle = doc.getString("direccionEnvio.calle");
                            cp = doc.getString("direccionEnvio.cp");

                            // Combina los datos en una sola cadena
                            String compra = "  Producto:      " + nombreProducto + "\n" +
                                    "  Comprador:  " + comprador + "\n" +
                                    "  Cantidad:      " + cantidad +"Kg"+ "\n" +
                                    "  Precio:           " + precio + "€"+ "\n" +
                                    "  Total:             " + total + "€"+"\n" +"\n" +
                                    "  [Dirección de envío]:"+"\n" +
                                    "       Localidad:   " + localidad + "\n" +
                                    "       calle:            " + calle + "\n"+
                                    "       Cp:                "+ cp +"\n" ;

                            // Agrega la cadena a la lista
                            listaVentas.add(compra);
                        }

                        if (listaVentas.size() == 0) {
                            listViewVentas.setAdapter(null);
                        } else {
                            mAdapterProductos = new ArrayAdapter<>(VentasRealizadas.this, R.layout.item_ventas_realizadas, R.id.textViewVenta, listaVentas);
                            listViewVentas.setAdapter(mAdapterProductos);
                        }
                    }
                });
    }


}
