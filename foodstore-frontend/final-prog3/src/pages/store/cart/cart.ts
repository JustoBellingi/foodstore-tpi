import "./cart.css";
import { navigate } from "../../../utils/router";

export interface CartItem {
    productId: number;
    name: string;
    price: number;
    quantity: number;
    image?: string;
}

/* =========================
   STATE + STORAGE
========================= */

export let cart: CartItem[] = JSON.parse(
    localStorage.getItem("cart") || "[]"
);

function saveCart() {
    localStorage.setItem("cart", JSON.stringify(cart));
}

/* =========================
   ADD
========================= */

export function addToCart(product: any) {

    const existing = cart.find(i => i.productId === product.id);

    if (existing) {
        existing.quantity += 1;
    } else {
        cart.push({
            productId: product.id,
            name: product.nombre,
            price: product.precio,
            quantity: 1,
            image: product.imagen
        });
    }

    saveCart();
}

/* =========================
   REMOVE
========================= */

export function removeFromCart(productId: number) {
    cart = cart.filter(i => i.productId !== productId);
    saveCart();
}

/* =========================
   UPDATE
========================= */

export function updateQuantity(productId: number, quantity: number) {

    const item = cart.find(i => i.productId === productId);

    if (item) {
        item.quantity = Math.max(1, quantity);
        saveCart();
    }
}

/* =========================
   TOTAL
========================= */

export function getCartTotal() {
    return cart.reduce((t, i) => t + i.price * i.quantity, 0);
}

/* =========================
   CLEAR
========================= */

export function clearCart() {
    cart = [];
    saveCart();
}

/* =========================
   UI
========================= */

export function loadCart() {

    const app = document.querySelector("#app") as HTMLDivElement;

    if (cart.length === 0) {
        app.innerHTML = `
            <h1>Carrito</h1>
            <p>Tu carrito está vacío.</p>
            <button id="back">Volver</button>
        `;

        document.getElementById("back")
            ?.addEventListener("click", () => navigate("store"));

        return;
    }

    app.innerHTML = `
        <h1>Carrito</h1>

        <div class="cart-container">
            ${cart.map(item => `
                <div class="cart-item">

                    <h3>${item.name}</h3>
                    <p>Precio: $${item.price}</p>
                    <p>Cantidad: ${item.quantity}</p>

                    <div class="cart-actions">
                        <button onclick="window.decrease(${item.productId})">-</button>
                        <button onclick="window.increase(${item.productId})">+</button>
                        <button onclick="window.removeItem(${item.productId})">
                            Eliminar
                        </button>
                    </div>

                </div>
            `).join("")}
        </div>

        <h2>Total: $${getCartTotal()}</h2>

        <button onclick="window.checkout()">
            Finalizar compra
        </button>

        <button id="back2">Seguir comprando</button>
    `;

    document.getElementById("back2")
        ?.addEventListener("click", () => navigate("store"));
}

/* =========================
   CHECKOUT
========================= */

window.checkout = () => {

    const products = JSON.parse(localStorage.getItem("products") || "[]");
    const orders = JSON.parse(localStorage.getItem("orders") || "[]");

    if (cart.length === 0) return;

    // validar stock
    for (const item of cart) {
        const product = products.find((p: any) => p.id === item.productId);

        if (!product || product.stock < item.quantity) {
            alert(`Stock insuficiente en ${item.name}`);
            return;
        }
    }

    // descontar stock
    for (const item of cart) {
        const product = products.find((p: any) => p.id === item.productId);
        product.stock -= item.quantity;
    }

    localStorage.setItem("products", JSON.stringify(products));

    // armar pedido
    const newOrder = {
        id: Date.now(),
        date: new Date().toISOString(),
        status: "PENDIENTE",
        payment: "EFECTIVO",
        total: getCartTotal(),
        details: cart.map(item => ({
            productId: item.productId,
            name: item.name,
            quantity: item.quantity,
            price: item.price,
            subtotal: item.price * item.quantity
        }))
    };

    orders.push(newOrder);
    localStorage.setItem("orders", JSON.stringify(orders));

    clearCart();

    alert("Compra realizada con éxito");

    location.hash = "store";
};