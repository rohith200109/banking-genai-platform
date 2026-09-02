from app.rag.retriever import search_knowledge


query = "What documents are required for a personal loan?"

results = search_knowledge(query)

print("\n===== RAG RESULTS =====\n")

for index, document in enumerate(results, start=1):

    print(f"--- Result {index} ---")

    print("Source:", document.metadata.get("source"))

    print("Content:")
    print(document.page_content)

    print()