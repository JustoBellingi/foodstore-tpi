package com.tp8jpa.main;

import com.tp8jpa.entities.Categoria;
import com.tp8jpa.entities.Producto;
import com.tp8jpa.repository.CategoriaRepository;
import com.tp8jpa.repository.ProductoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final CategoriaRepository categoriaRepository =
            new CategoriaRepository();

    private static final ProductoRepository productoRepository =
            new ProductoRepository();

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
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuReportes();
                case 0 -> System.out.println("Programa finalizado.");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);
    }

    // =========================
    // CATEGORIAS
    // =========================

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
                case 1 -> altaCategoria();
                case 2 -> modificarCategoria();
                case 3 -> bajaCategoria();
                case 4 -> listarCategorias();
            }

        } while (opcion != 0);
    }

    private static void altaCategoria() {

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        if (nombre.isBlank()) {
            System.out.println("Nombre vacio.");
            return;
        }

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        Categoria categoria = new Categoria(nombre, descripcion);
        categoria = categoriaRepository.guardar(categoria);

        System.out.println("Categoria creada ID: " + categoria.getId());
    }

    private static void modificarCategoria() {

        listarCategorias();

        System.out.print("ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<Categoria> opt = categoriaRepository.buscarPorId(id);

        if (opt.isEmpty()) {
            System.out.println("No encontrada.");
            return;
        }

        Categoria c = opt.get();

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        if (!nombre.isBlank()) c.setNombre(nombre);

        System.out.print("Nueva descripcion: ");
        String desc = scanner.nextLine();
        if (!desc.isBlank()) c.setDescripcion(desc);

        categoriaRepository.guardar(c);

        System.out.println("Categoria modificada.");
    }

    private static void bajaCategoria() {

        listarCategorias();

        System.out.print("ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        boolean ok = categoriaRepository.eliminarLogico(id);

        System.out.println(ok ? "Categoria eliminada." : "No encontrada.");
    }

    private static void listarCategorias() {

        List<Categoria> lista = categoriaRepository.listarActivos();

        if (lista.isEmpty()) {
            System.out.println("Sin categorias.");
            return;
        }

        System.out.println("\n===== CATEGORIAS =====");

        for (Categoria c : lista) {
            System.out.println(
                    "ID: " + c.getId() +
                    " | Nombre: " + c.getNombre() +
                    " | Descripcion: " + c.getDescripcion()
            );
        }
    }

    // =========================
    // PRODUCTOS
    // =========================

    private static void menuProductos() {

        int opcion;

        do {
            System.out.println("\n===== ABM PRODUCTOS =====");
            System.out.println("1 - Crear");
            System.out.println("2 - Modificar");
            System.out.println("3 - Eliminar");
            System.out.println("4 - Listar");
            System.out.println("0 - Volver");

            System.out.print("Opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> altaProducto();
                case 2 -> modificarProducto();
                case 3 -> bajaProducto();
                case 4 -> listarProductos();
            }

        } while (opcion != 0);
    }

    private static void altaProducto() {

        List<Categoria> categorias = categoriaRepository.listarActivos();

        if (categorias.isEmpty()) {
            System.out.println("No hay categorias.");
            return;
        }

        listarCategorias();

        System.out.print("ID categoria: ");
        Long catId = scanner.nextLong();
        scanner.nextLine();

        Optional<Categoria> optCat = categoriaRepository.buscarPorId(catId);

        if (optCat.isEmpty()) {
            System.out.println("Categoria invalida.");
            return;
        }
        Categoria categoria = optCat.get();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        if (nombre.isBlank()) {
            System.out.println("Nombre vacio.");
            return;
        }

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();

        if (precio <= 0 || stock < 0) {
            System.out.println("Datos invalidos.");
            return;
        }

        System.out.print("Descripcion: ");
        String descripcion = scanner.nextLine();

        Producto producto = new Producto(
            nombre,
            precio,
            descripcion,
            stock,
            "default.jpg",
            true
        );

        categoria.addProducto(producto);

        categoria = categoriaRepository.guardar(categoria);

        System.out.println("Producto creado correctamente.");
    }

    private static void modificarProducto() {

        listarProductos();

        System.out.print("ID producto: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        Optional<Producto> opt = productoRepository.buscarPorId(id);

        if (opt.isEmpty()) {
            System.out.println("No encontrado.");
            return;
        }

        Producto p = opt.get();

        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        if (!nombre.isBlank()) p.setNombre(nombre);

        System.out.print("Nuevo precio: ");
        String precio = scanner.nextLine();
        if (!precio.isBlank()) p.setPrecio(Double.parseDouble(precio));

        System.out.print("Nuevo stock: ");
        String stock = scanner.nextLine();
        if (!stock.isBlank()) p.setStock(Integer.parseInt(stock));

        System.out.print("Nueva descripcion: ");
        String desc = scanner.nextLine();
        if (!desc.isBlank()) p.setDescripcion(desc);

        productoRepository.guardar(p);

        System.out.println("Producto modificado.");
    }

    private static void bajaProducto() {

        listarProductos();

        System.out.print("ID: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        boolean ok = productoRepository.eliminarLogico(id);

        System.out.println(ok ? "Producto eliminado." : "No encontrado.");
    }

    private static void listarProductos() {

        List<Producto> lista = productoRepository.listarActivos();

        if (lista.isEmpty()) {
            System.out.println("Sin productos.");
            return;
        }

        System.out.println("\n===== PRODUCTOS =====");

        for (Producto p : lista) {
            System.out.println(
                    "ID: " + p.getId() +
                    " | Nombre: " + p.getNombre() +
                    " | Precio: " + p.getPrecio() +
                    " | Stock: " + p.getStock()
            );
        }
    }

    // =========================
    // REPORTES
    // =========================

    private static void menuReportes() {

        listarCategorias();

        System.out.print("ID categoria: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        List<Producto> productos =
                productoRepository.buscarPorCategoria(id);

        if (productos.isEmpty()) {
            System.out.println("Sin productos.");
            return;
        }

        System.out.println("\n===== REPORTE =====");

        for (Producto p : productos) {
            System.out.println(
                    "ID: " + p.getId() +
                    " | Nombre: " + p.getNombre() +
                    " | Precio: " + p.getPrecio() +
                    " | Stock: " + p.getStock()
            );
        }
    }
}