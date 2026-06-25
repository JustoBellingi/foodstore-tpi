import "./admin.css";
import { getProducts } from "../../utils/products";

export async function loadAdmin() {

  const app = document.querySelector<HTMLDivElement>("#app")!;
  let productos = await getProducts();
  const cache = localStorage.getItem("admin-products");

  if (cache) {
    productos = JSON.parse(cache);
  }

  // función para guardar cambios del admin
  const save = (data: any[]) => {
    localStorage.setItem("admin-products", JSON.stringify(data));
  };

  app.innerHTML = `
    <div class="admin-container">

      <h1>👑 Panel de Administración</h1>

      <div class="admin-card">

        <h2> Productos</h2>

        <table class="admin-table">

          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Precio</th>
              <th>Stock</th>
              <th>Categoría</th>
              <th>Acciones</th>
            </tr>
          </thead>

          <tbody>
            ${productos.map((p: any) => `
              <tr>
                <td>${p.id}</td>
                <td>${p.nombre}</td>
                <td>$${p.precio}</td>
                <td>${p.stock}</td>
                <td>${p.categoria}</td>
                <td>
                  <button
                    class="delete-btn"
                    onclick="window.deleteProduct(${p.id})"
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            `).join("")}
          </tbody>

        </table>

      </div>

    </div>
  `;

  // DELETE 
  (window as any).deleteProduct = (id: number) => {

    let data = JSON.parse(localStorage.getItem("admin-products") || "[]");

    // si no hay cache todavía, arrancar desde base
    if (!data.length) {
      data = [...productos];
    }

    data = data.filter((p: any) => p.id !== id);

    save(data);

    loadAdmin(); // recarga vista
  };
}