import { getRoute } from './utils/router';

import { loadLogin } from './pages/auth/login/login.ts';
import { loadStore } from './pages/store/home/home.ts';


function render() {
  const route = getRoute();

  switch (route) {
    case "login":
      loadLogin();
      break;

    case "store":
      loadStore();
      break;

    default:
      loadLogin();
  }
}

window.addEventListener("hashchange", render);
render();