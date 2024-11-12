// middleware.ts (프로젝트 루트에 위치)
import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

// 인증이 필요하지 않은 경로들
const PUBLIC_PATHS = ["/login"];

// 미들웨어 함수
export function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  // 공개 경로는 그대로 진행
  if (PUBLIC_PATHS.includes(pathname)) {
    return NextResponse.next();
  }

  // 쿠키에서 인증 토큰 확인
  const token = request.cookies.get("authToken")?.value;

  // 인증되지 않은 경우 로그인 페이지로 리다이렉트
  if (!token) {
    const loginUrl = new URL("/login", request.url);
    return NextResponse.redirect(loginUrl);
  }

  return NextResponse.next();
}

export const config = {
  matcher: ["/((?!api|_next|_static|_vercel|favicon.ico|sitemap.xml).*)"],
};
