import "./style.css";

import { getRoute } from "./utils/router";

import { loadLogin } from "./pages/auth/login/login";
import { loadStore } from "./pages/store/home/home";
import { loadCart } from "./pages/store/cart/cart";
import { loadOrders } from "./pages/client/orders/orders";

import {
  addToCart,
  removeFromCart,
  updateQuantity,
  clearCart,
  getCartTotal,
} from "./pages/store/cart/cart";

/* =========================
   CHECKOUT GLOBAL
========================= */

(window as any).checkout = () => {
  const cart = JSON.parse(localStorage.getItem("cart") || "[]");

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
      subtotal: item.price * item.quantity,
    })),
  };

  try {
    const productos = JSON.parse(localStorage.getItem("products") || "[]");
    const pedidos = JSON.parse(localStorage.getItem("orders") || "[]");

    // descontar stock
    cart.forEach((item: any) => {
      const product = productos.find((p: any) => p.id === item.productId);
      if (product) product.stock -= item.quantity;
    });

    localStorage.setItem("products", JSON.stringify(productos));

    pedidos.push(pedido);
    localStorage.setItem("orders", JSON.stringify(pedidos));

    clearCart();

    alert("Pedido realizado 🎉");
    location.hash = "orders";
  } catch (err) {
    console.error(err);
    alert("Error en checkout");
  }
};

/* =========================
   CART ACTIONS GLOBALES
========================= */

(window as any).addProduct = addToCart;

(window as any).removeItem = (id: number) => {
  removeFromCart(id);
  loadCart();
};

(window as any).increase = (id: number) => {
  const cart = JSON.parse(localStorage.getItem("cart") || "[]");
  const item = cart.find((i: any) => i.productId === id);

  if (item) {
    updateQuantity(id, item.quantity + 1);
    loadCart();
  }
};

(window as any).decrease = (id: number) => {
  const cart = JSON.parse(localStorage.getItem("cart") || "[]");
  const item = cart.find((i: any) => i.productId === id);

  if (item && item.quantity > 1) {
    updateQuantity(id, item.quantity - 1);
    loadCart();
  }
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

    default:
      loadLogin();
  }
}

window.addEventListener("hashchange", render);
render();