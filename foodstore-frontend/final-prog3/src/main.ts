import { getRoute } from './utils/router';
import "./style.css";
import { loadLogin } from './pages/auth/login/login.ts';
import { loadStore } from './pages/store/home/home.ts';
import { loadCart } from "./pages/store/cart/cart";
import { loadOrders } from "./pages/client/orders/orders";


function render() {

    const route = getRoute();

    switch(getRoute()){

    case "login":
        loadLogin();
        break;

    case "store":
        loadStore();
        break;

    case "cart":
        loadCart();
        break;

    case "orders":
        loadOrders();
        break;

    default:
        loadLogin();
}
}

window.addEventListener("hashchange", render);

render();