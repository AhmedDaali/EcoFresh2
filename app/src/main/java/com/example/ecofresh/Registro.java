package com.example.ecofresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecofresh.modelo.entidad.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.regex.Pattern;



public class Registro extends AppCompatActivity {

    // Esta parte de código será declarada para el botón de entrar en la activity_main
    // Obtendremos el botón y lo dejaremos guardado en una variable que será un atributo de la clase
    // Es de tipo Button
    Button botonEnter;

    // texto de las condiciones como botón
    TextView condiciones;
    // texto de la política como botón
    TextView politica;
    CheckBox checkBoxCondiciones;
    EditText nombre, apellidos, email, password;


    private static final String NAME_KEY = "name";
    private static final String LAST_NAME_KEY = "last_name";
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    FirebaseAuth mAuth;
    Boolean checkBoxState = false;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba  de
        getSupportActionBar().hide();

        // Inicializar Firebase Firestore
        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();


        // Obtener usuario actual
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Obtener referencias de los EditText
        nombre = findViewById(R.id.cajaNombre);
        apellidos = findViewById(R.id.cajaApellidos);
        email = findViewById(R.id.cajaEmail);
        password = findViewById(R.id.cajaContraseña);
        checkBoxCondiciones = findViewById(R.id.checkBox);
        checkBoxCondiciones.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxState = isChecked;
            }
        });

        // 1
        // Aquí daremos la referencia del botón "botonEnter", mediante el identificador que está en la activity_registro
        // se llama: "Enter". Por tanto buscamos con findViewById ese identificador en la clase R, con id "Enter"
        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.
        botonEnter = findViewById(R.id.Enter);
        botonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //CREAMOS USUARIO EN FIREBASE CON Email y Contraseña.
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();;
                String name = nombre.getText().toString();
                String secondName = apellidos.getText().toString();
                // Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");

                if(name.isEmpty()){
                    //Si el campo del email está vacio, devuelve error.
                    nombre.setError("Campo vacío.");
                }else if(name.length() < 2){
                    //Si el campo del email está vacio, devuelve error.
                    nombre.setError("El nombre debe tener al menos 2 caracteres.");
                }else if(secondName.length() < 2){
                    apellidos.setError("El apellido debe tener al menos 2 caracteres.");
                }else if (secondName.isEmpty()) {
                    apellidos.setError("Campo vacío.");
                }else if (emailUser.isEmpty()) {
                    email.setError("Campo vacío.");
                }else if (!validarEmail(emailUser)){
                    email.setError("Por favor ingrese una dirección de correo electrónico válida.");
                }else if (passUser.length() < 6) {
                    password.setError("La contraseña debe tener al menos 6 caracteres.");
                }else if (!checkBoxState){
                    Toast.makeText(Registro.this, "¡Por favor, acepta los términos de condiciones y uso!", Toast.LENGTH_SHORT).show();

                }else {
                    mAuth.fetchSignInMethodsForEmail(emailUser).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            if (task.isSuccessful()) {
                                SignInMethodQueryResult result = task.getResult();
                                boolean isEmailRegistered = result.getSignInMethods().size() > 0;
                                if (isEmailRegistered) {
                                    // El correo electrónico ya está registrado
                                    Toast.makeText(Registro.this, "El correo electrónico ya está registrado", Toast.LENGTH_SHORT).show();
                                } else {
                                    //CREAMOS USUARIO EN FIREBASE CON Email y Contraseña.
                                    mAuth.createUserWithEmailAndPassword(emailUser, passUser)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {

                                                    if (task.isSuccessful()) {
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Toast.makeText(Registro.this, "¡Bienvenido " + name + "!", Toast.LENGTH_LONG).show();
                                                        //Pasamos a la pagina principal al pulsar en añadir cuenta.
                                                        Intent intent = new Intent(Registro.this, MainActivity.class);
                                                        startActivity(intent);

                                                    } else {
                                                        // If sign in fails, display a message to the user.
                                                        Toast.makeText(Registro.this, "Authentication failed.",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                    // Comprobar si la colección "usuarios" existe, y crearla si no existe
                                    db.collection("usuarios").document("dummy").get().addOnFailureListener(e -> {
                                        if (e.getMessage() != null && e.getMessage().contains("No document exists")) {
                                            // La colección "usuarios" no existe, se crea el documento dummy
                                            db.collection("usuarios").document("dummy").set(new Usuario()).addOnSuccessListener(aVoid -> {
                                                // El documento dummy se creó correctamente
                                                Toast.makeText(Registro.this, "Se creó la colección 'usuarios'", Toast.LENGTH_SHORT).show();
                                            }).addOnFailureListener(error -> {
                                                // Error al crear el documento dummy
                                                Toast.makeText(Registro.this, "Error al crear la colección 'usuarios'", Toast.LENGTH_SHORT).show();
                                            });
                                        } else {
                                            // Otro error al acceder a la colección "usuarios"
                                            Toast.makeText(Registro.this, "Error al acceder a la colección 'usuarios'", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    // Crear un nuevo objeto Usuario con los datos actualizados
                                    Usuario usuarioCreado = new Usuario(name, secondName, emailUser);

                                    // Actualizar los datos del usuario en Firestore
                                    db.collection("usuarios").document(emailUser).set(usuarioCreado)
                                            .addOnSuccessListener(aVoid -> {
                                                // Datos actualizados correctamente
                                                Toast.makeText(Registro.this, "Datos creados correctamente", Toast.LENGTH_SHORT).show();

                                                // Iniciar la actividad CuentaUsuario
                                                Intent intent = new Intent(Registro.this, MainActivity.class);
                                                startActivity(intent);

                                                // Finalizar la actividad actual
                                                finish();
                                            })
                                            .addOnFailureListener(e -> {
                                                // Error al actualizar los datos del usuario
                                                Toast.makeText(Registro.this, "Error al crear los datos del usuario", Toast.LENGTH_SHORT).show();
                                            });
                                }
                            }
                        }
                    });

                }
            }
        });


        // 2
        // Aquí daremos la referencia del texto "condiciones", mediante el identificador que está en la activity_registro
        // se llama: "checkBox2". Por tanto buscamos con findViewById ese identificador en la clase R, con id "checkBox2"

        condiciones = findViewById(R.id.checkBox2);


        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.

        condiciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // De momento queremos que al hacer click en el texto pasamos a la activity_condiciones.
                // Para ello debemos crear un objeto de la clase Intent. Introduciendo en el paréntesis, que pase de esta activity (this) a la activity_condiciones (Condiciones.class)

                Intent intent = new Intent (Registro.this,Condiciones.class);

                // Arrancamos el evento que acabamos de crear

                startActivity(intent);

            }
        });


        // 3
        // Aquí daremos la referencia del texto "politica", mediante el identificador que está en la activity_registro
        // se llama: "checkBox4". Por tanto buscamos con findViewById ese identificador en la clase R, con id "checkBox4"

        politica = findViewById(R.id.checkBox4);


        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.

        politica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // De momento queremos que al hacer click en el botón pasemos a la siguiente activity_condiciones.
                // Para ello debemos crear un objeto de la clase Intent. Introduciendo en el paréntesis, que pase de esta activity (this) a la activity_condiciones (Condiciones.class)

                Intent intent = new Intent (Registro.this,Condiciones.class);

                // Arrancamos el evento que acabamos de crear

                startActivity(intent);

            }
        });



    }

    //Función para validar el formato del email.
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    //Métodos para que la información escrita en los editText se mantenga cuando cambiemos de Activity
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NAME_KEY, nombre.getText().toString());
        editor.putString(LAST_NAME_KEY, apellidos.getText().toString());
        editor.putString(EMAIL_KEY, email.getText().toString());
        editor.putString(PASSWORD_KEY, password.getText().toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String nombreValue = sharedPreferences.getString(NAME_KEY, "");
        String apellidosValue = sharedPreferences.getString(LAST_NAME_KEY, "");
        String emailValue = sharedPreferences.getString(EMAIL_KEY, "");
        String passwordValue = sharedPreferences.getString(PASSWORD_KEY, "");

        nombre.setText(nombreValue);
        apellidos.setText(apellidosValue);
        email.setText(emailValue);
        password.setText(passwordValue);
    }
}