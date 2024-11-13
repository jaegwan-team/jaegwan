import axios from "axios";

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
