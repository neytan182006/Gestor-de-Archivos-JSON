# Gestor de Archivos JSON

Gestor de productos que persiste en un archivo `.json` en disco, con lectura/escritura hecha a mano (sin Gson/Jackson), para el curso de **Programación Cliente-Servidor**.

## Estructura

```
src/
├── Producto.java             → POJO + serialización a JSON (Locale.US para forzar punto decimal)
├── GestorArchivoJson.java    → lee/escribe la lista completa en datos/productos.json (parser por regex)
└── Main.java                  → menú de consola
```

## Cómo ejecutarlo

```bash
javac -d bin src/*.java
java -cp bin Main
```

> **Verificado en runtime real con persistencia real en disco**: se agregaron productos, se cerró y reabrió el archivo, y se comprobó que los datos sobreviven entre ejecuciones.
>
> **Bug encontrado y corregido durante la prueba**: `String.format("%.2f", ...)` sin especificar `Locale.US` usaba la configuración regional del sistema (coma decimal), generando un archivo `.json` **inválido** (`"precio": 25000,00` en vez de `25000.00`). Se corrigió forzando `Locale.US` en el formateo de números — confirmado con una segunda ejecución que el archivo generado ya es JSON válido y se relee correctamente.

## Capturas

_Pendiente: agregar capturas del contenido del archivo `.json` generado en `capturas/`._

## Licencia

MIT — ver [LICENSE](LICENSE).
