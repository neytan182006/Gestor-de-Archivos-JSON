import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Lee y escribe una lista de Producto en un archivo .json en disco,
 * con un parser/serializador JSON hecho a mano (sin librerias externas),
 * para el curso de Programacion Cliente-Servidor.
 */
public class GestorArchivoJson {

    private static final Pattern PATRON_PRODUCTO = Pattern.compile(
            "\"id\"\\s*:\\s*(\\d+)\\s*,\\s*\"nombre\"\\s*:\\s*\"([^\"]*)\"\\s*,\\s*\"precio\"\\s*:\\s*([0-9.]+)");

    private final Path archivo;

    public GestorArchivoJson(String rutaArchivo) {
        this.archivo = Path.of(rutaArchivo);
    }

    public void guardarTodos(List<Producto> productos) throws IOException {
        StringBuilder sb = new StringBuilder("[\n");
        for (int i = 0; i < productos.size(); i++) {
            sb.append(productos.get(i).toJson());
            if (i < productos.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]\n");
        Files.writeString(archivo, sb.toString(), StandardCharsets.UTF_8);
    }

    public List<Producto> cargarTodos() throws IOException {
        List<Producto> productos = new ArrayList<>();
        if (!Files.exists(archivo)) {
            return productos;
        }
        String contenido = Files.readString(archivo, StandardCharsets.UTF_8);
        Matcher m = PATRON_PRODUCTO.matcher(contenido);
        while (m.find()) {
            int id = Integer.parseInt(m.group(1));
            String nombre = m.group(2);
            double precio = Double.parseDouble(m.group(3));
            productos.add(new Producto(id, nombre, precio));
        }
        return productos;
    }
}
