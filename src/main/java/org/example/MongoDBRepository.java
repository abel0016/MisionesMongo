package org.example;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Aggregates.set;
import static javax.management.Query.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class MongoDBRepository {

    private static MongoDBRepository instance;
    private MongoClient mongoClient;
    MongoCollection<Jugador> jugadoresCollection;
    MongoCollection<Mision> misionesCollection;
    MongoCollection<Recompensa> recompensaCollection;

    private MongoDBRepository() {
        String uri = "mongodb+srv://root:root@clusterad.5kxme.mongodb.net/?retryWrites=true&w=majority&appName=ClusterAD";

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(uri))
                .codecRegistry(pojoCodecRegistry)
                .build();

        mongoClient = MongoClients.create(settings);
        MongoDatabase database=mongoClient.getDatabase(MisionFields.DATABASE_NAME);
        recompensaCollection=database.getCollection(MisionFields.RECOMPENSA_COLLECTION_NAME, Recompensa.class);
        misionesCollection=database.getCollection(MisionFields.MISIONES_COLLECTION_NAME, Mision.class);
        jugadoresCollection=database.getCollection(MisionFields.JUGADORES_COLLECTION_NAME, Jugador.class);

    }

    // Metodo para obtener el cliente
    public static MongoDBRepository getInstance() {
        if (instance == null) {
            instance = new MongoDBRepository();
        }
        return instance;
    }

    // Metodo para cerrar el cliente al apagar la aplicación
    public void closeMongoClient() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    //Metodos CRUD
    public void addJugador(Jugador j1){
        jugadoresCollection.insertOne(j1);
    }
    public void updateJugador(Jugador j1, String nombre){
        UpdateResult result=jugadoresCollection.updateOne(
                Filters.eq("_id",j1.getId()),
                Updates.set("nombre",nombre)
        );

    }
    public List<Jugador> mostrarJugadores(){
        MongoCursor<Jugador> cursor=jugadoresCollection.find().iterator();
        List<Jugador> jugadoresMostrados=new ArrayList<>();
        while(cursor.hasNext()){
            Jugador jugadorActual=cursor.next();
            jugadoresMostrados.add(jugadorActual);
        }
        return jugadoresMostrados;
    }

    public List<Mision> mostrarMisiones(){
        MongoCursor<Mision> cursor=misionesCollection.find().iterator();
        List<Mision> misionesMostradas=new ArrayList<>();
        while(cursor.hasNext()){
            Mision misionActual=cursor.next();
            misionesMostradas.add(misionActual);
        }
        return misionesMostradas;
    }
    public void addMision(Mision m1){
        misionesCollection.insertOne(m1);
    }
    public void asignarMision(Jugador j1,Mision m1){
       UpdateResult result= misionesCollection.updateOne(
            Filters.eq("_id",m1.getId()),
               Updates.addToSet("jugadores_ids",j1.getId())
        );
    }
    public void rechazarMision(Mision m1, Jugador j1){

        UpdateResult result=misionesCollection.updateOne(
                Filters.eq("_id",m1.getId()),
                Updates.pull("jugadores_ids",j1.getId())
        );
    }
    public void modificarRecompensaMision(Mision m1, Recompensa r1){
        UpdateResult result= misionesCollection.updateOne(
                Filters.eq("_id",m1.getId()),
                Updates.set("recompensa_id",r1.getId())
        );
    }
    public void eliminarMisionaRecompensa(Mision m1, Recompensa r1){
        UpdateResult result= recompensaCollection.updateOne(
                Filters.eq("_id",r1.getId()),
                Updates.pull("misiones_ids",m1.getId())
        );
    }
    public void añadirMisionARecompensa(Mision m1, Recompensa r1){
        UpdateResult result= recompensaCollection.updateOne(
                Filters.eq("_id",r1.getId()),
                Updates.addToSet("recompensa_id",m1.getId())
        );
    }
    public List<Recompensa> mostrarRecompensas() {
        List<Recompensa> listaRecompensas = new ArrayList<>();
        MongoCursor<Recompensa> cursor = recompensaCollection.find().iterator();

        while (cursor.hasNext()) {
            listaRecompensas.add(cursor.next());
        }
        return listaRecompensas;
    }
    public List<Jugador> obtenerJugadoresDeMision(Mision mision) {
        Mision m = misionesCollection.find(Filters.eq("_id", mision.getId())).first();
        List<Jugador> jugadores = new ArrayList<>();

        for (ObjectId jugadorId : m.getJugadoresIds()) {
            Jugador jugador = jugadoresCollection.find(Filters.eq("_id", jugadorId)).first();
            jugadores.add(jugador);

            }
        return jugadores;
    }
    public Recompensa obtenerRecompensaPorId(ObjectId recompensaId) {
        return recompensaCollection.find(Filters.eq("_id", recompensaId)).first();
    }
    public List<Mision> obtenerMisionesPorRecompensa(Recompensa recompensa) {
        List<Mision> misiones = new ArrayList<>();

        MongoCursor<Mision> cursor = misionesCollection.find(Filters.eq("recompensa_id", recompensa.getId())).iterator();
        while (cursor.hasNext()) {
            misiones.add(cursor.next());
        }
        return misiones;
    }

}
