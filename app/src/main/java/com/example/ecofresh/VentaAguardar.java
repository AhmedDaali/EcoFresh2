package com.example.ecofresh;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecofresh.modelo.entidad.Compra;
import com.example.ecofresh.modelo.entidad.Usuario;
import com.example.ecofresh.modelo.entidad.Venta;
import com.example.ecofresh.modelo.entidad.Producto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;


public class VentaAguardar extends AppCompatActivity {

    private FirebaseFirestore db;


    // Variable que guardará la referencia del botón subir foto
    private Button botonSubirFoto;

    // Variable que guardará la referencia del botón Confirmar
    private Button botonConfirm;

    // Esto es el código para la entrada de texto autocompletable
    private AutoCompleteTextView autoCategoria;

    private String[] categorias = {

            "Frutas", "Verduras", "Hortalizas", "Legumbres"

    };
    //La foto que se guardará;
    public static List<byte[]> photoToSave;
    private  Bitmap photo;
    private EditText cantidadEditText, productoEditText,
            precioEditext,localidadEditext, emailEditext;

    private String nombre, apellidos, telefono, direccion, nombreProducto,categoria, localidad, email, vendedor;

    private double precio, cantidad;

    private static final String PRODUCTOS_KEY = "cajaProductos";
    private static final String CANTIDAD_KEY = "cajaCantidad";
    private static final String PRECIO_KEY = "cajaPrecio";
    private static final String LOCALIDAD_KEY = "cajaLocalidad";

    private FirebaseUser currentUser;
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();

        // Obtén la instancia de FirebaseFirestore
        db = FirebaseFirestore.getInstance();

        // Obtener usuario actual
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Obtener la URL de descarga de la imágen del intent
        String photoUrl = getIntent().getStringExtra("photoUrl");

        //Obtener la imágen
        photo = getIntent().getParcelableExtra("photo");

        // Referencias a los elementos de la interfaz
        cantidadEditText = findViewById(R.id.cajaCantidad);
        productoEditText = findViewById(R.id.cajaProductos);
        precioEditext = findViewById(R.id.cajaPrecio);
        localidadEditext = findViewById(R.id.cajaLocalidad);
        autoCategoria = findViewById(R.id.text_auto);


        // Obtener referencia al documento del usuario en Firestore
        DocumentReference usuarioRef = db.collection("usuarios").document(currentUser.getEmail());

        // Obtener los datos del usuario desde Firestore
        obtenerDatosUsuario();


