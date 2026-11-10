package cl.duoc.pataverde.repository;

import cl.duoc.pataverde.model.Mascota;
import java.io.IOException;
import java.util.List;

public interface MascotaDAO {

    List<Mascota> cargar() throws IOException;

    void guardar(List<Mascota> mascotas) throws IOException;
}