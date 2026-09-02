import axios from "axios";

import { API_BASE_URL } from "../config/apiConfig";
import keycloak from "../config/keycloak";

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json"
    }
});

apiClient.interceptors.request.use(
    async (config) => {

        try {

            if (keycloak.authenticated) {

                await keycloak.updateToken(30);

                config.headers.Authorization =
                    `Bearer ${keycloak.token}`;
            }

        } catch (error) {

            console.error(
                "Failed to refresh Keycloak token:",
                error
            );
        }

        return config;
    },

    (error) => {
        return Promise.reject(error);
    }
);

export default apiClient;