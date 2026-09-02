from fastapi import FastAPI

from app.api.chat import router as chat_router


app = FastAPI(
    title="Banking AI Service",
    version="1.0.0"
)


app.include_router(
    chat_router,
    prefix="/api/ai",
    tags=["AI"]
)


@app.get("/")
def root():

    return {
        "message": "Banking AI Service is running"
    }


@app.get("/health")
def health():

    return {
        "status": "UP"
    }