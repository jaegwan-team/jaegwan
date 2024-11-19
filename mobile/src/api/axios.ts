import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const BACKEND_BASE_URL = 'https://k11a501.p.ssafy.io';

export const axiosClient = axios.create({
  baseURL: BACKEND_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request Interceptor
axiosClient.interceptors.request.use(
  async config => {
    const token = await AsyncStorage.getItem('accessToken');
    if (token) {
      config.headers.Authorization = `${token}`;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  },
);
