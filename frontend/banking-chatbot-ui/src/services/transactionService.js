import apiClient from "./apiClient";

export const getTransactionsByAccount = async (
    accountId,
    page = 0,
    size = 10
) => {

    const response = await apiClient.get(
        `/api/transactions/account/${accountId}`,
        {
            params: {
                page,
                size
            }
        }
    );

    console.log("Transaction API response:", response.data);

    return response.data;
};