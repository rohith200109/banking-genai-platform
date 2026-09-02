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
def get_customer_loans(
    runtime: ToolRuntime[BankingContext | dict, dict]
) -> str:
    """
    Get the customer's loan information.

    Use this tool when the user asks about:
    - loans
    - my loans
    - loan details
    - outstanding loan amount
    - principal amount
    - interest rate
    - loan tenure
    - loan status

    The authenticated user's JWT is used automatically.
    """

    access_token = _get_access_token(runtime)

    if not access_token:
        return "Unable to access banking information."

    url = f"{GATEWAY_BASE_URL}/api/loans"

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

        loans = data.get("content", [])

        if not loans:
            return "No loans found for the customer."

        result = []

        for loan in loans:

            result.append(
                f"Loan ID: {loan.get('loanId')}\n"
                f"Loan Number: {loan.get('loanNumber')}\n"
                f"Loan Type: {loan.get('loanType')}\n"
                f"Principal Amount: ₹{loan.get('principalAmount')}\n"
                f"Outstanding Amount: ₹{loan.get('outstandingAmount')}\n"
                f"Interest Rate: {loan.get('interestRate')}%\n"
                f"Tenure: {loan.get('tenureMonths')} months\n"
                f"Status: {loan.get('status')}\n"
                f"Created At: {loan.get('createdAt')}\n"
                f"Updated At: {loan.get('updatedAt')}"
            )

        return "\n\n".join(result)

    except httpx.HTTPStatusError as error:

        if error.response.status_code == 401:
            return (
                "Unable to retrieve loan information. "
                "Authentication failed."
            )

        if error.response.status_code == 403:
            return (
                "You are not authorized to access "
                "loan information."
            )

        return (
            "Unable to retrieve loan information "
            "from the banking service."
        )

    except httpx.RequestError:

        return (
            "The banking service is currently unavailable. "
            "Please try again later."
        )

    except Exception as error:

        print(f"Loan tool error: {error}")

        return (
            "An unexpected error occurred while "
            "retrieving loan information."
        )