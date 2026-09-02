import { useState } from "react";
import { sendChatMessage } from "../services/aiService";

function AIChat() {

    const [message, setMessage] = useState("");
    const [messages, setMessages] = useState([]);
    const [loading, setLoading] = useState(false);

    const handleSend = async () => {

        const trimmedMessage = message.trim();

        if (!trimmedMessage || loading) {
            return;
        }

        // Add user message to chat
        setMessages((previousMessages) => [
            ...previousMessages,
            {
                role: "user",
                content: trimmedMessage
            }
        ]);

        setMessage("");
        setLoading(true);

        try {

            const response = await sendChatMessage(trimmedMessage);

            console.log("AI response:", response);

            setMessages((previousMessages) => [
                ...previousMessages,
                {
                    role: "assistant",
                    content: response.response
                }
            ]);

        } catch (error) {

            console.error("Failed to get AI response:", error);

            setMessages((previousMessages) => [
                ...previousMessages,
                {
                    role: "assistant",
                    content: "Sorry, I was unable to process your request."
                }
            ]);

        } finally {

            setLoading(false);
        }
    };

    const handleKeyDown = (event) => {

        if (event.key === "Enter" && !event.shiftKey) {

            event.preventDefault();

            handleSend();
        }
    };

    return (
        <div className="ai-chat-page">

            <div className="ai-chat-header">

                <div>
                    <h1>AI Assistant</h1>

                    <p>
                        Ask questions about your accounts, loans,
                        transactions, and banking services.
                    </p>
                </div>

            </div>


            <div className="chat-container">

                <div className="chat-messages">

                    {messages.length === 0 && (

                        <div className="chat-welcome">

                            <div className="chat-bot-icon">
                                🤖
                            </div>

                            <h2>
                                Welcome to Banking AI
                            </h2>

                            <p>
                                How can I help you today?
                            </p>

                            <div className="suggestions">

                                <button
                                    onClick={() =>
                                        setMessage(
                                            "What is my account balance?"
                                        )
                                    }
                                >
                                    What is my account balance?
                                </button>

                                <button
                                    onClick={() =>
                                        setMessage(
                                            "Show my recent transactions"
                                        )
                                    }
                                >
                                    Show my recent transactions
                                </button>

                                <button
                                    onClick={() =>
                                        setMessage(
                                            "What loans do I have?"
                                        )
                                    }
                                >
                                    What loans do I have?
                                </button>

                            </div>

                        </div>
                    )}


                    {messages.map((chatMessage, index) => (

                        <div
                            key={index}
                            className={`chat-message ${
                                chatMessage.role === "user"
                                    ? "user-message"
                                    : "assistant-message"
                            }`}
                        >

                            <div className="message-icon">

                                {chatMessage.role === "user"
                                    ? "👤"
                                    : "🤖"}

                            </div>

                            <div className="message-content">

                                {chatMessage.content}

                            </div>

                        </div>

                    ))}


                    {loading && (

                        <div className="chat-message assistant-message">

                            <div className="message-icon">
                                🤖
                            </div>

                            <div className="message-content">
                                Thinking...
                            </div>

                        </div>

                    )}

                </div>


                <div className="chat-input-container">

                    <textarea
                        value={message}
                        onChange={(event) =>
                            setMessage(event.target.value)
                        }
                        onKeyDown={handleKeyDown}
                        placeholder="Ask Banking AI something..."
                        rows="2"
                        disabled={loading}
                    />

                    <button
                        onClick={handleSend}
                        disabled={!message.trim() || loading}
                    >
                        {loading ? "..." : "Send"}
                    </button>

                </div>

            </div>

        </div>
    );
}

export default AIChat;