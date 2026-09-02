from fastapi import APIRouter, Header, HTTPException

from app.models.chat import (
    ChatRequest,
    ChatResponse
)

from app.chains.chat_chain import process_chat


router = APIRouter()


@router.post(
    "/chat",
    response_model=ChatResponse
)
def chat(
    request: ChatRequest,
    authorization: str = Header(...)
):

    if not authorization.startswith("Bearer "):

        raise HTTPException(
            status_code=401,
            detail="Invalid Authorization header"
        )

    access_token = authorization[len("Bearer "):].strip()

    if not access_token:

        raise HTTPException(
            status_code=401,
            detail="Missing access token"
        )

    response = process_chat(
        request.message,
        access_token
    )

    return ChatResponse(
        response=response
    )