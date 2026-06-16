import { navigate } from "../../../utils/router";

export function loadLogin() {
  document.querySelector<HTMLDivElement>('#app')!.innerHTML = `
    <div style="max-width:300px;margin:auto">
      <h2>Login</h2>

      <input id="email" placeholder="Email" />
      <br/><br/>

      <input id="password" type="password" placeholder="Password" />
      <br/><br/>

      <button id="loginBtn">Ingresar</button>

      <p id="error" style="color:red"></p>
    </div>
  `;

  document.getElementById('loginBtn')!.addEventListener('click', async () => {
    const res = await fetch('/src/data/usuarios.json');
    const users = await res.json();

    const email = (document.getElementById('email') as HTMLInputElement).value;
    const password = (document.getElementById('password') as HTMLInputElement).value;

    const user = users.find((u: any) => u.email === email && u.password === password);

    if (!user) {
      document.getElementById('error')!.innerText = "Credenciales inválidas";
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