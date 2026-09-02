from app.rag.vector_store import get_vector_store


def search_knowledge(
    query: str,
    k: int = 4
):
    vector_store = get_vector_store()

    results = vector_store.similarity_search(
        query,
        k=k
    )

    return results