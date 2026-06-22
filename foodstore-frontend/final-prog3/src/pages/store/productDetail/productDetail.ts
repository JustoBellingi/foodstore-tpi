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
   LOAD PRODUCT DETAIL
========================= */

export async function loadProductDetail(id:number) {

    const response =
        await fetch("/src/data/productos.json");

    const productos = await response.json();

    const producto =
        productos.find((p:any) => p.id === id);

    const app =
        document.querySelector<HTMLDivElement>("#app")!;

    if(!producto){

        app.innerHTML = `
            <h1>Producto no encontrado</h1>
        `;

        return;
    }

    app.innerHTML = `

        <div class="detail-container">

            <button id="volver">
                ← Volver
            </button>

            <div class="detail-card">

                <img
                    src="${imagenes[producto.imagen]}"
                    alt="${producto.nombre}"
                >

                <h1>${producto.nombre}</h1>

                <p>${producto.descripcion}</p>

                <h2>$${producto.precio}</h2>

                <p>
                    <strong>Stock:</strong>
                    ${producto.stock}
                </p>

                <button
                    onclick='window.addProduct(${JSON.stringify(producto)})'
                >
                    Agregar al carrito
                </button>

            </div>

        </div>
    `;

    document
        .getElementById("volver")
        ?.addEventListener("click", () => {
            navigate("store");
        });
}