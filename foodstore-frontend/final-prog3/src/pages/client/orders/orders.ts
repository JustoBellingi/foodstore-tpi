export function loadOrders() {

    const app = document.querySelector<HTMLDivElement>("#app")!;

    const pedidos =
        JSON.parse(localStorage.getItem("orders") || "[]");

    if (pedidos.length === 0) {

        app.innerHTML = `
            <div class="orders-container">

                <h1>📦 Mis Pedidos</h1>

                <p>Aún no realizaste ningún pedido.</p>

            </div>
        `;

        return;
    }

    app.innerHTML = `
        <div class="orders-container">

            <h1>📦 Mis Pedidos</h1>

            ${pedidos.map((pedido: any, index: number) => `

                <div class="order-card">

                    <div class="order-header">

                        <div class="order-id">
                            Pedido #${index + 1}
                        </div>

                        <div class="order-date">
                            ${new Date(pedido.fecha).toLocaleString()}
                        </div>

                    </div>

                    <div class="order-total">
                        Total: $${pedido.total}
                    </div>

                    <h4>🛒 Productos</h4>

                    <ul class="order-products">

                        ${pedido.detalles.map((detalle: any) => `

                            <li>
                                <span>
                                    ${detalle.nombre} x${detalle.cantidad}
                                </span>

                                <strong>
                                    $${detalle.subtotal}
                                </strong>
                            </li>

                        `).join("")}

                    </ul>

                </div>

            `).join("")}

        </div>
    `;
}