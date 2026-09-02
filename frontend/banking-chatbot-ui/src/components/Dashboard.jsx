import {
    Wallet,
    Landmark,
    ArrowLeftRight
} from "lucide-react";

function Dashboard() {

    return (
        <div className="dashboard">

            <div className="welcome-section">
                <h1>Welcome back, Rohith 👋</h1>
                <p>
                    Here's what's happening with your banking accounts.
                </p>
            </div>

            <div className="dashboard-cards">

                <div className="dashboard-card">

                    <div className="card-icon">
                        <Wallet size={24} />
                    </div>

                    <div>
                        <p>Total Balance</p>
                        <h2>₹85,420.00</h2>
                    </div>

                </div>

                <div className="dashboard-card">

                    <div className="card-icon">
                        <Landmark size={24} />
                    </div>

                    <div>
                        <p>Active Loans</p>
                        <h2>₹2,50,000</h2>
                    </div>

                </div>

                <div className="dashboard-card">

                    <div className="card-icon">
                        <ArrowLeftRight size={24} />
                    </div>

                    <div>
                        <p>Recent Transactions</p>
                        <h2>5</h2>
                    </div>

                </div>

            </div>

            <div className="ai-card">

                <h2>🤖 Banking AI Assistant</h2>

                <p>
                    Ask questions about your accounts, loans,
                    transactions, or banking services.
                </p>

                <button>
                    Start Conversation
                </button>

            </div>

        </div>
    );
}

export default Dashboard;