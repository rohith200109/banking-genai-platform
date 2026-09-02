from pathlib import Path
import sys

from langchain_core.documents import Document
from langchain_text_splitters import RecursiveCharacterTextSplitter

if __package__ in {None, ""}:
    sys.path.append(str(Path(__file__).resolve().parents[2]))

from app.rag.vector_store import get_vector_store


DOCUMENTS_DIR = Path(__file__).parent / "documents"


def load_documents():
    documents = []

    for file_path in DOCUMENTS_DIR.glob("*.txt"):

        text = file_path.read_text(
            encoding="utf-8"
        )

        documents.append(
            Document(
                page_content=text,
                metadata={
                    "source": file_path.name
                }
            )
        )

    return documents


def ingest_documents():

    documents = load_documents()

    print(
        f"Loaded {len(documents)} documents"
    )

    splitter = RecursiveCharacterTextSplitter(
        chunk_size=500,
        chunk_overlap=50
    )

    chunks = splitter.split_documents(
        documents
    )

    print(
        f"Created {len(chunks)} chunks"
    )

    vector_store = get_vector_store()

    vector_store.add_documents(chunks)

    print(
        f"Stored {len(chunks)} chunks in pgvector"
    )


if __name__ == "__main__":
    ingest_documents()
