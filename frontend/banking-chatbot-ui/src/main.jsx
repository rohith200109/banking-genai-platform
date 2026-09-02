import { StrictMode } from "react";
import { createRoot } from "react-dom/client";

import App from "./App.jsx";
import keycloak from "./config/keycloak.js";

keycloak.init({
    onLoad: "login-required",
    pkceMethod: "S256",
    checkLoginIframe: false
})
.then((authenticated) => {

    if (!authenticated) {
        console.log("User is not authenticated");
        return;
    }

    console.log("Keycloak authentication successful");
    console.log("Username:", keycloak.tokenParsed?.preferred_username);

    createRoot(document.getElementById("root")).render(
        <StrictMode>
            <App />
        </StrictMode>
    );
})
.catch((error) => {

    console.error(
        "Keycloak initialization failed:",
        error
    );

});