import os

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
def get_account_balance(
    account_id: int,
    runtime: ToolRuntime[BankingContext | dict, dict]
) -> str:
    """
    Get the current balance for a bank account.

    Use this tool when the user asks about:
    - account balance
    - current balance
    - available balance
    - money in the account

    Args:
        account_id: The bank account ID.
    """

    access_token = _get_access_token(runtime)

    if not access_token:
        return "Unable to access banking information."

    url = f"{GATEWAY_BASE_URL}/api/accounts/{account_id}"

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

        account = response.json()

        balance = account.get("balance")

        if balance is None:
            return "Unable to retrieve the account balance."

        return (
            f"Account {account_id} current balance is "
            f"Rs. {balance}."
        )

    except httpx.HTTPStatusError as error:

        if error.response.status_code == 401:
            return (
                "Unable to retrieve account information. "
                "Authentication failed."
            )

        if error.response.status_code == 403:
            return (
                "You are not authorized to access this account."
            )

        return (
            "Unable to retrieve account information "
            "from the banking service."
        )

    except httpx.RequestError:

        return (
            "The banking service is currently unavailable. "
            "Please try again later."
        )

    except Exception as error:

        print(f"Account tool error: {error}")

        return (
            "An unexpected error occurred while "
            "retrieving account information."
        )
