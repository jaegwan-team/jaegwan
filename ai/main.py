from fastapi import FastAPI
from pydantic import BaseModel
from ocr_service import analyze_image_with_ocr, analyze_text_with_chatgpt
import asyncio

app = FastAPI()
asyncio.set_event_loop_policy(asyncio.WindowsSelectorEventLoopPolicy())


class ImageRequest(BaseModel):
    image_url: str


@app.get("/")
def root():
    return {"message": "Hello World"}


@app.post("/api/receipt/image")
def ocr(request: ImageRequest):
    analyzer = analyze_image_with_ocr(request.image_url)
    result = analyze_text_with_chatgpt(analyzer)
    return result


# if __name__ == "__main__":
    # asyncio.run(main())