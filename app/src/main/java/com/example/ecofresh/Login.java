package com.example.ecofresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {


    // Esta parte de código será declarada para el botón de entrar después de introducir tus datos y comprobarlos
    // Obtendremos el botón y lo dejaremos guardado en una variable que será un atributo de la clase
    // Es de tipo Button
    Button botonEntrar;
    EditText emailText, passwordText;

    TextView olvidasteContraseña;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
        getSupportActionBar().hide();

        // 1
        // Aquí daremos la referencia del botón "botonEntrar", mediante el identificador que está en la activity_Login
        // se llama: "botonEntrar". Por tanto buscamos con findViewById ese identificador en la clase R, con id "botonEntrar"

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        botonEntrar = findViewById(R.id.botonEnter);
        emailText = findViewById(R.id.cajaEmail);
        passwordText = findViewById(R.id.cajaContraseña);
        olvidasteContraseña = findViewById(R.id.textView);

        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.¿?

        botonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CREAR USUARIO EN FIREBASE
                String email = emailText.getText().toString();
                String password = passwordText.getText().toString();

                if(email.isEmpty()){
                    //Si el campo del email está vacio, devuelve error.
                    emailText.setError("Campo vacío.");
                    //Si el campo de la contraseña está vacio, devuelve error.
                } else if (password.isEmpty()) {
                    passwordText.setError("Campo vacío.");

                    //La contraseña tiene que tener como minimo 6 caracteres.
                } else if (password.length() < 6) {
                    passwordText.setError("La contraseña debe tener al menos 6 caracteres.");

                }else{
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Toast.makeText(Login.this, "¡Bienvenido!", Toast.LENGTH_LONG).show();
                                            //Pasamos a la pagina principal al pulsar en añadir cuenta.
                                            Intent intent = new Intent(Login.this, MainActivity.class);
                                            startActivity(intent);

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(Login.this, "Usuario no registrado", Toast.LENGTH_SHORT).show();
                                            //Pasamos a la pagina principal al pulsar en añadir cuenta.
                                        }
                                }
                            });
                }
            }

        });

        olvidasteContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgotPassword.class);
                startActivity(intent);
                finish();
            }
        });

    }
}