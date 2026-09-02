import { useEffect, useState } from "react";
import { getLoans } from "../services/loanService";

function Loans() {

    const [loans, setLoans] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    useEffect(() => {

        const loadLoans = async () => {

            try {

                setLoading(true);

                const data = await getLoans();

                console.log("Loan API response:", data);

                // API returns a paginated response.
                // Actual loan records are inside data.content.
                if (data && Array.isArray(data.content)) {

                    setLoans(data.content);

                } else {

                    setLoans([]);

                }

            } catch (error) {

                console.error("Failed to load loans:", error);

                setError("Unable to load loan information.");

            } finally {

                setLoading(false);

            }
        };

        loadLoans();

    }, []);


    // Loading state
    if (loading) {

        return (
            <div>
                <h2>Loading loans...</h2>
            </div>
        );

    }


    // Error state
    if (error) {

        return (
            <div>
                <h2>{error}</h2>
            </div>
        );

    }


    return (
        <div>

            <h1>Loans</h1>

            <p>
                Your loan information retrieved from the banking backend.
            </p>


            {/* No loans */}
            {loans.length === 0 ? (

                <p>No loans found.</p>

            ) : (

                <div>

                    {loans.map((loan) => (

                        <div
                            key={loan.loanId}
                            style={{
                                background: "#ffffff",
                                padding: "20px",
                                marginTop: "20px",
                                borderRadius: "10px"
                            }}
                        >

                            <h2>Loan Details</h2>


                            <p>
                                <strong>Loan ID:</strong>{" "}
                                {loan.loanId}
                            </p>


                            <p>
                                <strong>Customer ID:</strong>{" "}
                                {loan.customerId}
                            </p>


                            <p>
                                <strong>Account ID:</strong>{" "}
                                {loan.accountId}
                            </p>


                            <p>
                                <strong>Loan Number:</strong>{" "}
                                {loan.loanNumber}
                            </p>


                            <p>
                                <strong>Loan Type:</strong>{" "}
                                {loan.loanType}
                            </p>


                            <p>
                                <strong>Principal Amount:</strong>{" "}
                                ₹{loan.principalAmount}
                            </p>


                            <p>
                                <strong>Outstanding Amount:</strong>{" "}
                                ₹{loan.outstandingAmount}
                            </p>


                            <p>
                                <strong>Interest Rate:</strong>{" "}
                                {loan.interestRate}%
                            </p>


                            <p>
                                <strong>Tenure:</strong>{" "}
                                {loan.tenureMonths} months
                            </p>


                            <p>
                                <strong>Status:</strong>{" "}
                                {loan.status}
                            </p>


                            <p>
                                <strong>Created At:</strong>{" "}
                                {loan.createdAt}
                            </p>


                            <p>
                                <strong>Updated At:</strong>{" "}
                                {loan.updatedAt}
                            </p>

                        </div>

                    ))}

                </div>

            )}

        </div>
    );
}

export default Loans;