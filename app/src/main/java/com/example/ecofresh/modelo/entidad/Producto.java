package com.example.ecofresh.modelo.entidad;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Producto {

    Map<String, Object> producto = new HashMap<>();
    public Producto(String nombre, double precio, String categoria, String localidad, String photoUrls) {
        producto.put("nombre", nombre);
        producto.put("precio", precio);
        producto.put("categoria", categoria);
        producto.put("localidad", localidad);
        producto.put("photoUrls", photoUrls);
    }
    /*public Producto(String nombre, double precio,  String localidad) {
        producto.put("nombre", nombre);
        producto.put("precio", precio);
        producto.put("localidad", localidad);
    }*/
    public Producto() {
    }

    public double getPrecio() {

        return (double) producto.get("precio");
    }

    public void setPrecio(double precio) {

        producto.put("precio", precio);
    }

    public String getNombre() {

        return (String)  producto.get("nombre");
    }

    public void setNombre(String nombre) {

        producto.put("nombre", nombre);
    }

    public String getLocalidad() {

        return (String) producto.get("localidad");
    }

    public void setLocalidad(String localidad) {

        producto.put("localidad", localidad);
    }

    public String getCategoria() {

        return (String) producto.get("categoria");
    }

    public void setCategoria(String categoria) {

        producto.put("categoria", categoria);
    }

    public String getPhotoUrls() {
        return (String) producto.get("photoUrls");
    }

    public void setPhotoUrls(String photoUrls) {

        producto.put("photoUrls", photoUrls);
    }

}
