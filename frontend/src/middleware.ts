/* eslint-disable @typescript-eslint/no-unused-vars */
import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

const PUBLIC_PATHS = ["/login"];
const BACKEND_BASE_URL = "https://k11a501.p.ssafy.io";

async function validateToken(token: string) {
  try {
    const response = await fetch(`${BACKEND_BASE_URL}/api/auth/kakao/login`, {
      headers: {
        Authorization: `${token}`,
      },
    });
    return response.ok;
  } catch (error) {
    return false;
  }
}

export async function middleware(request: NextRequest) {
  const { pathname } = request.nextUrl;

  // 루트 경로 처리
  if (pathname === "/") {
    const accessToken = request.cookies.get("accessToken")?.value;
    if (!accessToken) {
      const loginUrl = new URL("/login", request.url);
      return NextResponse.redirect(loginUrl);
    }

    const isValidToken = await validateToken(accessToken);
    if (isValidToken) {
      const mainUrl = new URL("/main", request.url);
      return NextResponse.redirect(mainUrl);
    }

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

      const mainUrl = new URL("/main", request.url);
      return NextResponse.redirect(mainUrl);
    } catch (error) {
      const loginUrl = new URL("/login", request.url);
      return NextResponse.redirect(loginUrl);
    }
  }

  // 공개 경로는 그대로 통과
  if (PUBLIC_PATHS.includes(pathname)) {
    return NextResponse.next();
  }

  // accessToken 확인
  const accessToken = request.cookies.get("accessToken")?.value;
  if (!accessToken) {
    const loginUrl = new URL("/login", request.url);
    return NextResponse.redirect(loginUrl);
  }
  console.log("===========accessToken==========")
  console.log(accessToken);
  // accessToken 유효성 검증
  const isValidToken = await validateToken(accessToken);
  console.log("===========isValidToken==========")
  console.log(isValidToken);
  if (isValidToken) {
    const response = NextResponse.next();
    response.headers.set('Authorization', accessToken);
    return response;
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

    const res = NextResponse.next();
    res.headers.set('Authorization', accessToken);
    return res;
  } catch (error) {
    const loginUrl = new URL("/login", request.url);
    return NextResponse.redirect(loginUrl);
  }
}

export const config = {
  matcher: ["/((?!api|_next|_static|_vercel|favicon.ico|sitemap.xml).*)"],
};
