import axios from "axios";
import { getCookie } from "cookies-next";

export const BASE_URL = "https://k11a501.p.ssafy.io";
//process.env.NEXT_PUBLIC_BACKEND_ADDRESS;

export const HEADERS = {
  "Access-Control-Allow-Origin": "*",
  "Content-Type": "application/json",
};

export const axiosClient = axios.create({
  baseURL: BASE_URL,
  headers: HEADERS,
  withCredentials: true,
});

// 요청 인터셉터 추가
axiosClient.interceptors.request.use(
  (config) => {
    const accessToken = getCookie("accessToken");
    if (accessToken) {
      config.headers.Authorization = `${accessToken}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axiosClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // 401 에러이고 재시도하지 않았던 요청인 경우
    if (error.response?.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;

      try {
        const refreshToken = getCookie("refreshToken");
        const response = await fetch(`${BASE_URL}/auth/reissue`, {
          method: "POST",
          headers: {
            Authorization: `${refreshToken}`,
          },
        });

        if (response.ok) {
          const data = await response.json();

          originalRequest.headers.Authorization = `${data.accessToken}`;
          return axiosClient(originalRequest);
        }
      } catch (refreshError) {
        // 리프레시 토큰도 만료된 경우
        console.log(refreshError);
        window.location.href = "/login";
      }
    }

    return Promise.reject(error);
  }
);
