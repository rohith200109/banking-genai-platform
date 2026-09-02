from langchain_google_genai import GoogleGenerativeAIEmbeddings

from app.config.settings import (
    GEMINI_API_KEY,
    GEMINI_EMBEDDING_MODEL
)


def get_embeddings():
    return GoogleGenerativeAIEmbeddings(
        model=GEMINI_EMBEDDING_MODEL,
        google_api_key=GEMINI_API_KEY
    )