package com.tp8jpa.main;

import com.tp8jpa.entities.Categoria;
import com.tp8jpa.entities.Producto;
import com.tp8jpa.repository.CategoriaRepository;
import com.tp8jpa.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    // Scanner global
    private static final Scanner scanner = new Scanner(System.in);

    // Repositorios
    private static final CategoriaRepository categoriaRepository =
            new CategoriaRepository();

    private static final ProductoRepository productoRepository =
            new ProductoRepository();

    // MAIN
    public static void main(String[] args) {

        int opcion;

        do {

            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - ABM Categorias");
            System.out.println("2 - ABM Productos");
            System.out.println("3 - Reportes");
            System.out.println("0 - Salir");

            System.out.print("Opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    menuCategorias();
                    break;

                case 2:
                    menuProductos();
                    break;

                case 3:
                    menuReportes();
                    break;

                case 0:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    // MENU CATEGORIAS
    private static void menuCategorias() {

        int opcion;

        do {

            System.out.println("\n===== ABM CATEGORIAS =====");
            System.out.println("1 - Crear");
            System.out.println("2 - Modificar");
            System.out.println("3 - Eliminar");
            System.out.println("4 - Listar");
            System.out.println("0 - Volver");

            System.out.print("Opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    altaCategoria();
                    break;

                case 2:
                    modificarCategoria();
                    break;

                case 3:
                    bajaCategoria();
                    break;

                case 4:
                    listarCategorias();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    // ALTA CATEGORIA

    private static void altaCategoria() {

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        // Validacion nombre vacio
        if (nombre.isBlank()) {

            System.out.println(
                    "El nombre no puede estar vacio."
            );

            return;
        }

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        Categoria categoria =
                new Categoria(nombre, descripcion);

        categoria = categoriaRepository.guardar(categoria);

        System.out.println(
                "Categoria creada con ID: "
                + categoria.getId()
        );
    }

    // MODIFICAR CATEGORIA

    private static void modificarCategoria() {

        listarCategorias();

        System.out.print("Ingrese ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<Categoria> optional =
                categoriaRepository.buscarPorId(id);

        if (optional.isEmpty()) {

            System.out.println(
                    "Categoria no encontrada."
            );

            return;
        }

        Categoria categoria = optional.get();

        // Mostrar valores actuales
        System.out.println(
                "Nombre actual: "
                + categoria.getNombre()
        );

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        // Si presiona Enter se conserva
        if (!nombre.isBlank()) {
            categoria.setNombre(nombre);
        }

        System.out.println(
                "Descripcion actual: "
                + categoria.getDescripcion()
        );

        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine();

        if (!descripcion.isBlank()) {
            categoria.setDescripcion(descripcion);
        }

        categoriaRepository.guardar(categoria);

        System.out.println("Categoria modificada.");
    }

    // BAJA CATEGORIA

    private static void bajaCategoria() {

        listarCategorias();

        System.out.print("Ingrese ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        // FIX HU-05: obtener el nombre antes de eliminar para mostrarlo en la confirmacion
        Optional<Categoria> optional =
                categoriaRepository.buscarPorId(id);

        if (optional.isEmpty()) {

            System.out.println(
                    "Categoria no encontrada."
            );

            return;
        }

        String nombreCategoria = optional.get().getNombre();

        boolean eliminada =
                categoriaRepository.eliminarLogico(id);

        if (eliminada) {

            // Muestra el nombre de la categoria afectada (criterio 4 HU-05)
            System.out.println(
                    "Categoria eliminada: " + nombreCategoria
            );

        } else {

            System.out.println(
                    "Categoria no encontrada."
            );
        }
    }

    // LISTAR CATEGORIAS

    private static void listarCategorias() {

        List<Categoria> categorias =
                categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {

            System.out.println(
                    "No hay categorias."
            );

            return;
        }

        System.out.println("\n===== CATEGORIAS =====");

        for (Categoria categoria : categorias) {

            System.out.println(
                    "ID: " + categoria.getId()
                    + " | Nombre: "
                    + categoria.getNombre()
                    + " | Descripcion: "
                    + categoria.getDescripcion()
            );
        }
    }

    // MENU PRODUCTOS

    private static void menuProductos() {

        int opcion;

        do {

            System.out.println("\n===== ABM PRODUCTOS =====");
            System.out.println("1 - Crear producto");
            System.out.println("2 - Modificar producto");
            System.out.println("3 - Eliminar producto");
            System.out.println("4 - Listar productos");
            System.out.println("0 - Volver");

            System.out.print("Opcion: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {

                case 1:
                    altaProducto();
                    break;

                case 2:
                    modificarProducto();
                    break;

                case 3:
                    bajaProducto();
                    break;

                case 4:
                    listarProductos();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    // ALTA PRODUCTO

    private static void altaProducto() {

        List<Categoria> categorias =
                categoriaRepository.listarActivos();

        // Si no hay categorias activas se cancela la operacion (criterio 2 HU-06)
        if (categorias.isEmpty()) {

            System.out.println(
                    "No hay categorias activas. No se puede crear un producto."
            );

            return;
        }

        listarCategorias();

        System.out.print("Ingrese ID categoria: ");
        Long categoriaId = scanner.nextLong();
        scanner.nextLine();

        Optional<Categoria> optionalCategoria =
                categoriaRepository.buscarPorId(categoriaId);

        if (optionalCategoria.isEmpty()) {

            System.out.println(
                    "Categoria inexistente."
            );

            return;
        }

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        // Validacion nombre vacio
        if (nombre.isBlank()) {

            System.out.println(
                    "El nombre no puede estar vacio."
            );

            return;
        }

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        // FIX HU-06: precio debe ser mayor a 0 (no >= 0), stock mayor o igual a 0
        if (precio <= 0 || stock < 0) {

            System.out.println(
                    "Precio invalido (debe ser mayor a 0) o stock invalido (debe ser 0 o mas)."
            );

            return;
        }

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        Producto producto =
                new Producto(
                        nombre,
                        precio,
                        descripcion,
                        stock
                );

        // Relacion con categoria
        Categoria categoriaAsignada = optionalCategoria.get();
        producto.setCategoria(categoriaAsignada);

        producto = productoRepository.guardar(producto);

        // FIX HU-06: mostrar ID generado y la categoria asignada (criterio 5)
        System.out.println(
                "Producto creado con ID: " + producto.getId()
                + " | Categoria: " + categoriaAsignada.getNombre()
        );
    }

    // MODIFICAR PRODUCTO

    private static void modificarProducto() {

        listarProductos();

        System.out.print("Ingrese ID producto: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<Producto> optional =
                productoRepository.buscarPorId(id);

        if (optional.isEmpty()) {

            System.out.println(
                    "Producto no encontrado."
            );

            return;
        }

        Producto producto = optional.get();

        // NOMBRE

        System.out.println(
                "Nombre actual: "
                + producto.getNombre()
        );

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();

        if (!nombre.isBlank()) {
            producto.setNombre(nombre);
        }

        // PRECIO

        System.out.println(
                "Precio actual: "
                + producto.getPrecio()
        );

        System.out.print("Nuevo precio: ");
        String precioInput = scanner.nextLine();

        if (!precioInput.isBlank()) {

            double precio =
                    Double.parseDouble(precioInput);

            // FIX HU-07: precio no puede ser menor o igual a 0 (criterio 5)
            if (precio > 0) {
                producto.setPrecio(precio);
            } else {
                System.out.println(
                        "Precio invalido, debe ser mayor a 0. Se conserva el valor anterior."
                );
            }
        }

        // STOCK

        System.out.println(
                "Stock actual: "
                + producto.getStock()
        );

        System.out.print("Nuevo stock: ");
        String stockInput = scanner.nextLine();

        if (!stockInput.isBlank()) {

            int stock =
                    Integer.parseInt(stockInput);

            if (stock >= 0) {
                producto.setStock(stock);
            } else {
                System.out.println(
                        "Stock invalido, no puede ser negativo. Se conserva el valor anterior."
                );
            }
        }

        // DESCRIPCION

        System.out.println(
                "Descripcion actual: "
                + producto.getDescripcion()
        );

        System.out.print("Nueva descripcion: ");
        String descripcion = scanner.nextLine();

        if (!descripcion.isBlank()) {
            producto.setDescripcion(descripcion);
        }

        productoRepository.guardar(producto);

        System.out.println("Producto modificado.");
    }

    // BAJA PRODUCTO

    private static void bajaProducto() {

        listarProductos();

        System.out.print("Ingrese ID producto: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<Producto> optional =
                productoRepository.buscarPorId(id);

        if (optional.isEmpty()) {

            System.out.println(
                    "Producto no encontrado."
            );

            return;
        }

        Producto producto = optional.get();

        boolean eliminado =
                productoRepository.eliminarLogico(id);

        if (eliminado) {

            System.out.println(
                    "Producto eliminado: "
                    + producto.getNombre()
            );

        } else {

            System.out.println(
                    "No se pudo eliminar."
            );
        }
    }

    // LISTAR PRODUCTOS

    private static void listarProductos() {

        List<Producto> productos =
                productoRepository.listarActivos();

        if (productos.isEmpty()) {

            System.out.println(
                    "No hay productos."
            );

            return;
        }

        System.out.println("\n===== PRODUCTOS =====");

        for (Producto producto : productos) {

            System.out.println(
                    "ID: " + producto.getId()
                    + " | Nombre: "
                    + producto.getNombre()
                    + " | Precio: "
                    + producto.getPrecio()
                    + " | Stock: "
                    + producto.getStock()
                    + " | Categoria: "
                    + producto.getCategoria().getNombre()
            );
        }
    }

    // REPORTES

    private static void menuReportes() {

        listarCategorias();

        System.out.print("Ingrese ID categoria: ");
        Long categoriaId = scanner.nextLong();
        scanner.nextLine();

        List<Producto> productos =
                productoRepository
                        .buscarPorCategoria(categoriaId);

        if (productos.isEmpty()) {

            System.out.println(
                    "No hay productos en esta categoria."
            );

            return;
        }

        System.out.println(
                "\n===== PRODUCTOS DE LA CATEGORIA ====="
        );

        for (Producto producto : productos) {

            System.out.println(
                    "ID: " + producto.getId()
                    + " | Nombre: "
                    + producto.getNombre()
                    + " | Precio: "
                    + producto.getPrecio()
                    + " | Stock: "
                    + producto.getStock()
            );
        }
    }
}