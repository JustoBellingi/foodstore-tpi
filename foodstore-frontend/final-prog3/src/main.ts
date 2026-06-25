import "./style.css";

import { getRoute } from "./utils/router";

import { loadLogin } from "./pages/auth/login/login";
import { loadStore } from "./pages/store/home/home";
import { loadCart } from "./pages/store/cart/cart";
import { loadOrders } from "./pages/client/orders/orders";
import { loadAdmin } from "./pages/admin/admin";


import {
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart,
    getCartTotal
} from "./pages/store/cart/cart";

/* =========================
   CHECKOUT GLOBAL
========================= */

(window as any).checkout = () => {

    const cart =
        JSON.parse(localStorage.getItem("cart") || "[]");

    if (!cart.length) {

        alert("El carrito está vacío");

        return;
    }

    const pedido = {

        fecha: new Date().toISOString(),

        estado: "PENDIENTE",

        formaPago: "EFECTIVO",

        total: getCartTotal(),

        detalles: cart.map((item: any) => ({

            productoId: item.productId,

            nombre: item.name,

            cantidad: item.quantity,

            precio: item.price,

            subtotal: item.price * item.quantity
        }))
    };

    try {

        const productos =
            JSON.parse(localStorage.getItem("products") || "[]");

        const pedidos =
            JSON.parse(localStorage.getItem("orders") || "[]");

        /* =========================
           DESCONTAR STOCK
        ========================= */

        cart.forEach((item: any) => {

            const producto = productos.find(
                (p: any) => p.id === item.productId
            );

            if (producto) {

                producto.stock -= item.quantity;

                if (producto.stock < 0) {
                    producto.stock = 0;
                }
            }
        });

        localStorage.setItem(
            "products",
            JSON.stringify(productos)
        );

        /* =========================
           GUARDAR PEDIDO
        ========================= */

        pedidos.push(pedido);

        localStorage.setItem(
            "orders",
            JSON.stringify(pedidos)
        );

        clearCart();

        alert("Pedido realizado 🎉");

        location.hash = "orders";

    } catch (error) {

        console.error(error);

        alert("Error en checkout");
    }
};

/* =========================
   CART ACTIONS
========================= */

(window as any).addProduct = addToCart;

(window as any).removeItem = (id: number) => {

    removeFromCart(id);

    loadCart();
};

(window as any).increase = (id: number) => {

    const cart =
        JSON.parse(localStorage.getItem("cart") || "[]");

    const item =
        cart.find((i: any) => i.productId === id);

    if (item) {

        updateQuantity(
            id,
            item.quantity + 1
        );

        loadCart();
    }
};

(window as any).decrease = (id: number) => {

    const cart =
        JSON.parse(localStorage.getItem("cart") || "[]");

    const item =
        cart.find((i: any) => i.productId === id);

    if (item && item.quantity > 1) {

        updateQuantity(
            id,
            item.quantity - 1
        );

        loadCart();
    }
};

/* =========================
   ADMIN ACTIONS
========================= */

(window as any).deleteProduct = (id: number) => {

    const productos =
        JSON.parse(localStorage.getItem("products") || "[]");

    const nuevos =
        productos.filter((p: any) => p.id !== id);

    localStorage.setItem(
        "products",
        JSON.stringify(nuevos)
    );

    loadAdmin();
};

/* =========================
   ROUTER
========================= */

function render() {

    const route = getRoute();

    switch (route) {

        case "login":
            loadLogin();
            break;

        case "store":
            loadStore();
            break;

        case "cart":
            loadCart();
            break;

        case "orders":
            loadOrders();
            break;

        case "admin":
            loadAdmin();
            break;

        default:
            loadLogin();
            break;
    }
}

window.addEventListener(
    "hashchange",
    render
);

render();