import apiClient from "../services/apiClient";

export const sendChatMessage = async (message) => {

    try {

        const response = await apiClient.post(
            "/api/ai/chat",
            {
                message: message
            }
        );

        return response.data;

    } catch (error) {

        console.error("AI Chat API error:", error);

        throw error;
    }
};