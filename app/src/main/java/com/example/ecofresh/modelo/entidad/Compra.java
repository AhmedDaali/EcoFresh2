package com.example.ecofresh.modelo.entidad;

import java.util.HashMap;
import java.util.Map;

public class Compra {


    Map<String, Object> compra = new HashMap<>();

    public Compra(double cantidad, double total, Producto producto, String comprador, String vendedor,
                  DireccionEnvio direccionEnvio, String emailComprador, String emailVendedor ) {
        compra.put("cantidad", cantidad);
        compra.put("producto", producto);
        compra.put("comprador", comprador);
        compra.put("vendedor", vendedor);
        compra.put("total", total);
        compra.put("direccion de envio", direccionEnvio);
        compra.put("Email del comprador", emailComprador);
        compra.put("Email del vendedor", emailVendedor);
    }
    public Compra() {
        // Constructor sin argumentos requerido por Firebase Firestore
    }

    public double getCantidad() {
        return (double) compra.get("cantidad");
    }

    public void setCantidad(double cantidad) {

        compra.put("cantidad", cantidad);
    }

    public Producto getProducto() {

        return (Producto)  compra.get("producto");
    }

    public void setProducto(Producto producto) {
        compra.put("producto", producto);
    }

    public String getComprador() {
        return (String) compra.get("comprador");
    }

    public void setComprador(String comprador) {

        compra.put("comprador", comprador);
    }
    public String getVendedor() {
        return (String) compra.get("vendedor");
    }

    public void setVendedor(String vendedor) {

        compra.put("vendedor", vendedor);
    }

    public double getTotal() {
        return (double) compra.get("total");
    }

    public void setTotal(double total) {

        compra.put("total", total);
    }
    public void setDireccionEnvio(DireccionEnvio direccionEnvio) {
        compra.put("direccion de envio", direccionEnvio);
    }

    public DireccionEnvio getDireccionEnvio() {
        return (DireccionEnvio) compra.get("direccion de envio");
    }

    public void setEmailComprador(String emailComprador) {
        compra.put("Email del comprador", emailComprador);
    }

    public String getEmailComprador() {
        return (String) compra.get("Email del comprador");
    }

    public void setEmailVendedor(String emailVendedor) {
        compra.put("Email del vendedor", emailVendedor);
    }

    public  String getEmailVendedor(){
        return (String) compra.get("Email del vendedor");
    }

}