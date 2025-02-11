package org.example;

public class Main {
    public static void main(String[] args) {
        MongoDBRepository instance=MongoDBRepository.getInstance();
        GestionBD juego=new GestionBD(instance);
        //juego.addJugador();
        //juego.actualizarJugador();
        //juego.insertarMision(); falla porque la lista de recompensas está vacia
        //juego.asignarMision(); falla porque no hay misiones la lista está vacia
        //juego.modificarRecompensaMision();
        //juego.consultarMisiones();
        //juego.consultarRecompensas();
    }
}