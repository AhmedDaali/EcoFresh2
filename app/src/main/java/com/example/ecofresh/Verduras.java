package com.example.ecofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

public class Verduras extends AppCompatActivity {

    ImageButton botonFlecha4;
    ImageButton botonEspinacas;
    ImageButton botonLechuga;
    ImageButton botonRepollo;
    ImageButton botonBrocoli;
    ImageButton botonApio;
    ImageButton botonGrelos;

    AutoCompleteTextView auto;
    String[] verduras = {

            "Espinacas", "Lechuga", "Repollo", "Brocoli", "Apio", "Grelos"

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verduras);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo

        getSupportActionBar().hide();


        // Esto se hace para que se autocomplete la entrada de texto

        auto = (AutoCompleteTextView) findViewById(R.id.txt_auto);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, verduras);
        auto.setThreshold(3); // Indicamos con cuántas letras empezará a autocompletar
        auto.setAdapter(adapter);


        // 1 Guardamos la referencia del botón de espinacas

        botonEspinacas = findViewById(R.id.boton_espinacas);

        botonEspinacas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Verduras.this,Muestrario.class);

                intent.putExtra("producto", "espinacas");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 2 Guardamos la referencia del botón de lechuga

        botonLechuga = findViewById(R.id.boton_lechuga);

        botonLechuga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Verduras.this,Muestrario.class);

                intent.putExtra("producto", "lechuga");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 3 Guardamos la referencia del botón de repollo

        botonRepollo = findViewById(R.id.boton_repollo);

        botonRepollo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Verduras.this,Muestrario.class);

                intent.putExtra("producto", "repollo");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 4 Guardamos la referencia del botón de brocoli

        botonBrocoli = findViewById(R.id.boton_brocoli);

        botonBrocoli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Verduras.this,Muestrario.class);

                intent.putExtra("producto", "brocoli");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 5 Guardamos la referencia del botón de apio

        botonApio = findViewById(R.id.boton_apio);

        botonApio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Verduras.this,Muestrario.class);

                intent.putExtra("producto", "apio");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });


        // 6 Guardamos la referencia del botón de grelos

        botonGrelos = findViewById(R.id.boton_grelos);

        botonGrelos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Verduras.this,Muestrario.class);

                intent.putExtra("producto", "grelos");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 7 Guardamos la referencia del botón de flecha

        botonFlecha4 = findViewById(R.id.boton_flechaV);

        botonFlecha4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreProducto =  auto.getText().toString().trim();

                Intent intent = new Intent(Verduras.this,Muestrario.class);

                intent.putExtra("producto", nombreProducto.toLowerCase());

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });



    }
}