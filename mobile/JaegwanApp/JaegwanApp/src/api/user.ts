import {axiosClient} from './axios';

export const getUserInfo = async () => {
  try {
    const response = await axiosClient.get('/api/member/top');
    return response.data;
  } catch (error) {
    console.error('getUserInfo error:', error);
    throw error;
  }
};
