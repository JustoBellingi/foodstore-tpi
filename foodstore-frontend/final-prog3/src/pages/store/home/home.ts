import { navigate } from "../../../utils/router";

/* =========================
   IMÁGENES
========================= */

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
  "Zanahoria.jpg": new URL("../../../assets/productos/Zanahoria.jpg", import.meta.url).href,
  "Oregano.jpg": new URL("../../../assets/productos/Oregano.jpg", import.meta.url).href,
  "Rucula.jpg": new URL("../../../assets/productos/Rucula.jpg", import.meta.url).href,
  "Garbanzos.jpg": new URL("../../../assets/productos/Garbanzos.jpg", import.meta.url).href,
  "Lentejas.jpg": new URL("../../../assets/productos/Lentejas.jpg", import.meta.url).href,
  "Poroto.jpg": new URL("../../../assets/productos/Poroto.jpg", import.meta.url).href,
  "Curry.jpg": new URL("../../../assets/productos/Curry.jpg", import.meta.url).href,
  "pimienta.jpg": new URL("../../../assets/productos/pimienta.jpg", import.meta.url).href
};

/* =========================
   LOAD STORE
========================= */

export async function loadStore() {
  const response = await fetch("/src/data/productos.json");
const productos = await response.json();
  const app = document.querySelector<HTMLDivElement>("#app")!;

  app.innerHTML = `
    <header class="navbar">
      <div class="logo">🥬 FoodStore</div>
      <nav>
        <button id="inicio">Inicio</button>
        <button id="productos">Productos</button>
        <button id="pedidos">Pedidos</button>
        <button id="carrito">Carrito</button>
        <button id="logout">Logout</button>
      </nav>
    </header>

    <main class="layout">
      <aside class="sidebar">
        <h3>Categorías</h3>
        <button class="filter-item" data-cat="todos">Todos</button>
        <button class="filter-item" data-cat="frutas">Frutas</button>
        <button class="filter-item" data-cat="verduras">Verduras</button>
        <button class="filter-item" data-cat="legumbres">Legumbres</button>
        <button class="filter-item" data-cat="condimentos">Condimentos</button>
      </aside>

      <section class="content">
        <h1>🥬 Productos</h1>

        <input
          type="text"
          id="search-input"
          placeholder="Buscar productos..."
          class="search-input"
        />

        <div class="grid" id="productos-grid"></div>
      </section>
    </main>
  `;

  /* =========================
     NAV
  ========================= */

  document.getElementById("inicio")
    ?.addEventListener("click", () => navigate("store"));

  document.getElementById("productos")
    ?.addEventListener("click", () => navigate("store"));

  document.getElementById("pedidos")
    ?.addEventListener("click", () => navigate("orders"));

  document.getElementById("carrito")
    ?.addEventListener("click", () => navigate("cart"));

  document.getElementById("logout")
    ?.addEventListener("click", () => {
      localStorage.removeItem("user");
      navigate("login");
    });

  /* =========================
     RENDER PRODUCTS
  ========================= */

  const contenedor = document.getElementById("productos-grid")!;

  function renderProductos(lista: any[]) {
    contenedor.innerHTML = "";

    lista.forEach((producto) => {
      contenedor.innerHTML += `
        <div class="card" onclick="window.openDetail(${producto.id})">
          <img src="${imagenes[producto.imagen]}" alt="${producto.nombre}" />
          <h3>${producto.nombre}</h3>
          <p>${producto.descripcion}</p>
          <h4>$${producto.precio}</h4>
          <p><strong>Stock:</strong> ${producto.stock}</p>

          <button onclick='window.addProduct(${JSON.stringify(producto)})'>
            Agregar al carrito
          </button>
        </div>
      `;
    });
  }

  renderProductos(productos);

  /* =========================
     SEARCH
  ========================= */

  const searchInput = document.getElementById("search-input") as HTMLInputElement;

  searchInput.addEventListener("input", () => {
    const texto = searchInput.value.toLowerCase();

    const filtrados = productos.filter((p: any) =>
      p.nombre.toLowerCase().includes(texto) ||
      p.descripcion.toLowerCase().includes(texto)
    );

    renderProductos(filtrados);
  });

  /* =========================
     FILTERS
  ========================= */

  document.querySelectorAll(".filter-item").forEach((btn) => {
    btn.addEventListener("click", (e) => {
      const cat = (e.target as HTMLElement).dataset.cat;

      if (cat === "todos") {
        renderProductos(productos);
        return;
      }

      const filtrados = productos.filter(
        (p: any) => p.categoria === cat
      );

      renderProductos(filtrados);
    });
  });
}