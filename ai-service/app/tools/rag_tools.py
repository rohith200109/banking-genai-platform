from langchain.tools import tool

from app.rag.retriever import search_knowledge


@tool
def search_banking_knowledge(query: str) -> str:
    """
    Search the bank's knowledge base for general banking
    information, policies, FAQs, loan information,
    transaction information, and account-related guidance.

    Use this tool when the user asks about banking policies,
    procedures, eligibility, requirements, FAQs, or other
    general banking information.

    Do not use this tool for live customer account balances,
    transactions, or loan records.
    """

    try:

        results = search_knowledge(
            query=query,
            k=4
        )

        if not results:
            return (
                "No relevant information was found "
                "in the banking knowledge base."
            )

        response_parts = []

        for document in results:

            source = document.metadata.get(
                "source",
                "unknown"
            )

            content = document.page_content.strip()

            response_parts.append(
                f"Source: {source}\n"
                f"Information:\n{content}"
            )

        return "\n\n".join(response_parts)

    except Exception as error:

        print(
            f"RAG tool error: {error}"
        )

        return (
            "Unable to search the banking "
            "knowledge base at this time."
        )