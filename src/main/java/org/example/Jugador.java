package org.example;

import org.bson.types.ObjectId;

public class Jugador {
    private ObjectId id;
    private String nombre;

    public Jugador(String nombre) {
        this.nombre = nombre;
    }

    public Jugador(){

    }
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
