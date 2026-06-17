import "./login.css";
import { navigate } from "../../../utils/router";

export function loadLogin() {
  document.querySelector<HTMLDivElement>("#app")!.innerHTML = `
    <div class="login-container">

      <div class="login-card">

        <h1>🥬 FoodStore</h1>
        <p>Frutas y verduras frescas al mejor precio</p>

        <label for="email">Correo electrónico</label>
        <input
          id="email"
          type="email"
          placeholder="Ingresá tu correo"
        >

        <label for="password">Contraseña</label>
        <input
          id="password"
          type="password"
          placeholder="Ingresá tu contraseña"
        >

        <button id="loginBtn">
          Ingresar
        </button>

        <p id="error"></p>

      </div>

    </div>
  `;

  document.getElementById("loginBtn")!.addEventListener("click", async () => {

    const res = await fetch("/src/data/usuarios.json");
    const users = await res.json();

    const email = (document.getElementById("email") as HTMLInputElement).value;
    const password = (document.getElementById("password") as HTMLInputElement).value;

    const user = users.find(
      (u: any) => u.email === email && u.password === password
    );

    if (!user) {
      document.getElementById("error")!.textContent = "Credenciales inválidas";
      return;
    }

    localStorage.setItem("user", JSON.stringify(user));

    if (user.role === "ADMIN") {
      navigate("admin");
    } else {
      navigate("store");
    }
  });
}