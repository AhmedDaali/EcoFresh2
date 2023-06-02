package com.example.ecofresh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class Splash extends AppCompatActivity implements Animation.AnimationListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        // Esta es la parte de la entrada del titulo ECO

        TextView titulo_eco = (TextView) findViewById(R.id.titulo_eco);
        Animation animTitulo_eco = AnimationUtils.loadAnimation(this, R.anim.entrada_letra_eco);
        titulo_eco.startAnimation(animTitulo_eco);


        // Esta es la parte de la entrada del titulo FRESH

        TextView titulo_fresh = (TextView) findViewById(R.id.titulo_fresh);
        Animation animTitulo_fresh = AnimationUtils.loadAnimation(this, R.anim.entrada_letra_fresh);
        titulo_fresh.startAnimation(animTitulo_fresh);


        // Esta es la parte del salto de la manzana

        ImageView apple = (ImageView) findViewById(R.id.apple);
        Animation animApple = AnimationUtils.loadAnimation(this, R.anim.salto_manzana);
        apple.startAnimation(animApple);


       // Esta es la parte de la rotación y escalado del logo

        ImageView logo = (ImageView) findViewById(R.id.logo);
        Animation animLogo = AnimationUtils.loadAnimation(this, R.anim.desplegar_logo);
        logo.startAnimation(animLogo);


       // Esta es la escucha de la animación del splash para que el método  "onAnimationEnd" actúe
       // cuando esta finalice. Debe estar aquí dentro de estas llaves. Le tendremos que pasar
       // el objeto de la clase que implementa la interface, que es él mismo, splash.
       // Si fuera de otra clase, habría que escribir dentro del paréntesis un new de instanciar esa clase
       // Pero en este caso como es la misma utilizamos el this.

       animApple.setAnimationListener(this);


        // Con esta linea ocultamos el actionBar, la barra de acción situada arriba de todo

        getSupportActionBar().hide();


    }

    // ---------------------------------------------------------------------------------------------


    // Estos son los métodos que implementa la interface, de los cuales solo necesitamos uno de ellos
    // para que cambie de activity sin tocar en la pantalla. El método "onAnimationEnd".
    // Como empiezan por "on" quiere decir que son eventos que se producen en un determinado momento.



    @Override
    public void onAnimationStart(Animation animation) {

    }




    // Este es el método para que cambie de activity sin tocar en la pantalla. El método "onAnimationEnd"
    // Actua cuando termine la animación. Como es un evento, debemos poner a la escucha a la animación
    // que finalizará para que actúe el evento, o método siguiente.
    // Para cambiar de activity, necesitamos la clase Intent y crear un objeto y tenemos que indicar
    // de qué activity a qué activity pasará.
    // Con el método "startActivity" se le pasa el intent. Con "finish();" indicamos que no se pueda
    // regresar desde la activity_login al activity_splash.

    @Override
    public void onAnimationEnd(Animation animation) {

        // Con esto atrasamos el paso a la siguiente activity. Nos dice que debe ir dentro de un try catch

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Para cambiar de activity, necesitamos la clase Intent y crear un objeto y tenemos que indicar
        // de qué activity a qué activity pasará.

        Intent intent = new Intent(Splash.this, Inicial.class);

        startActivity(intent);

        // regresar desde la activity_main al activity_splash.

        finish();

    }



    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}