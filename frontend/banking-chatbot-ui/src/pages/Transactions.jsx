import { useEffect, useState } from "react";
import { getTransactionsByAccount } from "../services/transactionService";

function Transactions() {

    // Currently using Account ID 1.
    // Later we will get this dynamically from the logged-in user's account.
    const accountId = 1;

    const [transactions, setTransactions] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);

    useEffect(() => {

        const loadTransactions = async () => {

            try {

                setLoading(true);
                setError("");

                const data = await getTransactionsByAccount(
                    accountId,
                    page,
                    10
                );

                console.log("Transactions received:", data);

                setTransactions(data.content || []);
                setTotalPages(data.totalPages || 0);

            } catch (error) {

                console.error(
                    "Failed to load transactions:",
                    error
                );

                setError(
                    "Unable to load transaction information."
                );

            } finally {

                setLoading(false);

            }
        };

        loadTransactions();

    }, [accountId, page]);

    if (loading) {

        return (
            <div>
                <h2>Loading transactions...</h2>
            </div>
        );

    }

    if (error) {

        return (
            <div>
                <h2>{error}</h2>
            </div>
        );

    }

    return (

        <div>

            <h1>Transactions</h1>

            <p>
                Transaction history for Account ID {accountId}.
            </p>

            {transactions.length === 0 ? (

                <p>No transactions found.</p>

            ) : (

                <div>

                    {transactions.map((transaction) => (

                        <div
                            key={transaction.transactionId}
                            style={{
                                background: "#ffffff",
                                padding: "20px",
                                marginTop: "20px",
                                borderRadius: "10px"
                            }}
                        >

                            <h2>Transaction Details</h2>

                            <p>
                                <strong>
                                    Transaction ID:
                                </strong>{" "}
                                {transaction.transactionId}
                            </p>

                            <p>
                                <strong>
                                    Reference:
                                </strong>{" "}
                                {transaction.transactionReference}
                            </p>

                            <p>
                                <strong>
                                    Account ID:
                                </strong>{" "}
                                {transaction.accountId}
                            </p>

                            <p>
                                <strong>
                                    Customer ID:
                                </strong>{" "}
                                {transaction.customerId}
                            </p>

                            <p>
                                <strong>
                                    Transaction Type:
                                </strong>{" "}
                                {transaction.transactionType}
                            </p>

                            <p>
                                <strong>
                                    Amount:
                                </strong>{" "}
                                ₹{transaction.amount}
                            </p>

                            <p>
                                <strong>
                                    Balance Before:
                                </strong>{" "}
                                ₹{transaction.balanceBefore}
                            </p>

                            <p>
                                <strong>
                                    Balance After:
                                </strong>{" "}
                                ₹{transaction.balanceAfter}
                            </p>

                            <p>
                                <strong>
                                    Status:
                                </strong>{" "}
                                {transaction.status}
                            </p>

                            <p>
                                <strong>
                                    Description:
                                </strong>{" "}
                                {transaction.description}
                            </p>

                            <p>
                                <strong>
                                    Created At:
                                </strong>{" "}
                                {transaction.createdAt}
                            </p>

                            <p>
                                <strong>
                                    Updated At:
                                </strong>{" "}
                                {transaction.updatedAt}
                            </p>

                        </div>

                    ))}

                </div>

            )}

            {/* Pagination */}

            {totalPages > 1 && (

                <div
                    style={{
                        marginTop: "20px",
                        display: "flex",
                        gap: "10px",
                        alignItems: "center"
                    }}
                >

                    <button
                        disabled={page === 0}
                        onClick={() => setPage(page - 1)}
                    >
                        Previous
                    </button>

                    <span>
                        Page {page + 1} of {totalPages}
                    </span>

                    <button
                        disabled={page >= totalPages - 1}
                        onClick={() => setPage(page + 1)}
                    >
                        Next
                    </button>

                </div>

            )}

        </div>
    );
}

export default Transactions;