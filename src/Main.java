import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner TECLADO = new Scanner(System.in);
    private static final GestorArchivoJson gestor = new GestorArchivoJson("datos/productos.json");
    private static int siguienteId = 1;

    public static void main(String[] args) throws IOException {
        List<Producto> productos = gestor.cargarTodos();
        if (!productos.isEmpty()) {
            siguienteId = productos.stream().mapToInt(Producto::getId).max().getAsInt() + 1;
        }

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");

            try {
                switch (opcion) {
                    case 1 -> agregarProducto(productos);
                    case 2 -> listarProductos(productos);
                    case 3 -> eliminarProducto(productos);
                    case 0 -> System.out.println("Hasta luego.");
                    default -> System.out.println("Opcion invalida.");
                }
            } catch (IOException e) {
                System.out.println("Error de archivo: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== GESTOR DE ARCHIVOS JSON (datos/productos.json) ===");
        System.out.println("1. Agregar producto");
        System.out.println("2. Listar productos (leidos del archivo)");
        System.out.println("3. Eliminar producto por id");
        System.out.println("0. Salir");
    }

    private static void agregarProducto(List<Producto> productos) throws IOException {
        System.out.print("Nombre: ");
        String nombre = TECLADO.nextLine();
        double precio = leerDouble("Precio: ");

        productos.add(new Producto(siguienteId++, nombre, precio));
        gestor.guardarTodos(productos);
        System.out.println("Producto guardado en el archivo JSON.");
    }

    private static void listarProductos(List<Producto> productosEnMemoria) throws IOException {
        List<Producto> desdeArchivo = gestor.cargarTodos(); // relee del disco para demostrar la persistencia real
        if (desdeArchivo.isEmpty()) {
            System.out.println("El archivo esta vacio o no existe todavia.");
            return;
        }
        desdeArchivo.forEach(System.out::println);
    }

    private static void eliminarProducto(List<Producto> productos) throws IOException {
        int id = leerEntero("Id del producto a eliminar: ");
        boolean eliminado = productos.removeIf(p -> p.getId() == id);
        if (eliminado) {
            gestor.guardarTodos(productos);
            System.out.println("Producto eliminado y archivo actualizado.");
        } else {
            System.out.println("No se encontro un producto con ese id.");
        }
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        while (!TECLADO.hasNextInt()) {
            System.out.print("Ingrese un numero valido: ");
            TECLADO.next();
        }
        int valor = TECLADO.nextInt();
        TECLADO.nextLine();
        return valor;
    }

    private static double leerDouble(String mensaje) {
        System.out.print(mensaje);
        while (!TECLADO.hasNextDouble()) {
            System.out.print("Ingrese un numero valido: ");
            TECLADO.next();
        }
        double valor = TECLADO.nextDouble();
        TECLADO.nextLine();
        return valor;
    }
}
