import { axiosClient } from "./axios";

// user

// ingredient
export const getIngredientList = (restaurant: Record<string, number>) => {
  return axiosClient.post("/ingredient", restaurant);
};

export const getIngredientDetail = (id: number) => {
  return axiosClient.get(`/ingredient/${id}`);
};

export const deleteIngredient = (ingredientId: number) => {
  return axiosClient.delete(`/ingredient/detail/${ingredientId}`);
};

//!!!자동완성 위한 type 지정임!!!
interface AutocompleteRequest {
  restaurantId: number;
  word: string;
}

export const autocompleteIngredient = (request: AutocompleteRequest) => {
  return axiosClient.post("/ingredient/auto-complete", request);
};

// restaurant
export const registRestaurant = (restaurantData: Record<string, string>) => {
  return axiosClient.post(`/restaurant`, restaurantData);
};

export const getRestaurant = (id: number) => {
  return axiosClient.get(`restaurant/${id}`);
};

export const getRestaurantMember = (id: number) => {
  return axiosClient.get(`restaurant/${id}/member`);
};

export const registRestaurantMember = (id: number, memberId: number) => {
  return axiosClient.post(`restaurant/${id}/member/${memberId}`);
};

// receipt
export const getReceiptList = (restaurant: Record<string, number>) => {
  return axiosClient.post(`/receipt/detail`, restaurant, {
    headers: {
      "Requires-Auth": true,
    },
  });
};

export const confirmReceipt = (content: Record<string, unknown>) => {
  return axiosClient.post(`/receipt/confirm`, content);
};

export const getReceiptDetail = (id: number) => {
  return axiosClient.get(`/receipt/detail/${id}`);
};

export const deleteReceiptDetail = (receiptIngredientId: number) => {
  return axiosClient.delete(`/receipt/detail/${receiptIngredientId}`);
};

// significant
