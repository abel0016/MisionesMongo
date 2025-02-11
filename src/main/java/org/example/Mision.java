package org.example;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.List;

//Entidad principal
public class Mision {
    private ObjectId id;
    private String descripcion;
    @BsonProperty(value = "recompensa_id")
    private ObjectId recompensaId;
    @BsonProperty(value = "jugadores_ids")
    private List<ObjectId> jugadoresIds;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ObjectId getRecompensaId() {
        return recompensaId;
    }

    public void setRecompensaId(ObjectId recompensaId) {
        this.recompensaId = recompensaId;
    }

    public List<ObjectId> getJugadoresIds() { return jugadoresIds; }
    public void setJugadoresIds(List<ObjectId> jugadoresIds) { this.jugadoresIds = jugadoresIds; }
}
