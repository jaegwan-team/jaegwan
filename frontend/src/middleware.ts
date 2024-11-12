/* eslint-disable @typescript-eslint/no-unused-vars */
import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

const PUBLIC_PATHS = ["/login"];
const BACKEND_BASE_URL = process.env.NEXT_PUBLIC_ADDRESS;

async function validateToken(token: string) {
  try {
    const response = await fetch(`${BACKEND_BASE_URL}/api/auth/kakao/login`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.ok;
  } catch (error) {
    return false;
  }
}

export async function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  // 공개 경로는 그대로 통과
  if (PUBLIC_PATHS.includes(pathname)) {
    return NextResponse.next();
  }

  // accessToken 확인
  const accessToken = request.cookies.get("authToken")?.value;
  if (!accessToken) {
    const loginUrl = new URL("/login", request.url);
    return NextResponse.redirect(loginUrl);
  }

  // accessToken 유효성 검증
  const isValidToken = await validateToken(accessToken);
  if (isValidToken) {
    return NextResponse.next();
  }

  // accessToken이 유효하지 않은 경우, refreshToken으로 재발급 시도
  const refreshToken = request.cookies.get("refreshToken")?.value;
  if (!refreshToken) {
    const loginUrl = new URL("/login", request.url);
    return NextResponse.redirect(loginUrl);
  }

  try {
    const response = await fetch(`${BACKEND_BASE_URL}/auth/reissue`, {
      headers: {
        Authorization: `${refreshToken}`,
      },
    });

    if (!response.ok) {
      const loginUrl = new URL("/login", request.url);
      return NextResponse.redirect(loginUrl);
    }

    return NextResponse.next();
  } catch (error) {
    const loginUrl = new URL("/login", request.url);
    return NextResponse.redirect(loginUrl);
  }
}

export const config = {
  matcher: ["/((?!api|_next|_static|_vercel|favicon.ico|sitemap.xml).*)"],
};
