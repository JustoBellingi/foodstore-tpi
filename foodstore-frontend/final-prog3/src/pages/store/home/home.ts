import Arandanos from '../../../assets/productos/Arandanos.jpg';
import Banana from '../../../assets/productos/Banana.jpg';
import Frutilla from '../../../assets/productos/Frutilla.jpg';
import Mandarina from '../../../assets/productos/Mandarina.jpg';
import ManzanaV from '../../../assets/productos/ManzanaV.jpg';
import Melon from '../../../assets/productos/Melon.jpg';
import Morron from '../../../assets/productos/Morron.jpg';
import Palta from '../../../assets/productos/Palta.jpg';
import Zapallo from '../../../assets/productos/Zapallo.jpg';
import Zanahoria from '../../../assets/productos/Zanahoria.jpg';

const imagenes: Record<string, string> = {
  Arandanos,
  Banana,
  Frutilla,
  Mandarina,
  ManzanaV,
  Melon,
  Morron,
  Palta,
  Zapallo,
  Zanahoria
};

export async function loadStore() {
  const res = await fetch('/src/data/productos.json');
  const productos = await res.json();

  document.querySelector('#app')!.innerHTML = `
    <h1>🍎 Frutas y Verduras</h1>
    <div id="productos"></div>
  `;

  const container = document.getElementById('productos')!;

  container.innerHTML = productos.map((p: any) => card(p)).join('');
}

function card(p: any) {
  return `
    <div class="card">
      <img src="${imagenes[p.nombre]}" width="120" />
      <h3>${p.nombre}</h3>
      <p>${p.descripcion}</p>
      <b>$${p.precio}</b>
    </div>
  `;
}