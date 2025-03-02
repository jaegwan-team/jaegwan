import { UpdatedReceiptDetailTypes } from "@/types/receiptType";
import { axiosClient } from "./axios";
import { ReceiptListParams, SignificantListParams } from "@/types/mainType";
import { SignificantParams } from "@/types/significantType";

// user
export const getUserInfo = async () => {
  try {
    console.log("Sending request to /api/member/me"); // 요청 시작 로그
    const response = await axiosClient.get("/api/member/top");
    console.log("Response received:", response); // 응답 로그
    return response;
  } catch (error) {
    console.error("getUserInfo error:", error); // 자세한 에러 로그
    throw error;
  }
};

// ingredient
export const getIngredientList = (restaurant: Record<string, number>) => {
  return axiosClient.post("/api/ingredient", restaurant);
};

export const getIngredientDetail = (id: number) => {
  return axiosClient.get(`/api/ingredient/${id}`);
};

export const deleteIngredient = (ingredientId: number) => {
  return axiosClient.delete(`/api/ingredient/detail/${ingredientId}`);
};

//!!!자동완성 위한 type 지정임!!!
interface AutocompleteRequest {
  restaurantId: number;
  word: string;
}

export const autocompleteIngredient = (request: AutocompleteRequest) => {
  return axiosClient.post("/api/ingredient/auto-complete", request);
};

// restaurant
export const registRestaurant = (restaurantData: Record<string, string>) => {
  return axiosClient.post(`/api/restaurant`, restaurantData);
};

export const getRestaurant = (id: number) => {
  return axiosClient.get(`/api/restaurant/${id}`);
};

export const getRestaurantMember = (id: number) => {
  return axiosClient.get(`/api/restaurant/${id}/member`);
};

export const registRestaurantMember = (id: number, memberId: number) => {
  return axiosClient.post(`/api/restaurant/${id}/member/${memberId}`);
};

// receipt
export const getReceiptList = (restaurant: ReceiptListParams) => {
  return axiosClient.post(`/api/receipt/detail`, restaurant);
};

interface ConfirmRequest {
  receiptId: number;
  receiptIngredientConfirmData: UpdatedReceiptDetailTypes[];
}

export const confirmReceipt = (content: ConfirmRequest) => {
  return axiosClient.post(`/api/receipt/confirm`, content);
};

export const getReceiptDetail = (id: number) => {
  return axiosClient.get(`/api/receipt/detail/${id}`);
};

export const deleteReceiptDetail = (receiptIngredientId: number) => {
  return axiosClient.delete(`/api/receipt/detail/${receiptIngredientId}`);
};

export const getReceiptImage = (id: number) => {
  return axiosClient.get(`/api/receipt/image/${id}`);
};

// significant
export const getSignificantListUnchecked = (
  restaurant: SignificantListParams
) => {
  return axiosClient.post(`/api/significant/list/uncheck`, restaurant);
};

export const getSignificantList = (restaurant: SignificantListParams) => {
  return axiosClient.post(`/api/significant/list`, restaurant);
};

export const confirmSignificant = (significant: SignificantParams) => {
  return axiosClient.put(`api/significant`, significant);
};

export const deleteSignificant = (significant: SignificantParams) => {
  return axiosClient.delete(`api/significant`, { data: significant });
};
