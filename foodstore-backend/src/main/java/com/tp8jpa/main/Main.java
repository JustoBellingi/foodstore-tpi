package com.tp8jpa.main;

import com.tp8jpa.entities.*;
import com.tp8jpa.entities.enums.*;
import com.tp8jpa.repository.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final CategoriaRepository categoriaRepository = new CategoriaRepository();
    private static final ProductoRepository productoRepository = new ProductoRepository();
    private static final UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static final PedidoRepository pedidoRepository = new PedidoRepository();
    

    // =========================
    // INPUT
    // =========================

    private static String readLine() {
        return scanner.nextLine().trim();
    }

    private static int readInt() {
        return Integer.parseInt(readLine());
    }

    private static long readLong() {
        return Long.parseLong(readLine());
    }

    private static double readDouble() {
        return Double.parseDouble(readLine());
    }
    

    // =========================
    // MAIN
    // =========================

    public static void main(String[] args) {

        int opcion;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1 - ABM Categorias");
            System.out.println("2 - ABM Productos");
            System.out.println("3 - ABM Usuarios");
            System.out.println("4 - ABM Pedidos");
            System.out.println("5 - Reportes");
            System.out.println("0 - Salir");

            System.out.print("Opcion: ");
            opcion = readInt();

            switch (opcion) {
                case 1 -> menuCategorias();
                case 2 -> menuProductos();
                case 3 -> menuUsuarios();
                case 4 -> menuPedidos();
                case 5 -> menuReportes();
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
            opcion = readInt();

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
        String nombre = readLine();

        System.out.print("Descripcion: ");
        String descripcion = readLine();

        if (nombre.isBlank()) {
            System.out.println("Nombre vacio.");
            return;
        }

        Categoria c = new Categoria(nombre, descripcion);
        c = categoriaRepository.guardar(c);

        System.out.println("Categoria creada ID: " + c.getId());
    }

    private static void modificarCategoria() {

        listarCategorias();

        System.out.print("ID: ");
        Long id = readLong();

        Optional<Categoria> opt = categoriaRepository.buscarPorId(id);

        if (opt.isEmpty()) {
            System.out.println("No encontrada.");
            return;
        }

        Categoria c = opt.get();

        System.out.print("Nuevo nombre: ");
        String nombre = readLine();
        if (!nombre.isBlank()) c.setNombre(nombre);

        System.out.print("Nueva descripcion: ");
        String desc = readLine();
        if (!desc.isBlank()) c.setDescripcion(desc);

        categoriaRepository.guardar(c);

        System.out.println("Categoria modificada.");
    }

    private static void bajaCategoria() {

        listarCategorias();

        System.out.print("ID: ");
        Long id = readLong();

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
            opcion = readInt();

            switch (opcion) {
                case 1 -> altaProducto();
                case 2 -> modificarProducto();
                case 3 -> bajaProducto();
                case 4 -> listarProductos();
            }

        } while (opcion != 0);
    }

    private static void altaProducto() {

        listarCategorias();

        System.out.print("ID categoria: ");
        long catId = readLong();

        Optional<Categoria> optCat = categoriaRepository.buscarPorId(catId);

        if (optCat.isEmpty()) {
            System.out.println("Categoria invalida.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = readLine();

        System.out.print("Precio: ");
        double precio = readDouble();

        System.out.print("Stock: ");
        int stock = readInt();

        System.out.print("Descripcion: ");
        String descripcion = readLine();

        if (precio <= 0 || stock < 0) {
            System.out.println("Datos invalidos.");
            return;
        }

        Producto p = new Producto(nombre, precio, descripcion, stock, "default.jpg", true);

        categoriaRepository.agregarProducto(catId, p);

        System.out.println("Producto creado correctamente.");
    }

    private static void modificarProducto() {

        listarProductos();

        System.out.print("ID producto: ");
        long id = readLong();

        Optional<Producto> opt = productoRepository.buscarPorId(id);

        if (opt.isEmpty()) {
            System.out.println("No encontrado.");
            return;
        }

        Producto p = opt.get();

        System.out.print("Nuevo nombre: ");
        String nombre = readLine();
        if (!nombre.isBlank()) p.setNombre(nombre);

        System.out.print("Nuevo precio: ");
        String precio = readLine();
        if (!precio.isBlank()) p.setPrecio(Double.parseDouble(precio));

        System.out.print("Nuevo stock: ");
        String stock = readLine();
        if (!stock.isBlank()) p.setStock(Integer.parseInt(stock));

        System.out.print("Nueva descripcion: ");
        String desc = readLine();
        if (!desc.isBlank()) p.setDescripcion(desc);

        productoRepository.guardar(p);

        System.out.println("Producto modificado.");
    }

    private static void bajaProducto() {

        listarProductos();

        System.out.print("ID: ");
        long id = readLong();

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
    // USUARIOS
    // =========================

    private static void menuUsuarios() {

        int opcion;

        do {
            System.out.println("\n===== ABM USUARIOS =====");
            System.out.println("1 - Crear");
            System.out.println("2 - Modificar");
            System.out.println("3 - Eliminar");
            System.out.println("4 - Listar");
            System.out.println("0 - Volver");

            System.out.print("Opcion: ");
            opcion = readInt();

            switch (opcion) {
                case 1 -> altaUsuario();
                case 2 -> modificarUsuario();
                case 3 -> bajaUsuario();
                case 4 -> listarUsuarios();
            }

        } while (opcion != 0);
    }

    private static void altaUsuario() {

        System.out.print("Nombre: ");
        String nombre = readLine();

        System.out.print("Apellido: ");
        String apellido = readLine();

        System.out.print("Mail: ");
        String mail = readLine();

        System.out.print("Celular: ");
        String celular = readLine();

        System.out.print("Contraseña: ");
        String pass = readLine();

        System.out.print("Rol (1 ADMIN / 2 USUARIO): ");
        Rol rol = (readInt() == 1) ? Rol.ADMIN : Rol.USUARIO;

        Usuario u = new Usuario(nombre, apellido, mail, celular, pass, rol);
        u = usuarioRepository.guardar(u);

        System.out.println("Usuario creado ID: " + u.getId());
    }

    private static void modificarUsuario() {

        listarUsuarios();

        System.out.print("ID: ");
        long id = readLong();

        Optional<Usuario> opt = usuarioRepository.buscarPorId(id);

        if (opt.isEmpty()) {
            System.out.println("No encontrado.");
            return;
        }

        Usuario u = opt.get();

        System.out.print("Nuevo nombre: ");
        String nombre = readLine();
        if (!nombre.isBlank()) u.setNombre(nombre);

        System.out.print("Nuevo apellido: ");
        String apellido = readLine();
        if (!apellido.isBlank()) u.setApellido(apellido);

        usuarioRepository.guardar(u);

        System.out.println("Usuario modificado.");
    }

    private static void bajaUsuario() {

        listarUsuarios();

        System.out.print("ID: ");
        long id = readLong();

        boolean ok = usuarioRepository.eliminarLogico(id);

        System.out.println(ok ? "Usuario eliminado." : "No encontrado.");
    }

    private static void listarUsuarios() {

        List<Usuario> lista = usuarioRepository.listarActivos();

        if (lista.isEmpty()) {
            System.out.println("Sin usuarios.");
            return;
        }

        System.out.println("\n===== USUARIOS =====");

        for (Usuario u : lista) {
            System.out.println(
                    "ID: " + u.getId() +
                    " | " + u.getNombre() + " " + u.getApellido() +
                    " | Mail: " + u.getMail() +
                    " | Rol: " + u.getRol()
            );
        }
    }

// =========================
// PEDIDOS
// =========================

// =========================
// PEDIDOS
// =========================

private static void menuPedidos() {

    int opcion;

    do {
        System.out.println("\n===== ABM PEDIDOS =====");
        System.out.println("1 - Crear");
        System.out.println("2 - Cambiar estado");
        System.out.println("3 - Eliminar");
        System.out.println("4 - Listar");
        System.out.println("0 - Volver");

        System.out.print("Opcion: ");
        opcion = readInt();

        switch (opcion) {
            case 1 -> altaPedido();
            case 2 -> cambiarEstadoPedido();
            case 3 -> bajaPedido();
            case 4 -> listarPedidos();
            case 0 -> System.out.println("Volver hacia el menu principal");
            default -> System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}

// =========================
// ALTA PEDIDO
// =========================

private static void altaPedido() {

    System.out.println("\n===== NUEVO PEDIDO =====");

    System.out.print("ID usuario: ");
    long userId = readLong();

    Optional<Usuario> optUser = usuarioRepository.buscarPorId(userId);

    if (optUser.isEmpty()) {
        System.out.println("Usuario no encontrado.");
        return;
    }

    System.out.println("Forma de pago (1 TARJETA / 2 TRANSFERENCIA / 3 EFECTIVO): ");
    int fp = readInt();

    FormaPago formaPago = switch (fp) {
        case 1 -> FormaPago.TARJETA;
        case 2 -> FormaPago.TRANSFERENCIA;
        default -> FormaPago.EFECTIVO;
    };

    Pedido pedido = new Pedido();
    pedido.setFecha(LocalDate.now());
    pedido.setEstado(Estado.PENDIENTE);
    pedido.setFormaPago(formaPago);

    while (true) {

        listarProductos();

        System.out.print("ID producto (pulsa 0 para terminar): ");
        long prodId = readLong();

        if (prodId == 0) break;

        Optional<Producto> optProd = productoRepository.buscarPorId(prodId);

        if (optProd.isEmpty()) {
            System.out.println("Producto invalido.");
            continue;
        }

        Producto p = optProd.get();

        System.out.print("Cantidad: ");
        int cant = readInt();

        if (cant <= 0 || cant > p.getStock()) {
            System.out.println("Stock insuficiente.");
            continue;
        }

        pedido.addDetalle(cant, p);

        p.setStock(p.getStock() - cant);
        productoRepository.guardar(p);
    }

    pedido.calcularTotal();

    pedido = pedidoRepository.guardar(pedido);

    System.out.println("Pedido creado ID: " + pedido.getId());
    System.out.println("Total: $" + pedido.getTotal());
}
// =========================
// CAMBIAR ESTADO
// =========================

private static void cambiarEstadoPedido() {

    listarPedidos();

    System.out.print("ID pedido: ");
    long id = readLong();

    Optional<Pedido> opt = pedidoRepository.buscarPorId(id);

    if (opt.isEmpty()) {
        System.out.println("No encontrado.");
        return;
    }

    Pedido p = opt.get();

    System.out.println("Estado actual: " + p.getEstado());
    System.out.println("1 PENDIENTE / 2 CONFIRMADO / 3 TERMINADO / 4 CANCELADO");

    int op = readInt();

    Estado estado = switch (op) {
        case 2 -> Estado.CONFIRMADO;
        case 3 -> Estado.TERMINADO;
        case 4 -> Estado.CANCELADO;
        default -> Estado.PENDIENTE;
    };

    p.setEstado(estado);
    pedidoRepository.guardar(p);

    System.out.println("Estado actualizado.");
}

// =========================
// BAJA
// =========================

private static void bajaPedido() {

    listarPedidos();

    System.out.print("ID: ");
    long id = readLong();

    boolean ok = pedidoRepository.eliminarLogico(id);

    System.out.println(ok ? "Pedido eliminado." : "No encontrado.");
}

// =========================
// LISTAR
// =========================

private static void listarPedidos() {

    List<Pedido> lista = pedidoRepository.listarActivos();

    if (lista.isEmpty()) {
        System.out.println("Sin pedidos.");
        return;
    }

    System.out.println("\n===== PEDIDOS =====");

    for (Pedido p : lista) {
        System.out.println(
                "ID: " + p.getId() +
                " | Fecha: " + p.getFecha() +
                " | Estado: " + p.getEstado() +
                " | Total: $" + p.getTotal()
        );
    }
}

// =========================
// REPORTES
// =========================

private static void menuReportes() {

    int opcion;

    do {
        System.out.println("\n===== REPORTES =====");
        System.out.println("1 - Total facturado ");
        System.out.println("2 - Detalle de facturacion");
        System.out.println("0 - Volver");

        System.out.print("Opcion: ");
        opcion = readInt();

        switch (opcion) {
            case 1 -> totalFacturado();
            case 2 -> detalleFacturacion();
            case 0 -> System.out.println("Volver hacia el menu principal.");
            default -> System.out.println("Opcion invalida.");
        }

    } while (opcion != 0);
}

private static void totalFacturado() {

    List<Pedido> pedidos = pedidoRepository.listarActivos();

    double total = 0;

    for (Pedido p : pedidos) {

        if (p.getEstado() == Estado.TERMINADO) {
            total += p.getTotal();
        }
    }

    System.out.println("\n===== REPORTE =====");
    System.out.println("Total facturado: $" + total);
}
private static void detalleFacturacion() {

    List<Pedido> pedidos = pedidoRepository.listarActivos();

    System.out.println("\n===== DETALLE FACTURACION =====");

    for (Pedido p : pedidos) {

        if (p.getEstado() == Estado.TERMINADO) {

            System.out.println(
                "ID: " + p.getId() +
                " | Fecha: " + p.getFecha() +
                " | Total: $" + p.getTotal()
            );
        }
    }
}
}