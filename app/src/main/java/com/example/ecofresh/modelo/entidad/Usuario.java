package com.example.ecofresh.modelo.entidad;

import com.example.ecofresh.VentaAguardar;

import java.util.HashMap;
import java.util.Map;

public class Usuario {

    private Map<String, Object> usuario = new HashMap<>();

    public Usuario(String nombre, String apellidos, String email, String direccion,
                   String localidad, String telefono) {

        usuario.put("nombre", nombre);
        usuario.put("apellidos", apellidos);
        usuario.put("email", email);
        usuario.put("direccion", direccion);
        usuario.put("localidad", localidad);
        usuario.put("telefono", telefono);
    }

    public Usuario() {

    }

    public Usuario(String nombre, String apellidos, String email) {
        usuario.put("nombre", nombre);
        usuario.put("apellidos", apellidos);
        usuario.put("email", email);
    }

    public void setUsuario(HashMap<String, Object> usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {

        return (String) usuario.get("nombre");
    }

    public void setNombre(String nombre) {

        usuario.put("nombre", nombre);
    }

    public String getApellidos() {

        return (String) usuario.get("apellidos");
    }

    public void setApellidos(String apellidos) {

        usuario.put("apellidos", apellidos);
    }

    public String getEmail() {
        return (String) usuario.get("email");
    }

    public void setEmail(String email) {
        usuario.put("email", email);
    }

    public String getDireccion() {
        return (String) usuario.get("direccion");
    }

    public void setDireccion(String direccion) {
        usuario.put("direccion", direccion);
    }

    public String getLocalidad() {
        return (String) usuario.get("localidad");
    }

    public void setLocalidad(String localidad) {
        usuario.put("localidad", localidad);
    }

    public String getTelefono() {
        return (String) usuario.get("telefono");
    }

    public void setTelefono(String telefono) {
        usuario.put("telefono", telefono);
    }
}
