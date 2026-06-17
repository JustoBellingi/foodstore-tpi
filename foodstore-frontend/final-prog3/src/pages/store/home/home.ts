import { navigate } from "../../../utils/router";

const imagenes: Record<string, string> = {
  "Arandanos.jpg": new URL("../../../assets/productos/Arandanos.jpg", import.meta.url).href,
  "Banana.jpg": new URL("../../../assets/productos/Banana.jpg", import.meta.url).href,
  "Frutilla.jpg": new URL("../../../assets/productos/Frutilla.jpg", import.meta.url).href,
  "Mandarina.jpg": new URL("../../../assets/productos/Mandarina.jpg", import.meta.url).href,
  "ManzanaV.jpg": new URL("../../../assets/productos/ManzanaV.jpg", import.meta.url).href,
  "Melon.jpg": new URL("../../../assets/productos/Melon.jpg", import.meta.url).href,
  "Morron.jpg": new URL("../../../assets/productos/Morron.jpg", import.meta.url).href,
  "Palta.jpg": new URL("../../../assets/productos/Palta.jpg", import.meta.url).href,
  "Zapallo.jpg": new URL("../../../assets/productos/Zapallo.jpg", import.meta.url).href,
  "Zanahoria.jpg": new URL("../../../assets/productos/Zanahoria.jpg", import.meta.url).href
};

export async function loadStore() {
  const respuesta = await fetch("/src/data/productos.json");
  const productos = await respuesta.json();

  document.querySelector<HTMLDivElement>("#app")!.innerHTML = `

<header class="navbar">

    <div class="logo">
        🥬 FoodStore
    </div>

    <nav>

        <a href="#">🏠 Inicio</a>

        <a href="#">🍎 Productos</a>

        <a href="#">📦 Pedidos</a>

        <a href="#">🛒 Carrito</a>

        <button id="logoutBtn">
            Cerrar sesión
        </button>

    </nav>

</header>

<main>

    <h1>Frutas y Verduras</h1>

    <div class="grid" id="productos"></div>

</main>

`;
document.getElementById("inicio")?.addEventListener("click", () => {
    navigate("store");
});

document.getElementById("productos")?.addEventListener("click", () => {
    navigate("store");
});

document.getElementById("pedidos")?.addEventListener("click", () => {
    navigate("orders");
});

document.getElementById("carrito")?.addEventListener("click", () => {
    navigate("cart");
});

document.getElementById("logout")?.addEventListener("click", () => {
    localStorage.removeItem("user");
    navigate("login");
});
  const contenedor = document.getElementById("productos")!;

  productos.forEach((producto: any) => {
    contenedor.innerHTML += `
      <div class="card">
        <img src="${imagenes[producto.imagen]}" alt="${producto.nombre}" />

        <h3>${producto.nombre}</h3>

        <p>${producto.descripcion}</p>

        <h4>$${producto.precio}</h4>

        <p>Stock: ${producto.stock}</p>

        <button>Agregar al carrito</button>
      </div>
    `;
  });
}