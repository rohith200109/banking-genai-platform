import os
from typing_extensions import runtime

import httpx

from langchain.tools import tool, ToolRuntime

from app.agents.context import BankingContext


GATEWAY_BASE_URL = os.getenv(
    "GATEWAY_BASE_URL",
    "http://localhost:8080"
)


def _get_access_token(
    runtime: ToolRuntime[BankingContext | dict, dict]
) -> str | None:

    context = runtime.context

    if isinstance(context, dict):
        return context.get("access_token")

    return getattr(context, "access_token", None)


@tool
def get_recent_transactions(
    account_id: int,
    runtime: ToolRuntime[BankingContext | dict, dict]
) -> str:
    """
    Get recent transactions for a bank account.

    Use this tool when the user asks about:
    - recent transactions
    - transaction history
    - deposits
    - withdrawals
    - transfers
    - account activity

    Args:
        account_id: The bank account ID.
    """
    print(
    f"Transaction tool called for account: {account_id}"
    )
    access_token = _get_access_token(runtime)

    if not access_token:
        return "Unable to access banking information."

    url = (
        f"{GATEWAY_BASE_URL}"
        f"/api/transactions/account/{account_id}"
        f"?page=0&size=10"
    )

    headers = {
        "Authorization": f"Bearer {access_token}",
        "Content-Type": "application/json"
    }

    try:

        response = httpx.get(
            url,
            headers=headers,
            timeout=10.0
        )

        response.raise_for_status()

        data = response.json()

        transactions = data.get("content", [])

        if not transactions:
            return (
                f"No transactions found for "
                f"account {account_id}."
            )

        result = []

        for transaction in transactions:

            result.append(
                f"Transaction ID: {transaction.get('transactionId')}\n"
                f"Reference: {transaction.get('transactionReference')}\n"
                f"Type: {transaction.get('transactionType')}\n"
                f"Amount: ₹{transaction.get('amount')}\n"
                f"Balance Before: ₹{transaction.get('balanceBefore')}\n"
                f"Balance After: ₹{transaction.get('balanceAfter')}\n"
                f"Status: {transaction.get('status')}\n"
                f"Description: {transaction.get('description')}\n"
                f"Date: {transaction.get('createdAt')}"
            )

        return "\n\n".join(result)

    except httpx.HTTPStatusError as error:

        if error.response.status_code == 401:
            return (
                "Unable to retrieve transaction information. "
                "Authentication failed."
            )

        if error.response.status_code == 403:
            return (
                "You are not authorized to access "
                "these transactions."
            )

        return (
            "Unable to retrieve transaction information "
            "from the banking service."
        )

    except httpx.RequestError:

        return (
            "The banking service is currently unavailable. "
            "Please try again later."
        )

    except Exception as error:

        print(f"Transaction tool error: {error}")

        return (
            "An unexpected error occurred while "
            "retrieving transaction information."
        )