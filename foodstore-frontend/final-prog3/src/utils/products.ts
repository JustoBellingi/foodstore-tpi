export async function getProducts() {
  const response = await fetch("/src/data/productos.json");
  return await response.json();
}