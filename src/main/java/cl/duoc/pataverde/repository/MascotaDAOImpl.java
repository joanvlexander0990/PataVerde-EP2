
package cl.duoc.pataverde.repository;

import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Mascota;
import cl.duoc.pataverde.model.Perro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAOImpl implements MascotaDAO {

    private final Path archivo = Path.of("mascotas.json");

    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    @Override
    public void guardar(List<Mascota> mascotas) throws IOException {
        JsonArray lista = new JsonArray();

        for (Mascota mascota : mascotas) {
            JsonObject objeto = gson.toJsonTree(mascota).getAsJsonObject();
            objeto.addProperty("tipo", mascota.getTipo());
            lista.add(objeto);
        }

        Files.writeString(archivo, gson.toJson(lista));
    }

    @Override
    public List<Mascota> cargar() throws IOException {
        List<Mascota> mascotas = new ArrayList<>();

        if (!Files.exists(archivo)) {
            return mascotas;
        }

        String contenido = Files.readString(archivo);

        if (contenido.isBlank()) {
            return mascotas;
        }

        JsonArray lista = JsonParser.parseString(contenido).getAsJsonArray();

        for (var elemento : lista) {
            JsonObject objeto = elemento.getAsJsonObject();
            String tipo = objeto.get("tipo").getAsString();

            if (tipo.equals("Perro")) {
                mascotas.add(gson.fromJson(objeto, Perro.class));
            } else if (tipo.equals("Gato")) {
                mascotas.add(gson.fromJson(objeto, Gato.class));
            }
        }

        return mascotas;
    }
}
