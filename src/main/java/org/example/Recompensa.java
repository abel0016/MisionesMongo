package org.example;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Recompensa {
    private ObjectId id;
    private String nombre;
    private String descripcion;
    @BsonProperty(value = "misiones_ids")
    private int[] misionesId;

    public Recompensa(String nombre, String descripcion, int[] misionesId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.misionesId = misionesId;
    }
    public Recompensa(){

    }
    public Recompensa(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int[] getMisionesId() {
        return misionesId;
    }

    public void setMisionesId(int[] misionesId) {
        this.misionesId = misionesId;
    }
}
