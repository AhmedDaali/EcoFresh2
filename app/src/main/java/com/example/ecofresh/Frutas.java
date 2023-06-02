package com.example.ecofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

public class Frutas extends AppCompatActivity {

    ImageButton botonFlecha3;
    ImageButton botonNaranjas;
    ImageButton botonPlatanos;
    ImageButton botonMandarinas;
    ImageButton botonFresas;
    ImageButton botonManzanas;
    ImageButton botonUvas;

    AutoCompleteTextView auto;
    String[] frutas = {

            "Platanos", "Naranjas", "Fresas", "Mandarinas", "Uvas", "Manzanas"

    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frutas);

        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo

        getSupportActionBar().hide();


        // Esto se hace para que se autocomplete la entrada de texto

        auto = (AutoCompleteTextView) findViewById(R.id.txt_auto);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, frutas);
        auto.setThreshold(3); // Indicamos con cuántas letras empezará a autocompletar
        auto.setAdapter(adapter);

        // 1 Guardamos la referencia del botón de naranjas

        botonNaranjas = findViewById(R.id.boton_naranjas);

        botonNaranjas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Frutas.this,Muestrario.class);

                //Enviamos la variable a buscar al listView
                intent.putExtra("producto", "naranjas");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 2 Guardamos la referencia del botón de platanos

        botonPlatanos = findViewById(R.id.boton_platanos);

        botonPlatanos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Frutas.this,Muestrario.class);

                //Enviamos la variable a buscar al listView
                intent.putExtra("producto", "platanos");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 3 Guardamos la referencia del botón de mandarinas

        botonMandarinas = findViewById(R.id.boton_mandarinas);

        botonMandarinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Frutas.this,Muestrario.class);

                //Enviamos la variable a buscar al listView
                intent.putExtra("producto", "mandarinas");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 4 Guardamos la referencia del botón de Fresas

        botonFresas = findViewById(R.id.boton_fresas);

        botonFresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Frutas.this,Muestrario.class);

                //Enviamos la variable a buscar al listView
                intent.putExtra("producto", "fresas");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 5 Guardamos la referencia del botón de manzanas

        botonManzanas = findViewById(R.id.boton_manzanas);

        botonManzanas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Frutas.this,Muestrario.class);

                //Enviamos la variable a buscar al listView
                intent.putExtra("producto", "manzanas");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });


        // 6 Guardamos la referencia del botón de uvas

        botonUvas = findViewById(R.id.boton_uvas);

        botonUvas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Frutas.this,Muestrario.class);

                //Enviamos la variable a buscar al listView
                intent.putExtra("producto", "uvas");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });


        // 7 Guardamos la referencia del botón de Flecha

        botonFlecha3 = findViewById(R.id.boton_flechaF);

        botonFlecha3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Recogemos el valor del autoCompleteTextView
                String nombreProducto =  auto.getText().toString().trim();

                Intent intent = new Intent(Frutas.this,Muestrario.class);

                //Enviamos la variable a buscar al listView
                intent.putExtra("producto", nombreProducto.toLowerCase());

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });


    }
}