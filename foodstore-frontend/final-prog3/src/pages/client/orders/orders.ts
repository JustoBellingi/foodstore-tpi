import "./orders.css";

export function loadOrders() {
  const app = document.querySelector("#app") as HTMLDivElement;

  const orders = JSON.parse(localStorage.getItem("orders") || "[]");

  if (!orders.length) {
    app.innerHTML = `
      <div class="orders-empty">
        <h1>No hay pedidos</h1>
        <p>Cuando realices compras van a aparecer acá.</p>
      </div>
    `;
    return;
  }

  app.innerHTML = `
    <div class="orders-container">
      <h1>Pedidos</h1>

      ${orders
        .map((o: any) => `
          <div class="order-card">

            <div class="order-header">
              <div>
                <h3>Pedido</h3>
                <p class="order-date">
                  ${new Date(o.fecha).toLocaleString()}
                </p>
              </div>

              <span class="order-status">
                ${o.estado ?? "PENDIENTE"}
              </span>
            </div>

            <p class="order-total">
              Total: $${o.total}
            </p>

            <div class="order-products">
              <h4>Productos</h4>

              ${o.detalles
                .map((i: any) => `
                  <div class="order-product-item">
                    <span>${i.nombre} x${i.cantidad}</span>
                    <strong>$${i.subtotal}</strong>
                  </div>
                `)
                .join("")}
            </div>

          </div>
        `)
        .join("")}
    </div>
  `;
}