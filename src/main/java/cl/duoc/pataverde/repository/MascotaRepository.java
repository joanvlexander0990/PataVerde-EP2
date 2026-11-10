
package cl.duoc.pataverde.repository;

import cl.duoc.pataverde.model.Mascota;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MascotaRepository {

    private final MascotaDAO dao = new MascotaDAOImpl();

    private final List<Mascota> mascotas = new ArrayList<>();

    public MascotaRepository() {
        try {
            mascotas.addAll(dao.cargar());
        } catch (IOException e) {
            throw new IllegalStateException(
                    "No se pudieron cargar las mascotas", e);
        }
    }

    public List<Mascota> cargar() {
        return new ArrayList<>(mascotas);
    }

    public void guardar(List<Mascota> nuevasMascotas) throws IOException {
        dao.guardar(nuevasMascotas);
        mascotas.clear();
        mascotas.addAll(nuevasMascotas);
    }
}
