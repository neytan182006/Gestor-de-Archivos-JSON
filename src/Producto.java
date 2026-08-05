import java.util.Locale;

public class Producto {

    private int id;
    private String nombre;
    private double precio;

    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String toJson() {
        // Locale.US fuerza el punto decimal (obligatorio en JSON valido);
        // con la configuracion regional del sistema %.2f podria imprimir
        // coma decimal y producir un archivo .json invalido.
        return String.format(Locale.US, "  {\"id\": %d, \"nombre\": \"%s\", \"precio\": %.2f}",
                id, nombre.replace("\"", "\\\""), precio);
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "[%d] %-25s $%.2f", id, nombre, precio);
    }
}