        Button btnOpenCamera = findViewById(R.id.subirFoto);
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }

             /**
              * Método para abrir la cámara y capturar una foto.
              */
            private void openCamera() {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    cameraLauncher.launch(intent);
                }
            }
        });

        // Configurar el lanzador de resultados de actividad para capturar la imagen de la cámara
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // Se verifica si el resultado de la actividad es RESULT_OK, lo que indica que la captura de la foto fue exitosa.
                        if (result.getResultCode() == RESULT_OK) {
                            // Se obtiene el intent que contiene los datos de retorno de la actividad de la cámara.
                            Intent data = result.getData();
                            // Se obtiene la imagen capturada como un objeto Bitmap desde los extras del intent.
                            Bitmap photo = (Bitmap) data.getExtras().get("data");
                            // Se crea un nuevo intent para iniciar la actividad ShowPhotoActivity
                            // y se le pasa el objeto Bitmap (photo) como dato extra bajo la clave "photo".
                            Intent intent = new Intent(VentaAguardar.this, ShowPhotoActivity.class);
                            intent.putExtra("photo", photo);
                            // Se inicia la actividad ShowPhotoActivity.
                            startActivity(intent);
                        }
                    }
                });



        // 2
        // Aquí daremos la referencia del botón "botonConfirm", mediante el identificador que está en la activity_venta
        // se llama: "Confirmar". Por tanto buscamos con findViewById ese identificador en la clase R, con id "Confirmar"

        botonConfirm = findViewById(R.id.Confirmar);


        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.¿?

        botonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*// Obtener los nuevos datos del usuario desde los EditText
                String cantidad = cantidadEditText.getText().toString().trim();
                String nombreProducto = productoEditText.getText().toString().trim();
                String localidad = localidadEditext.getText().toString().trim();
                String precio = precioEditext.getText().toString().trim();
                String categoria =  autoCategoria.getText().toString().trim();*/

                if (photoToSave != null) {
                    // Guarda la foto capturada pasando la URL de descarga como argumento
                    guardarVenta(photoUrl);
                    // De momento queremos que al hacer click en el botón pasemos a la siguiente activity_confirm_venta.
                    // Para ello debemos crear un objeto de la clase Intent. Introduciendo en el paréntesis, que pase de esta activity (this) a la activity_confirm_venta (ConfirmVenta.class)

                    Intent intent = new Intent(VentaAguardar.this, ConfirmVenta.class);
                    // Arrancamos el evento que acabamos de crear
                    startActivity(intent);

                    // Finalizar la actividad actual
                    finish();

                } else {
                    Toast.makeText(VentaAguardar.this, "Captura una foto antes de guardar la venta", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Esto se hace para que se autocomplete la entrada de texto de la categoría

        autoCategoria = (AutoCompleteTextView) findViewById(R.id.text_auto);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categorias);
        autoCategoria.setThreshold(3); // Indicamos con cuántas letras empezará a autocompletar
        autoCategoria.setAdapter(adapter);


    }
    // Obtener referencia al documento del usuario en Firestore
    //DocumentReference usuarioRef = db.collection("usuarios").document(currentUser.getEmail());

    private void obtenerDatosUsuario() {
        DocumentReference usuarioRef = db.collection("usuarios").document(currentUser.getEmail());


        usuarioRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Obtener los datos del documento y actualizar los TextView correspondientes
                    email = document.getString("email");
                    vendedor = document.getString("nombre")+" "+document.getString("apellidos");
                }
            } else {
                Toast.makeText(VentaAguardar.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardarVenta(String photoUrl) {


        // Obtener los nuevos datos del usuario desde los EditText
        cantidad = Double.parseDouble(cantidadEditText.getText().toString().trim());
        nombreProducto = productoEditText.getText().toString().trim();
        localidad = localidadEditext.getText().toString().trim();
        precio = Double.parseDouble(precioEditext.getText().toString().trim());
        categoria =  autoCategoria.getText().toString().trim();

        // Crea un objeto Producto
        Producto producto = new Producto(nombreProducto.toLowerCase(), precio, categoria, localidad, photoUrl);
        // Crea un objeto Venta con los datos de la venta
        //Venta venta = new Venta(cantidad, producto, currentUser.getEmail());
        Venta venta = new Venta(cantidad, producto, email, vendedor);


        // Obtener referencia a la colección de VentasRealizadas en Firestore
        CollectionReference ventasRef = db.collection("VentasRealizadas");

        // Agregar la nueva venta a la colección VentasRealizadas
        ventasRef.add(venta)
                .addOnSuccessListener(documentReference -> {
                    // Venta guardada correctamente
                    Toast.makeText(VentaAguardar.this, "Venta guardada exitosamente", Toast.LENGTH_SHORT).show();

                    // Obtener el ID del documento de la venta recién guardada
                    String ventaId = documentReference.getId();

                    // Pasar los datos de la venta como dato extra en el Intent
                    Intent intent = new Intent(VentaAguardar.this, ConfirmVenta.class);
                    intent.putExtra("cantidad", cantidad).toString();
                    intent.putExtra("producto", nombreProducto);
                    intent.putExtra("localidad", localidad);
                    intent.putExtra("precio", precio).toString();
                    intent.putExtra("photo", photo);
                    startActivity(intent);

                    // Finalizar la actividad actual
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Error al guardar la venta
                    Toast.makeText(VentaAguardar.this, "Error al guardar la venta", Toast.LENGTH_SHORT).show();
                });
    }

    //Métodos para que la información escrita en los editText se mantenga cuando cambiemos de Activity
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(PRODUCTOS_KEY,productoEditText.getText().toString());
        editor.putString(CANTIDAD_KEY, cantidadEditText.getText().toString());
        editor.putString(PRECIO_KEY, precioEditext.getText().toString());
        editor.putString(LOCALIDAD_KEY, localidadEditext.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String productosValue = sharedPreferences.getString(PRODUCTOS_KEY, "");
        String cantidadValue = sharedPreferences.getString(CANTIDAD_KEY, "");
        String precioValue = sharedPreferences.getString(PRECIO_KEY, "");
        String localidadValue = sharedPreferences.getString(LOCALIDAD_KEY, "");

        precioEditext.setText(productosValue);
        cantidadEditText.setText(cantidadValue);
        precioEditext.setText(precioValue);
        localidadEditext.setText(localidadValue);
    }

}