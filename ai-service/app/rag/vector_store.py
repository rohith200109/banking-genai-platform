from langchain_postgres import PGVector

from app.config.settings import PGVECTOR_CONNECTION
from app.rag.embeddings import get_embeddings


COLLECTION_NAME = "banking_knowledge"


def get_vector_store():

    return PGVector(
        embeddings=get_embeddings(),
        collection_name=COLLECTION_NAME,
        connection=PGVECTOR_CONNECTION,
        use_jsonb=True
    )