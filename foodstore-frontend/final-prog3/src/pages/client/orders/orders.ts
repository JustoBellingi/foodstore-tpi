import "./orders.css";

export function loadOrders() {

    const app = document.querySelector<HTMLDivElement>("#app")!;

    const pedidos = JSON.parse(localStorage.getItem("orders") || "[]");

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
                                ${new Date(pedido.date).toLocaleString()}
                            </div>

                            <div class="order-payment">
                                💳 ${pedido.payment}
                            </div>

                        </div>

                        <div class="order-status">
                            ${pedido.status}
                        </div>

                    </div>

                    <div class="order-total">
                        Total abonado: $${pedido.total}
                    </div>

                    <h4 class="products-title">
                        🛒 Productos comprados
                    </h4>

                    <ul class="order-products">

                        ${pedido.details.map((d: any) => `

                            <li>

                                <div>
                                    <strong>${d.name}</strong>
                                    <span>Cantidad: ${d.quantity}</span>
                                </div>

                                <div class="subtotal">
                                    $${d.subtotal}
                                </div>

                            </li>

                        `).join("")}

                    </ul>

                </div>

            `).join("")}

        </div>
    `;
}