import React, {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {getUserInfo} from '../api/user';
interface UserType {
  id: number;
  name: string;
  role: string;
  email: string;
  imageUrl: string;
  restaurants: {
    id: number;
    name: string;
    registerNumber: string;
  }[];
}

interface UserContextType {
  user: UserType | null;
  setUser: (user: UserType | null) => void;
  isLoading: boolean;
}

const UserContext = createContext<UserContextType>({
  user: null,
  setUser: () => {},
  isLoading: false,
});

export const UserProvider = ({children}: {children: ReactNode}) => {
  const [user, setUser] = useState<UserType | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  // 앱 시작시 저장된 유저 정보 확인
  useEffect(() => {
    const loadUser = async () => {
      try {
        const token = await AsyncStorage.getItem('accessToken');

        if (token) {
          const userInfo = await getUserInfo();
          setUser(userInfo.data);
        }
      } catch (error) {
        console.error('Failed to load user:', error);
      } finally {
        setIsLoading(false);
      }
    };

    loadUser();
  }, []);

  return (
    <UserContext.Provider value={{user, setUser, isLoading}}>
      {children}
    </UserContext.Provider>
  );
};

export const useUser = () => useContext(UserContext);
