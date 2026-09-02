import { useEffect, useState } from "react";
import { getAccountById } from "../services/accountService";

function Accounts() {

    const [account, setAccount] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {

        const loadAccount = async () => {

            try {

                const data = await getAccountById(7);

                setAccount(data);

            } catch (error) {

                console.error("Failed to load account:", error);

                setError(
                    "Unable to load account information."
                );

            } finally {

                setLoading(false);
            }
        };

        loadAccount();

    }, []);

    if (loading) {
        return <h2>Loading account...</h2>;
    }

    if (error) {
        return <h2>{error}</h2>;
    }

    return (

        <div>

            <h1>Accounts</h1>

            <p>
                Account information retrieved from the banking backend.
            </p>

            {account && (

                <div className="account-card">

                    <h2>Account Details</h2>

                    <p>
                        <strong>Account ID:</strong>{" "}
                        {account.accountId}
                    </p>

                    <p>
                        <strong>Customer ID:</strong>{" "}
                        {account.customerId}
                    </p>

                    <p>
                        <strong>Account Number:</strong>{" "}
                        {account.accountNumber}
                    </p>

                    <p>
                        <strong>Account Type:</strong>{" "}
                        {account.accountType}
                    </p>

                    <p>
                        <strong>Balance:</strong>{" "}
                        ₹{account.balance}
                    </p>

                </div>

            )}

        </div>
    );
}

export default Accounts;