export function loadCart() {

    document.querySelector<HTMLDivElement>("#app")!.innerHTML = `

        <h1>Carrito</h1>

        <p>Tu carrito está vacío.</p>

        <button onclick="location.hash='store'">
            Volver a la tienda
        </button>

    `;

}