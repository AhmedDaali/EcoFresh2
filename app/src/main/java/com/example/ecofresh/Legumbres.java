package com.example.ecofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

public class Legumbres extends AppCompatActivity {

    private ImageButton botonFlecha;
    private ImageButton botonHabas;
    private ImageButton botonGarbanzos;
    private ImageButton botonLentejas;
    private ImageButton botonGuisantes;
    private ImageButton botonAlfalfa;
    private ImageButton botonLupino;

    private AutoCompleteTextView auto;
    private String[] legumbres = {

            "Habas", "Garbanzos", "Lentejas", "Guisantes", "Alfalfa", "Lupino"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legumbres);


        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo

        getSupportActionBar().hide();


        // Esto se hace para que se autocomplete la entrada de texto

        auto = (AutoCompleteTextView) findViewById(R.id.txt_auto);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, legumbres);
        auto.setThreshold(3); // Indicamos con cuántas letras empezará a autocompletar
        auto.setAdapter(adapter);

        // 1 Guardamos la referencia del botón de habas

        botonHabas = findViewById(R.id.boton_habas);

        botonHabas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreProducto = auto.getText().toString().trim();

                Intent intent = new Intent(Legumbres.this,Muestrario.class);

                intent.putExtra("producto", "habas");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 2 Guardamos la referencia del botón de garbanzos

        botonGarbanzos = findViewById(R.id.boton_garbanzos);

        botonGarbanzos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Legumbres.this,Muestrario.class);

                intent.putExtra("producto", "garbanzos");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 3 Guardamos la referencia del botón de lentejas

        botonLentejas = findViewById(R.id.boton_lentejas);

        botonLentejas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Legumbres.this,Muestrario.class);

                intent.putExtra("producto", "lentejas");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 4 Guardamos la referencia del botón de guisantes

        botonGuisantes = findViewById(R.id.boton_guisantes);

        botonGuisantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Legumbres.this,Muestrario.class);

                intent.putExtra("producto", "guisantes");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 5 Guardamos la referencia del botón de alfalfa

        botonAlfalfa = findViewById(R.id.boton_alfalfa);

        botonAlfalfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Legumbres.this,Muestrario.class);

                intent.putExtra("producto", "alfalfa");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });


        // 6 Guardamos la referencia del botón de lupino

        botonLupino = findViewById(R.id.boton_lupino);

        botonLupino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Legumbres.this,Muestrario.class);

                intent.putExtra("producto", "lupino");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 7 Guardamos la referencia del botón de next

        botonFlecha = findViewById(R.id.boton_flechaL);

        botonFlecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreProducto =  auto.getText().toString().trim();

                Intent intent = new Intent(Legumbres.this,Muestrario.class);

                //Enviamos la variable a buscar al listView
                intent.putExtra("producto", nombreProducto.toLowerCase());

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });







    }
}