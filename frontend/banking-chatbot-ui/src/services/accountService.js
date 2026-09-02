import apiClient from "./apiClient";

export const getAccountById = async (accountId) => {

    const response = await apiClient.get(
        `/api/accounts/${accountId}`
    );

    return response.data;
};