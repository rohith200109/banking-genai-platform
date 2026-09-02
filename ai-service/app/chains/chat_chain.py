import json
from typing import Any

from app.agents.banking_agent import banking_agent


def _content_to_text(content: Any) -> str:
    if isinstance(content, str):
        return content

    if isinstance(content, list):
        text_parts = []

        for block in content:
            if isinstance(block, str):
                text_parts.append(block)
                continue

            if isinstance(block, dict):
                block_text = block.get("text")

                if isinstance(block_text, str):
                    text_parts.append(block_text)
                    continue

        if text_parts:
            return "\n".join(text_parts)

    return json.dumps(content, default=str)


def process_chat(
    message: str,
    access_token: str
) -> str:

    result = banking_agent.invoke(
        {
            "messages": [
                {
                    "role": "user",
                    "content": message
                }
            ]
        },
        context={
            "access_token": access_token
        }
    )

    messages = result["messages"]

    return _content_to_text(messages[-1].content)
