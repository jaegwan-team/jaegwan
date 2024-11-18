# ocr_service.py
import requests
import time
import os
import openai
import json
from config import OCR_API_URL, OCR_SECRET_KEY, OPENAI_API_KEY


openai.api_key = OPENAI_API_KEY


def analyze_image_with_ocr(image_url: str) -> str:
    # 이미지 URL에서 파일 확장자 추출
    file_extension = os.path.splitext(image_url)[-1].lower().replace('.', '')
    if file_extension not in ['jpg', 'jpeg', 'png']:
        raise ValueError("Unsupported image format. Supported formats are: jpg, jpeg, png")

    # OCR API 요청 설정
    headers = {'X-OCR-SECRET': OCR_SECRET_KEY}
    payload = {
        'images': [{
            'url': image_url,
            'name': 'receipt',
            'format': file_extension
        }],
        'requestId': str(int(time.time() * 1000)),  # 고유 ID로 설정
        'version': 'V2',
        'timestamp': int(time.time() * 1000)  # 현재 시간 스탬프로 설정
    }

    response = requests.post(OCR_API_URL, headers=headers, json=payload)
    # async with httpx.AsyncClient() as client:
    #     response = await client.post(OCR_API_URL, headers=headers, data=payload)

    # 상태 코드에 따라 에러 처리
    if response.status_code != 200:
        print("Error details:", response.text)
    response.raise_for_status()

    ocr_result = response.json()

    # OCR 데이터에서 텍스트 추출
    text = ' '.join(item['inferText'] for item in ocr_result['images'][0].get('fields', []))
    return text


def analyze_text_with_chatgpt(text: str, max_retries: int = 3, current_retry: int = 0) -> list:
    # ChatGPT에 재료 정보를 분류 요청
    category = """
        Vegetables("채소", "1"),
        Fruits("과일", "2"),
        Meat("고기", "3"),
        Seafood("해산물", "4"),
        Dairy("유제품", "5"),
        Grains("곡물", "6"),
        Spices("향신료", "7"),
        Herbs("허브", "8"),
        Oils("오일", "9"),
        Drinks("음료", "10");
    """

    example = """
        {
            "category": 1,
            "name": "당근",
            "unit": 1,
            "amount": 1,
            "price": 10000
        }
    """

    query = f"""
    {text}
    위 텍스트에서 재료 name을 추출하고, 주요 단어만 남겨주세요.
    재료의 category를 {category}에 따라 분류하고, {example}처럼 category, name, unit(단위, 계란 같은 건 15구라면 unit이 15), 
    amount(영수증에 적힌 개수), price(할인 가격이 있다면 빼고 지불한 총 가격)가 담긴 객체 리스트로 반환해주세요.
    그리고 name은 무조건 식자재 관련된 거니깐 오타 있으면 알아서 수정해주세요.
    """

    try:
        # ChatGPT API 호출
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=[{"role": "user", "content": query}]
        )
        result_text = response['choices'][0]['message']['content']

        # JSON 문자열을 파이썬 리스트로 변환
        result_text = result_text.strip("```json").strip("```").strip()
        result_data = json.loads(result_text)  # JSON 변환 시도
        return result_data

    except json.JSONDecodeError as e:
        print(f"JSON 변환 실패 (재시도 {current_retry + 1}/{max_retries}): {e}")
        if current_retry < max_retries:
            return analyze_text_with_chatgpt(text, max_retries, current_retry + 1)
        else:
            raise RuntimeError("JSON 변환 실패: 최대 재시도 횟수 초과")  # 최대 재시도 초과 시 예외 발생
    except Exception as e:
        print(f"API 호출 실패 (재시도 {current_retry + 1}/{max_retries}): {e}")
        if current_retry < max_retries:
            return analyze_text_with_chatgpt(text, max_retries, current_retry + 1)
        else:
            raise RuntimeError("API 호출 실패: 최대 재시도 횟수 초과")  # 최대 재시도 초과 시 예외 발생