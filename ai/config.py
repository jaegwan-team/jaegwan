# config.py
import os
from dotenv import load_dotenv

# .env 파일을 로드
load_dotenv()

# 환경 변수에서 API URL과 키를 가져오기
OCR_API_URL = os.getenv('OCR_API_URL')
OCR_SECRET_KEY = os.getenv('OCR_SECRET_KEY')
OPENAI_API_KEY = os.getenv('OPENAI_API_KEY')