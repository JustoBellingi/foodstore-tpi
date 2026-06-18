import "./cart.css";

export interface CartItem {
    productId: number;
    name: string;
    price: number;
    quantity: number;
    image?: string;
}

/* =========================
   STORE
========================= */

export const cart: CartItem[] = [];

/* =========================
   ADD TO CART
========================= */

export function addToCart(product: any) {

    const existing = cart.find(item => item.productId === product.id);

    if (existing) {
        existing.quantity += 1;
        return;
    }

    cart.push({
        productId: product.id,
        name: product.nombre,
        price: product.precio,
        quantity: 1,
        image: product.imagen
    });
}

/* =========================
   REMOVE ITEM
========================= */

export function removeFromCart(productId: number) {

    const index = cart.findIndex(item => item.productId === productId);

    if (index !== -1) {
        cart.splice(index, 1);
    }
}

/* =========================
   UPDATE QUANTITY
========================= */

export function updateQuantity(productId: number, quantity: number) {

    const item = cart.find(i => i.productId === productId);

    if (item && quantity > 0) {
        item.quantity = quantity;
    }
}

/* =========================
   TOTAL
========================= */

export function getCartTotal(): number {

    return cart.reduce((total, item) => {
        return total + item.price * item.quantity;
    }, 0);
}

/* =========================
   CLEAR
========================= */

export function clearCart() {
    cart.length = 0;
}

/* =========================
   GET CART
========================= */

export function getCart() {
    return cart;
}

/* =========================
   UI
========================= */

export function loadCart() {

    const app = document.querySelector<HTMLDivElement>("#app")!;

    if (cart.length === 0) {
        app.innerHTML = `
            <h1>Carrito</h1>
            <p>Tu carrito está vacío.</p>

            <button onclick="location.hash='store'">
                Volver a la tienda
            </button>
        `;
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

                        <button class="cart-btn" onclick="window.decrease(${item.productId})">
                            -
                        </button>

                        <button class="cart-btn" onclick="window.increase(${item.productId})">
                            +
                        </button>

                        <button class="cart-btn danger cart-btn-delete" onclick="window.removeItem(${item.productId})">
                            Eliminar
                        </button>

                    </div>

                </div>
            `).join("")}
        </div>

        <h2 class="cart-total">
            Total: $${getCartTotal()}
        </h2>

        <button class="checkout-btn" onclick="window.checkout()">
            Finalizar compra
        </button>

        <button onclick="location.hash='store'">
            Seguir comprando
        </button>
    `;
}