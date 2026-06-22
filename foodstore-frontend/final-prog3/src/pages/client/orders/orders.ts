import "./orders.css";

export function loadOrders() {

    const app = document.querySelector<HTMLDivElement>("#app")!;

    const pedidos =
        JSON.parse(localStorage.getItem("orders") || "[]");

    if (pedidos.length === 0) {

        app.innerHTML = `
            <div class="orders-container">

                <h1>📦 Mis Pedidos</h1>

                <div class="empty-orders">
                    <p>Aún no realizaste ningún pedido.</p>
                </div>

            </div>
        `;

        return;
    }

    app.innerHTML = `
        <div class="orders-container">

            <h1>📦 Historial de Pedidos</h1>

            ${pedidos.map((pedido: any, index: number) => `

                <div class="order-card">

                    <div class="order-header">

                        <div>
                            <div class="order-id">
                                Pedido #${index + 1}
                            </div>

                            <div class="order-date">
                                ${new Date(pedido.fecha).toLocaleString()}
                            </div>
                        </div>

                        <div class="order-status">
                            Entregado
                        </div>

                    </div>

                    <div class="order-total">
                        Total abonado: $${pedido.total}
                    </div>

                    <h4 class="products-title">
                        🛒 Productos comprados
                    </h4>

                    <ul class="order-products">

                        ${pedido.detalles.map((detalle: any) => `

                            <li>

                                <div>
                                    <strong>${detalle.nombre}</strong>
                                    <span>
                                        Cantidad: ${detalle.cantidad}
                                    </span>
                                </div>

                                <div class="subtotal">
                                    $${detalle.subtotal}
                                </div>

                            </li>

                        `).join("")}

                    </ul>

                </div>

            `).join("")}

        </div>
    `;
}