package org.example;

import java.util.List;
import java.util.Scanner;

public class GestionBD {

    MongoDBRepository instance=MongoDBRepository.getInstance();
    Scanner teclado=new Scanner(System.in);

    public GestionBD(MongoDBRepository instance) {
        this.instance = instance;
    }

    //1.Añadir Jugador
    public void addJugador(){
        System.out.println("Introduce el nombre del nuevo Jugador");
        String nombre=teclado.nextLine();
        Jugador j1=new Jugador(nombre);
        instance.addJugador(j1);
    }
    //2.Actualizar Jugador
    public void actualizarJugador(){
       List<Jugador> jugadoresMostrados= instance.mostrarJugadores();
       for(int i=0;i<jugadoresMostrados.size();i++){
           Jugador j1= jugadoresMostrados.get(i);
           System.out.println(i+" "+j1.getNombre());
       }
        System.out.println("Introduce el jugador que quieres modificar");
        int op=teclado.nextInt();
        teclado.nextLine();
        Jugador cambiante=jugadoresMostrados.get(op);
        System.out.println("Dime el nombre nuevo");
        String nombreNuevo=teclado.nextLine();
        instance.updateJugador(cambiante,nombreNuevo);
    }
    //3.Insertar Mision

    public void insertarMision() {
        System.out.println("Introduce la descripción de la misión:");
        String descripcion = teclado.nextLine();
        List<Recompensa> recompensas = instance.mostrarRecompensas();
        System.out.println("Seleccione una recompensa para la misión:");
        for (int i = 0; i < recompensas.size(); i++) {
            System.out.println(i + " " + recompensas.get(i).getNombre());
        }
        int opcion;
        System.out.print("Introduce el número de la recompensa: ");
        opcion = teclado.nextInt();
        teclado.nextLine();
        Recompensa recompensaSeleccionada = recompensas.get(opcion);
        Mision nuevaMision = new Mision();
        nuevaMision.setDescripcion(descripcion);
        nuevaMision.setRecompensaId(recompensaSeleccionada.getId());
        instance.addMision(nuevaMision);
    }
    //4.Asignar Mision
    public void asignarMision() {
        List<Jugador> jugadores = instance.mostrarJugadores();
        System.out.println("Seleccione un jugador:");
        for (int i = 0; i < jugadores.size(); i++) {
            System.out.println(i + " " + jugadores.get(i).getNombre());
        }
        int opcionJugador;
        System.out.print("Introduce el número del jugador: ");
        opcionJugador = teclado.nextInt();
        teclado.nextLine();
        Jugador jugadorSeleccionado = jugadores.get(opcionJugador);

        // Mostrar todas las misiones disponibles
        List<Mision> misiones = instance.mostrarMisiones();
        System.out.println("Seleccione una misión:");
        for (int i = 0; i < misiones.size(); i++) {
            System.out.println(i + " " + misiones.get(i).getDescripcion());
        }
        int opcionMision;
        System.out.print("Introduce el número de la misión: ");
        opcionMision = teclado.nextInt();
        teclado.nextLine();
        Mision misionSeleccionada = misiones.get(opcionMision);
        instance.asignarMision(jugadorSeleccionado, misionSeleccionada);
    }
    //5.Rechazar Mision
    public void rechazarMision() {
        // Obtener lista de misiones disponibles
        List<Mision> misiones = instance.mostrarMisiones();
        System.out.println("Seleccione una misión para rechazar:");
        for (int i = 0; i < misiones.size(); i++) {
            System.out.println(i + " " + misiones.get(i).getDescripcion());
        }
        int opcionMision;

        System.out.print("Introduce el número de la misión: ");
        opcionMision = teclado.nextInt();
        teclado.nextLine();
        Mision misionSeleccionada = misiones.get(opcionMision);
        // Obtener los jugadores asignados a esta misión
        List<Jugador> jugadoresAsignados = instance.obtenerJugadoresDeMision(misionSeleccionada);
        System.out.println("Seleccione un jugador para eliminar de la misión:");
        for (int i = 0; i < jugadoresAsignados.size(); i++) {
            System.out.println(i + " " + jugadoresAsignados.get(i).getNombre());
        }
        int opcionJugador;
        System.out.print("Introduce el número del jugador: ");
        opcionJugador = teclado.nextInt();
        teclado.nextLine();
        Jugador jugadorSeleccionado = jugadoresAsignados.get(opcionJugador);
        instance.rechazarMision(misionSeleccionada, jugadorSeleccionado);
    }
    //6.Modificar Recompensa de la Mision
    public void modificarRecompensaMision() {
        List<Mision> misiones = instance.mostrarMisiones();
        System.out.println("Seleccione una misión para modificar su recompensa:");
        for (int i = 0; i < misiones.size(); i++) {
            System.out.println(i + " " + misiones.get(i).getDescripcion());
        }
        int opcionMision;
        System.out.print("Introduce el número de la misión: ");
        opcionMision = teclado.nextInt();
        teclado.nextLine();
        Mision misionSeleccionada = misiones.get(opcionMision);
        Recompensa recompensaActual = instance.obtenerRecompensaPorId(misionSeleccionada.getRecompensaId());
        List<Recompensa> recompensas = instance.mostrarRecompensas();
        System.out.println("Seleccione una nueva recompensa para la misión:");
        for (int i = 0; i < recompensas.size(); i++) {
            System.out.println(i + " " + recompensas.get(i).getNombre());
        }
        int opcionRecompensa;
        System.out.print("Introduce el número de la recompensa: ");
        opcionRecompensa = teclado.nextInt();
        teclado.nextLine(); // Consumir salto de línea
        Recompensa nuevaRecompensa = recompensas.get(opcionRecompensa);
        instance.eliminarMisionaRecompensa(misionSeleccionada, recompensaActual);
        instance.modificarRecompensaMision(misionSeleccionada, nuevaRecompensa);
        instance.añadirMisionARecompensa(misionSeleccionada, nuevaRecompensa);
    }
    //7.Consultar Misiones
    public void consultarMisiones() {
        List<Mision> misiones = instance.mostrarMisiones();
        for (Mision mision : misiones) {
            System.out.println("Descripción: " + mision.getDescripcion());
            Recompensa recompensa = instance.obtenerRecompensaPorId(mision.getRecompensaId());
            System.out.println("Recompensa: " + recompensa.getNombre());

            List<Jugador> jugadores = instance.obtenerJugadoresDeMision(mision);
            System.out.print("Jugadores asignados: ");
            for (Jugador jugador : jugadores) {
                System.out.print(jugador.getNombre() + " ");
            }
        }
    }
    //8.Consultar Recompensas
    public void consultarRecompensas() {
        List<Recompensa> recompensas = instance.mostrarRecompensas();
        for (Recompensa recompensa : recompensas) {
            System.out.println("Recompensa: " + recompensa.getNombre());
            List<Mision> misiones = instance.obtenerMisionesPorRecompensa(recompensa);
            System.out.print("Misiones donde se obtiene: ");
            for (Mision mision : misiones) {
                System.out.print(mision.getDescripcion() + " ");
            }
        }
    }
}
