import { axiosClient } from './axios';

// user

// ingredient
export const getIngredientList = ( restuarent: Record<string, number> ) => {
    return axiosClient.post('/ingredient', restuarent);
};

export const getIngredientDetail = (id : number) => {
    return axiosClient.get(`/ingredient/${id}`);
}

export const deleteIngredient = (ingredientId: number) => {
    return axiosClient.delete(`/ingredient/detail/${ingredientId}`);
}

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
}

// receipt
export const getReceiptList = ( restuarent: Record<string, number> ) => {
    return axiosClient.post(`/receipt/detail`, restuarent, {
        headers: {
            'Requires-Auth': true,
        }
    });
};

export const confirmReceipt = (content : Record<string, unknown>) => {
    return axiosClient.post(`/receipt/confirm`, content);
}

export const getReceiptDetail = (id: number) => {
    return axiosClient.get(`/receipt/detail/${id}`);
}

export const deleteReceiptDetail = (receiptIngredientId: number) => {
    return axiosClient.delete(`/receipt/detail/${receiptIngredientId}`);
}

// significant
