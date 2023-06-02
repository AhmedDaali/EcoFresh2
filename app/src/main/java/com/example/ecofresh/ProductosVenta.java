package com.example.ecofresh;

import android.os.Bundle;
import android.view.View;
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

public class ProductosVenta extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String emailUsuario;

    private ListView listViewVentas;

    private List<String> listaVentas;
    private List<String> listaIdVentas = new ArrayList<>();
    private ArrayAdapter<String> mAdapterVentas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_venta);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();

        // Aquí inicializo las instancias de Firebase

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        emailUsuario = mAuth.getCurrentUser().getEmail();
        listViewVentas = findViewById(R.id.listViewProductosVentas);



        // Una vez que entra el usuario a esta activity debemos actualizar la interfaz de usuario con sus propias ventas, del usuario logueado

        actualizarUI();
    }

    private void actualizarUI() {
        db.collection("VentasRealizadas")
                .whereEqualTo("email", emailUsuario)
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
                            listaIdVentas.add(doc.getId());

                            double cantidad = doc.getDouble("cantidad");
                            //precio = getIntent().getFloatExtra("precio", 0.0f);

                            // Obtiene los datos del producto directamente del documento actual
                            double precio = doc.getDouble("producto.precio");
                            String nombre = doc.getString("producto.nombre");

                            // Combina los datos en una sola cadena
                            String venta = "  Producto:     " + nombre + "\n" +
                                    "  Cantidad:     " + cantidad + "\n" +
                                    "  Precio:          " + precio + "€";

                            // Agrega la cadena a la lista
                            listaVentas.add(venta);
                        }

                        if (listaVentas.size() == 0) {
                            listViewVentas.setAdapter(null);
                        } else {
                            mAdapterVentas = new ArrayAdapter<>(ProductosVenta.this, R.layout.item_productos_venta, R.id.ProductoVenta, listaVentas);
                            listViewVentas.setAdapter(mAdapterVentas);
                        }
                    }
                });
    }


// El view que le pasamos a este método es el botón.

    public void retirarProducto(View view){


        // Necesitamos obtener al padre, que es otro view y guardarlo en la variable parent.
        // necesitamos hacer un casting para que sea del mismo tipo  el padre con la variable parent.

        View parent = (View) view.getParent();

        // A través del padre, obtendremos el textView que contiene la información y guardarlo en otra variable.

        TextView productoTextView = parent.findViewById(R.id.ProductoVenta);

        // Ahora de la caja de texto necesitamos el contenido, que guardaremos en otra variable intermedia.
        // Para conseguir el contenido, tenemos la caja, productoTextView, conseguimos el texto y lo pasamos a string.

        String producto = productoTextView.getText().toString();

        // Como la posición del producto, coincide con la posición del id, vamos a obtener al identificador del producto primero para después encontrar el producto.
        // Sacamos la posición de este producto en la lista. la lista de productos tiene un método que se llama indexOf para sacar la posición.

        int posicion = listaVentas.indexOf(producto);

        // Ahora haremos el borrado de datos en la BBDD. Accedemos a la colección 'VentasRealizadas'
        // A continuación al documento cuyo identificador estará en la lista de identificadores
        // De esa lista obtener el identificador que está en la posición que hemos conseguido anteriormente

        db.collection("VentasRealizadas").document(listaIdVentas.get(posicion)).delete();


    }








}