package com.example.ecofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class ConfirmVenta extends AppCompatActivity {


    // Variable que guardará la referencia del botón Menú principal
    Button botonMenu;

    // Variable que guardará la referencia del botón salir
    Button botonSalir;

    //Variables de textView
    private TextView nombre, nombreProducto, cantidad, precio, localidad;

    private ImageView imageView;

    //Imágen guardada
    private Bitmap photo;

    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_venta);


        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();

        // Inicializar Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Obtener usuario actual
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Referencias a los elementos de la interfaz
        nombre = findViewById(R.id.textVendedor);
        cantidad = findViewById(R.id.textCantidad);
        nombreProducto = findViewById(R.id.textProducto);
        precio = findViewById(R.id.textPrecio);
        localidad = findViewById(R.id.textLocalidad);
        imageView = findViewById(R.id.imageView2);

        // Obtener los datos del usuario desde Firestore
        obtenerDatosUsuario();

        // Obtener los datos de la venta de la venta del Intent.
        double cantidadVenta = getIntent().getDoubleExtra("cantidad", 0.0f);
        String productoVenta = getIntent().getStringExtra("producto");

        double precioVenta = getIntent().getDoubleExtra("precio", 0.0f);
        String localidadVenta = getIntent().getStringExtra("localidad");
        photo = getIntent().getParcelableExtra("photo");

        //Colocar los datos de la venta en los textView
        cantidad.setText("Cantidad:    " + cantidadVenta+"Kg");
        nombreProducto.setText("Producto:   " + productoVenta);
        precio.setText("Precio/Kg:  " + precioVenta + "€/kg");
        localidad.setText("Localidad:  " + localidadVenta);
        imageView.setImageBitmap(photo);


        // 1
        // Aquí daremos la referencia del botón "botonMenu", mediante el identificador que está en la activity_confirm_venta
        // se llama: "btnMenu". Por tanto buscamos con findViewById ese identificador en la clase R, con id "btnMenu"

        botonMenu = findViewById(R.id.btnMenu);


        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.¿?

        botonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // De momento queremos que al hacer click en el botón pasemos a la activity_main.
                // Para ello debemos crear un objeto de la clase Intent. Introduciendo en el paréntesis, que pase de esta activity (this) a la activity_main(MainActivity.class)

                Intent intent = new Intent (ConfirmVenta.this,MainActivity.class);

                //Ponemos la foto a null para la próxima venta
                photo = null;

                // Arrancamos el evento que acabamos de crear

                startActivity(intent);

            }
        });


        // 2
        // Aquí daremos la referencia del botón "botonSalir", mediante el identificador que está en la activity_confirm_venta
        // se llama: "salir". Por tanto buscamos con findViewById ese identificador en la clase R, con id "salir"

        botonSalir = findViewById(R.id.salir);


        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.¿?

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // De momento queremos que al hacer click en el botón pasemos a la activity_inicial.
                // Para ello debemos crear un objeto de la clase Intent. Introduciendo en el paréntesis, que pase de esta activity (this) a la activity_inicial (Inicial.class)

                Intent intent = new Intent (ConfirmVenta.this,Inicial.class);

                //Ponemos la foto a null para la próxima venta
                photo = null;

                // Arrancamos el evento que acabamos de crear

                startActivity(intent);

            }
        });
    }

    private void obtenerDatosUsuario() {
        // Obtener referencia al documento del usuario en Firestore
        DocumentReference usuarioRef = db.collection("usuarios").document(currentUser.getEmail());

        usuarioRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Obtener los datos del documento y actualizar los TextView correspondientes
                    String nombreUser = document.getString("nombre");
                    nombre.setText("Vendedor:  " + nombreUser);
                }
            } else {
                Toast.makeText(ConfirmVenta.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

