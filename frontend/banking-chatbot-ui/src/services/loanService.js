import apiClient from "./apiClient";

export const getLoanById = async (loanId) => {
    const response = await apiClient.get(`/api/loans/${loanId}`);
    return response.data;
};

export const getLoans = async () => {
    const response = await apiClient.get("/api/loans");
       console.log("Loan API response:", response.data);
    return response.data;
};