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
   STORAGE
========================= */

function getCart(): CartItem[] {
    return JSON.parse(localStorage.getItem("cart") || "[]");
}

function saveCart(cart: CartItem[]) {
    localStorage.setItem("cart", JSON.stringify(cart));
}

/* =========================
   ACTIONS
========================= */

export function addToCart(product: any) {
    const cart = getCart();

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

    saveCart(cart);
}

export function removeFromCart(id: number) {
    const cart = getCart().filter(i => i.productId !== id);
    saveCart(cart);
}

export function updateQuantity(id: number, qty: number) {
    const cart = getCart();

    const item = cart.find(i => i.productId === id);
    if (item) item.quantity = Math.max(1, qty);

    saveCart(cart);
}

export function clearCart() {
    localStorage.removeItem("cart");
}

export function getCartTotal(): number {
    const cart = getCart();
    return cart.reduce((t, i) => t + i.price * i.quantity, 0);
}

/* =========================
   UI
========================= */

export function loadCart() {
    const cart = getCart();
    const app = document.querySelector("#app") as HTMLDivElement;

    if (!cart.length) {
        app.innerHTML = `
            <h1>Carrito</h1>
            <p>Tu carrito está vacío</p>
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
                    <p>$${item.price}</p>
                    <p>Cant: ${item.quantity}</p>

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