package com.example.ecofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmCompra extends AppCompatActivity {

    private Button botonContinuarCompra;
    private Button botonExit;

    private String comprador, vendedor, nombreProducto, localidadEnvio, calle, cp;
    private double total, cantidad, precio;


    private TextView  compradorTextView, vendedorTextView, totalTextView, cantidadTextView,
            nombreProductoTextView, localidadEnvioTextView, calleTextView, precioTextView, cpTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_compra);


        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo

        getSupportActionBar().hide();


        // 1 Boton para regresar a la activity_selection

        botonContinuarCompra = findViewById(R.id.btnContinuarComprando);

        // Referencias a los elementos de la interfaz
        compradorTextView = findViewById(R.id.textComprador);
        vendedorTextView = findViewById(R.id.textVendedor2);
        totalTextView = findViewById(R.id.textTotal);
        cantidadTextView = findViewById(R.id.textCantidad2);
        nombreProductoTextView = findViewById(R.id.textProducto2);
        localidadEnvioTextView = findViewById(R.id.textLocalidad2);
        calleTextView = findViewById(R.id.textCalle2);
        precioTextView = findViewById(R.id.textPrecio2);
        cpTextView = findViewById(R.id.textCp2);

        // Obtener los datos de la compra del Intent.
        comprador = getIntent().getStringExtra("comprador");
        vendedor = getIntent().getStringExtra("vendedor");
        total = getIntent().getDoubleExtra("total",0.0d);
        cantidad = getIntent().getDoubleExtra("cantidad",0.0d);

        nombreProducto= getIntent().getStringExtra("producto");
        localidadEnvio = getIntent().getStringExtra("localidad");
        calle = getIntent().getStringExtra("calle");
        cp = getIntent().getStringExtra("cp");
        precio = getIntent().getDoubleExtra("precio",0.0d);

        //Colocar los datos de la venta en los textView
        compradorTextView.setText("Comprador:  " + comprador);
        vendedorTextView.setText("Vendedor:     " + vendedor);
        totalTextView.setText("Total:             " + total+"€");
        cantidadTextView.setText("Cantidad:      " + cantidad+"Kg");
        nombreProductoTextView.setText("Producto:     " + nombreProducto);
        localidadEnvioTextView.setText("Localidad:    " + localidadEnvio);
        calleTextView.setText("calle:             " + calle);
        precioTextView.setText("Precio:          " + precio +" €/kg" );
        cpTextView.setText("CP:                 " + cp);


        botonContinuarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ConfirmCompra.this,Selection.class);

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });

        // 2 Boton para salir

        botonExit = findViewById(R.id.exit);

        botonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ConfirmCompra.this,Inicial.class);

                // Arrancamos el evento que acabamos de crear
                startActivity(intent);

            }
        });





    }
}