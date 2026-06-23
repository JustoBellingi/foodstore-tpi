import "./orders.css";

export function loadOrders() {

    const app = document.querySelector("#app") as HTMLDivElement;

    const pedidos = JSON.parse(localStorage.getItem("orders") || "[]");

    if (!pedidos.length) {
        app.innerHTML = `
            <h1>📦 Mis pedidos</h1>
            <p>No hiciste compras todavía.</p>
        `;
        return;
    }

    app.innerHTML = `
        <h1>📦 Mis pedidos</h1>

        <div class="orders-container">
            ${pedidos.map((pedido: any, index: number) => `
                <div class="order-card">

                    <h3>Pedido #${index + 1}</h3>

                    <p>Fecha: ${new Date(pedido.fecha).toLocaleString()}</p>
                    <p>Estado: ${pedido.estado}</p>
                    <p>Total: $${pedido.total}</p>

                    <h4>Productos</h4>

                    <ul>
                        ${pedido.detalles.map((d: any) => `
                            <li>
                                ${d.nombre} x${d.cantidad} - $${d.subtotal}
                            </li>
                        `).join("")}
                    </ul>

                </div>
            `).join("")}
        </div>
    `;
}