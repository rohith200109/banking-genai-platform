import os

from dotenv import load_dotenv


load_dotenv()


# OPENAI_API_KEY = os.getenv("OPENAI_API_KEY")
# OPENAI_MODEL = os.getenv("OPENAI_MODEL", "gpt-5-mini")


# if not OPENAI_API_KEY:
#     raise RuntimeError(
#         "OPENAI_API_KEY is not configured"
#     )

GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")

GEMINI_MODEL = os.getenv(
    "GEMINI_MODEL",
    "gemini-3.6-flash"
)


if not GEMINI_API_KEY:
    raise RuntimeError(
        "GEMINI_API_KEY is not configured"
    )

PGVECTOR_CONNECTION = os.getenv("PGVECTOR_CONNECTION")

if not PGVECTOR_CONNECTION:
    raise RuntimeError(
        "PGVECTOR_CONNECTION is not configured"
    )

GEMINI_EMBEDDING_MODEL = os.getenv(
    "GEMINI_EMBEDDING_MODEL",
    "models/gemini-embedding-001"
)