import {
    LayoutDashboard,
    WalletCards,
    Landmark,
    ArrowLeftRight,
    Bot
} from "lucide-react";

import { NavLink } from "react-router-dom";

function Sidebar() {

    return (
        <aside className="sidebar">

            <div className="sidebar-logo">
                🏦
                <span>Banking AI</span>
            </div>

            <nav className="sidebar-menu">

                <NavLink to="/">
                    <LayoutDashboard size={20} />
                    <span>Dashboard</span>
                </NavLink>

                <NavLink to="/accounts">
                    <WalletCards size={20} />
                    <span>Accounts</span>
                </NavLink>

                <NavLink to="/loans">
                    <Landmark size={20} />
                    <span>Loans</span>
                </NavLink>

                <NavLink to="/transactions">
                    <ArrowLeftRight size={20} />
                    <span>Transactions</span>
                </NavLink>

                <NavLink to="/chat">
                    <Bot size={20} />
                    <span>AI Assistant</span>
                </NavLink>

            </nav>

        </aside>
    );
}

export default Sidebar;