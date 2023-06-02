package com.example.ecofresh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Compras extends AppCompatActivity {

    Button botonRegreso;

    List<String> listaCompras;

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    private String emailUsuario, vendedor, localidad, nombreProducto  ;
    private Double cantidad, precio, total;


    private ListView listViewCompras;

    private List<String> listaIdCompras = new ArrayList<>();
    private ArrayAdapter<String> mAdapterProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compras);

        listViewCompras = findViewById(R.id.listViewComprasRealizadas);
        listViewCompras.setAdapter(mAdapterProductos);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();


        // Aquí inicializo las instancias de Firebase

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();

        // Una vez que entra el usuario a esta activity debemos actualizar la interfaz de usuario con sus propias ventas, del usuario logueado

        actualizarUI();


        // 1 Guardamos la referencia del botón de regreso
        botonRegreso = findViewById(R.id.volver2);


        botonRegreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Compras.this, CuentaUsuario.class);

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });
    }
    private void actualizarUI() {
        db.collection("comprasRealizadas")
                .whereEqualTo("emailComprador", emailUsuario)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            // Manejar el error aquí
                            return;
                        }

                        listaCompras = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            listaIdCompras.add(doc.getId());

                            // Obtiene los datos del producto directamente del documento actual
                            precio = doc.getDouble("producto.precio");
                            nombreProducto = doc.getString("producto.nombre");
                            cantidad = doc.getDouble("cantidad");
                            total = doc.getDouble("total");
                            vendedor = doc.getString("vendedor");
                            localidad = doc.getString("producto.localidad");

                            // Combina los datos en una sola cadena
                            String compra = "  Producto:     " + nombreProducto + "\n" +
                                    "  Localidad:    " + localidad + "\n" +
                                    "  Vendedor:    " + vendedor + "\n" +
                                    "  Cantidad:     " + cantidad +"Kg"+ "\n" +
                                    "  Precio:          " + precio + "€"+ "\n" +
                                    "  Total:            " + total + "€";

                            // Agrega la cadena a la lista
                            listaCompras.add(compra);
                        }

                        if (listaCompras.size() == 0) {
                            listViewCompras.setAdapter(null);
                        } else {
                            mAdapterProductos = new ArrayAdapter<>(Compras.this, R.layout.item_compras_realizadas, R.id.textView2, listaCompras);
                            listViewCompras.setAdapter(mAdapterProductos);
                        }
                    }
                });
    }
}