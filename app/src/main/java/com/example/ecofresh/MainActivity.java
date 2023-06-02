package com.example.ecofresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    // Creamos las variables donde vamos a guardar las referencias

    Button botonComprar;

    Button botonVender;

    Button botonCuenta;

    Button botonSalir;

    FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo
       //FirebaseUser user = mAuth.getCurrentUser();

        // [START config_signin] Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        getSupportActionBar().hide();


        // 1
        // Aquí daremos la referencia del botón "botonComprar", mediante el identificador que está en la activity_main
        // se llama: "comprar". Por tanto buscamos con findViewById ese identificador en la clase R, con id "comprar"

        botonComprar = findViewById(R.id.comprar);


        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.¿?

        botonComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // De momento queremos que al hacer click en el botón pasemos a la siguiente activity_selection.
                // Para ello debemos crear un objeto de la clase Intent. Introduciendo en el paréntesis, que pase de esta activity (this) a la activity_selection (Selection.class)

                Intent intent = new Intent (MainActivity.this,Selection.class);

                // Arrancamos el evento que acabamos de crear

                startActivity(intent);

            }
        });


        // 2
        // Aquí daremos la referencia del botón "botonVender", mediante el identificador que está en la activity_main
        // se llama: "bvender". Por tanto buscamos con findViewById ese identificador en la clase R, con id "bvender"

        botonVender = findViewById(R.id.vender);


        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.¿?

        botonVender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // De momento queremos que al hacer click en el botón pasemos a la siguiente activity_venta.
                // Para ello debemos crear un objeto de la clase Intent. Introduciendo en el paréntesis, que pase de esta activity (this) a la activity_venta (Venta.class)

                Intent intent = new Intent (MainActivity.this, VentaAguardar.class);

                // Arrancamos el evento que acabamos de crear

                startActivity(intent);

            }
        });



        // 3
        // Aquí daremos la referencia del botón "botonCuenta", mediante el identificador que está en la activity_main
        // se llama: "MiCuenta". Por tanto buscamos con findViewById ese identificador en la clase R, con id "MiCuenta"

        botonCuenta = findViewById(R.id.MiCuenta);


        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.¿?

        botonCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // De momento queremos que al hacer click en el botón pasemos a la siguiente activity_cuenta_usuario.
                // Para ello debemos crear un objeto de la clase Intent. Introduciendo en el paréntesis, que pase de esta activity (this) a la activity_cuenta_usuario (CuentaUsuario.class)

                Intent intent = new Intent (MainActivity.this,CuentaUsuario.class);

                // Arrancamos el evento que acabamos de crear

                startActivity(intent);

            }
        });

        // 4
        // Aquí daremos la referencia del botón "botonSalir", mediante el identificador que está en la activity_main
        // se llama: "salir". Por tanto buscamos con findViewById ese identificador en la clase R, con id "salir"

        botonSalir = findViewById(R.id.salir);

        // Ahora debemos ponerlo a la escucha, para saber cuándo se clica sobre él, con el método setOnClickListener()
        // Dentro del paréntesis que está vacío, debemos crear un nuevo objeto: new View.OnClickListener()
        // Con la interface View.OnClickListener, sobreescribimos el método public void onClick.
        // Al tener muchos botones, es mejor sacarlo del método onCreate.

        botonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut(); //Cierra la sesión si entras con email y contraseña.
                logOutGoogle(); //Cierra la sesión si entras con Google.

                Intent intent = new Intent (MainActivity.this,Inicial.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void logOutGoogle(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
}