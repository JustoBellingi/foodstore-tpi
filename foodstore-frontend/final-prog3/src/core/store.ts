export interface CartItem {
  productId: number;
  name: string;
  price: number;
  quantity: number;
  image?: string;
}

export interface Product {
  id: number;
  nombre: string;
  descripcion: string;
  precio: number;
  stock: number;
  imagen: string;
  categoria: string;
}

/* =========================
   STATE
========================= */

export let cart: CartItem[] =
  JSON.parse(localStorage.getItem("cart") || "[]");

export let products: Product[] =
  JSON.parse(localStorage.getItem("products") || "[]");

/* =========================
   STORAGE
========================= */

function saveCart() {
  localStorage.setItem("cart", JSON.stringify(cart));
}

function saveProducts() {
  localStorage.setItem("products", JSON.stringify(products));
}

/* =========================
   CART
========================= */

export function addToCart(p: Product) {
  const item = cart.find(i => i.productId === p.id);

  if (item) item.quantity++;
  else {
    cart.push({
      productId: p.id,
      name: p.nombre,
      price: p.precio,
      quantity: 1,
      image: p.imagen
    });
  }

  saveCart();
}

export function removeFromCart(id: number) {
  cart = cart.filter(i => i.productId !== id);
  saveCart();
}

export function updateQuantity(id: number, qty: number) {
  const item = cart.find(i => i.productId === id);
  if (!item) return;

  item.quantity = Math.max(1, qty);
  saveCart();
}

export function clearCart() {
  cart = [];
  saveCart();
}

export function getCartTotal() {
  return cart.reduce((t, i) => t + i.price * i.quantity, 0);
}

/* =========================
   PRODUCTS
========================= */

export function setProducts(data: Product[]) {
  products = data;
  saveProducts();
}