import axios from "axios";
import keycloak from "../config/keycloak";
export const API_BASE_URL = "http://localhost:8080";
const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        "Content-Type": "application/json"
    }
});

apiClient.interceptors.request.use(
    async (config) => {

        if (keycloak.authenticated) {

            await keycloak.updateToken(30);

            config.headers.Authorization =
                `Bearer ${keycloak.token}`;
        }

        return config;
    },
    (error) => Promise.reject(error)
);

export default apiClient;