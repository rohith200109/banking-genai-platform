from langchain_google_genai import ChatGoogleGenerativeAI
from langgraph.prebuilt import create_react_agent
from app.tools.rag_tools import search_banking_knowledge
from app.config.settings import (
    GEMINI_API_KEY,
    GEMINI_MODEL
)

from app.agents.context import BankingContext

from app.tools.account_tools import (
    get_account_balance
)

from app.tools.transaction_tools import (
    get_recent_transactions
)

from app.tools.loan_tools import (
    get_customer_loans
)


llm = ChatGoogleGenerativeAI(
    model=GEMINI_MODEL,
    google_api_key=GEMINI_API_KEY
)


tools = [
    get_account_balance,
    get_recent_transactions,
    get_customer_loans,
    search_banking_knowledge
]


banking_agent = create_react_agent(
    model=llm,
    tools=tools,
    context_schema=BankingContext
)