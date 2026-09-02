import { Bell, UserCircle } from "lucide-react";

function Navbar() {

    return (
        <header className="navbar">

            <div>
                <h2>Banking Dashboard</h2>
            </div>

            <div className="navbar-right">

                <Bell size={21} />

                <div className="user-profile">
                    <UserCircle size={28} />
                    <span>Rohith</span>
                </div>

            </div>

        </header>
    );
}

export default Navbar;