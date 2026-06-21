import { getRoute } from "./utils/router";
import "./style.css";

import { loadLogin } from "./pages/auth/login/login";
import { loadStore } from "./pages/store/home/home";
import { loadCart } from "./pages/store/cart/cart";
import { loadOrders } from "./pages/client/orders/orders";

import {
    cart,
    addToCart,
    removeFromCart,
    updateQuantity,
    clearCart,
    getCartTotal
} from "./pages/store/cart/cart";

/* =========================
   CHECKOUT
========================= */

(window as any).checkout = async () => {

    if (cart.length === 0) {
        alert("El carrito está vacío");
        return;
    }

    const pedido = {
        fecha: new Date().toISOString(),
        total: getCartTotal(),
        detalles: cart.map(item => ({
            productoId: item.productId,
            nombre: item.name,
            cantidad: item.quantity,
            precio: item.price,
            subtotal: item.price * item.quantity
        }))
    };

    console.log("🧾 Pedido generado:", pedido);

    try {

        const pedidos =
            JSON.parse(localStorage.getItem("orders") || "[]");

        pedidos.push(pedido);

        localStorage.setItem(
            "orders",
            JSON.stringify(pedidos)
        );

        alert("Pedido realizado con éxito 🎉");

        clearCart();

        location.hash = "orders";

    } catch (error) {

        console.error(error);

        alert("Error al crear el pedido");
    }
};

/* =========================
   CART ACTIONS
========================= */

(window as any).addProduct = (product: any) => {
    addToCart(product);
};

(window as any).removeItem = (id: number) => {
    removeFromCart(id);
    loadCart();
};

(window as any).increase = (id: number) => {
    const item = cart.find(i => i.productId === id);

    if (item) {
        updateQuantity(id, item.quantity + 1);
        loadCart();
    }
};

(window as any).decrease = (id: number) => {
    const item = cart.find(i => i.productId === id);

    if (item && item.quantity > 1) {
        updateQuantity(id, item.quantity - 1);
        loadCart();
    }
};

/* =========================
   ROUTER
========================= */

function render() {

    switch (getRoute()) {

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

        default:
            loadLogin();
            break;
    }
}

window.addEventListener("hashchange", render);

render();