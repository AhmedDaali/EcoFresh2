package com.example.ecofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;

public class Hortalizas extends AppCompatActivity {


    private ImageButton botonFlecha2;
    private ImageButton botonZanahorias;
    private ImageButton botonPatatas;
    private ImageButton botonTomates;
    private ImageButton botonAjos;
    private ImageButton botonCebollas;
    private ImageButton botonPimientos;

    //String nombreProducto;

    private AutoCompleteTextView auto;
    private String[] hortalizas = {

            "Zanahorias", "Patatas", "Tomates", "Ajos", "Cebollas", "Pimientos", "Puerros"

    };
    private String nombreProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hortalizas);

        //String nombreProducto =  auto.getText().toString().trim();


        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo

        getSupportActionBar().hide();


        // Esto se hace para que se autocomplete la entrada de texto

        auto = (AutoCompleteTextView) findViewById(R.id.txt_auto);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, hortalizas);
        auto.setThreshold(3); // Indicamos con cuántas letras empezará a autocompletar
        auto.setAdapter(adapter);

        String nombreProducto =  auto.getText().toString().trim();


        // 1 Guardamos la referencia del botón de zanahorias

        botonZanahorias = findViewById(R.id.boton_zanahorias);

        botonZanahorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Hortalizas.this,Muestrario.class);

                intent.putExtra("producto", "zanahorias");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 2 Guardamos la referencia del botón de patatas

        botonPatatas = findViewById(R.id.boton_patatas);

        botonPatatas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Hortalizas.this,Muestrario.class);

                intent.putExtra("producto", "patatas");
                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 3 Guardamos la referencia del botón de tomates

        botonTomates = findViewById(R.id.boton_tomates);

        botonTomates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Hortalizas.this,Muestrario.class);

                intent.putExtra("producto", "tomates");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 4 Guardamos la referencia del botón de ajos

        botonAjos = findViewById(R.id.boton_ajos);

        botonAjos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Hortalizas.this,Muestrario.class);

                intent.putExtra("producto", "ajos");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 5 Guardamos la referencia del botón de cebollas

        botonCebollas = findViewById(R.id.boton_cebollas);

        botonCebollas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Hortalizas.this,Muestrario.class);

                intent.putExtra("producto", "cebollas");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });


        // 6 Guardamos la referencia del botón de pimientos

        botonPimientos = findViewById(R.id.boton_pimientos);

        botonPimientos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Hortalizas.this,Muestrario.class);

                intent.putExtra("producto", "pimientos");

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });


        // 7 Guardamos la referencia del botón de la flecha del autocompletado

        botonFlecha2 = findViewById(R.id.boton_flechaH);

        botonFlecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //String nombreProducto =  auto.getText().toString().trim();


                Intent intent = new Intent(Hortalizas.this,Muestrario.class);

                intent.putExtra("producto", nombreProducto.toLowerCase());

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });



    }
}