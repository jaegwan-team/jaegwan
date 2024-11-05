import { axiosClient } from './axios';

// user

// ingredient
export const getIngredientList = ( restuarent: Record<string, number> ) => {
    return axiosClient.post('/ingredient', restuarent, {
        headers: {
            'Requires-Auth': true,
        }
    });
};

export const getIngredientDetail = (id : number) => {
    return axiosClient.get(`/ingredient/${id}`, {
        headers: {
            'Requires-Auth': true,
        }
    });
}

// menu

// receipe

// receipt
export const getReceiptList = ( restuarent: Record<string, number> ) => {
    return axiosClient.post(`/receipt/detail`, restuarent, {
        headers: {
            'Requires-Auth': true,
        }
    });
};

export const uploadReceipt = () => {
};

// significant
