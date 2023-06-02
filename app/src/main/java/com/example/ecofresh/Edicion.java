package com.example.ecofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecofresh.modelo.entidad.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Edicion extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextApellidos;
    private EditText editTextEmail;
    private EditText editTextTelefono;
    private EditText editTextDireccion;
    private EditText editTextCiudad;
    private Button botonGuardar;

    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicion);

        // Ocultar ActionBar
        getSupportActionBar().hide();

        // Inicializar Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Obtener usuario actual
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Obtener referencias de los EditText
        editTextNombre = findViewById(R.id.textName);
        editTextApellidos = findViewById(R.id.textApellido);
        editTextEmail = findViewById(R.id.textCorreo);
        editTextTelefono = findViewById(R.id.textTelefono);
        editTextDireccion = findViewById(R.id.textCalle);
        editTextCiudad = findViewById(R.id.textCiudad);

        // Obtener el email del usuario actual
        if (currentUser != null) {
            String email = currentUser.getEmail();
            if (email != null) {
                editTextEmail.setText(email);
                editTextEmail.setEnabled(false); // Deshabilitar la edición del email
            }
        }

        // Obtener referencia al documento del usuario en Firestore
        DocumentReference usuarioRef = db.collection("usuarios").document(currentUser.getEmail());

        // Obtener datos actuales del usuario
        usuarioRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                Usuario usuario = documentSnapshot.toObject(Usuario.class);
                if (usuario != null) {
                    editTextNombre.setText(usuario.getNombre());
                    editTextApellidos.setText(usuario.getApellidos());
                    editTextTelefono.setText(usuario.getTelefono());
                    editTextDireccion.setText(usuario.getDireccion());
                    editTextCiudad.setText(usuario.getLocalidad());
                }
            }
        }).addOnFailureListener(e -> {
            // Manejar error al obtener datos del usuario
            Toast.makeText(Edicion.this, "Error al obtener los datos del usuario", Toast.LENGTH_SHORT).show();
        });

        // Configurar el botón "Guardar"
        botonGuardar = findViewById(R.id.btnGuarda);
        botonGuardar.setOnClickListener(view -> {
            // Obtener los nuevos datos del usuario desde los EditText
            String nombre = editTextNombre.getText().toString().trim();
            String apellidos = editTextApellidos.getText().toString().trim();
            String telefono = editTextTelefono.getText().toString().trim();
            String direccion = editTextDireccion.getText().toString().trim();
            String localidad = editTextCiudad.getText().toString().trim();

            // Crear un nuevo objeto Usuario con los datos actualizados
            Usuario usuarioActualizado = new Usuario(nombre, apellidos, currentUser.getEmail(), direccion, localidad, telefono);

            // Actualizar los datos del usuario en Firestore
            usuarioRef.set(usuarioActualizado)
                    .addOnSuccessListener(aVoid -> {
                        // Datos actualizados correctamente
                        Toast.makeText(Edicion.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();

                        // Iniciar la actividad CuentaUsuario
                        Intent intent = new Intent(Edicion.this, CuentaUsuario.class);
                        startActivity(intent);

                        // Finalizar la actividad actual
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        // Error al actualizar los datos del usuario
                        Toast.makeText(Edicion.this, "Error al actualizar los datos del usuario", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
