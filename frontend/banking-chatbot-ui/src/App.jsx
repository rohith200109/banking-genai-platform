import { BrowserRouter, Routes, Route } from "react-router-dom";

import Sidebar from "./components/Sidebar";
import Navbar from "./components/Navbar";
import AIChat from "./pages/AIChat";

import Dashboard from "./pages/Dashboard";
import Accounts from "./pages/Accounts";
import Loans from "./pages/Loans";
import Transactions from "./pages/Transactions";
import Chat from "./pages/Chat";

import "./App.css";

function App() {

    return (
        <BrowserRouter>

            <div className="app">

                <Sidebar />

                <div className="main-section">

                    <Navbar />

                    <main className="content">

                        <Routes>

                        <Route path="/chat" element={<AIChat />} />
                            <Route
                                path="/"
                                element={<Dashboard />}
                            />

                            <Route
                                path="/accounts"
                                element={<Accounts />}
                            />

                            <Route
                                path="/loans"
                                element={<Loans />}
                            />

                            <Route
                                path="/transactions"
                                element={<Transactions />}
                            />

                            <Route
                                path="/chat"
                                element={<Chat />}
                            />

                        </Routes>

                    </main>

                </div>

            </div>

        </BrowserRouter>
    );
}

export default App;