"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
  ReactNode,
} from "react";
import { getUserInfo } from "../../../../services/api";
import type { UserProps } from "@/types/user";

type UserContextType = {
  user: UserProps | null;
  setUser: (user: UserProps | null) => void;
  isLoading: boolean;
};

const UserContext = createContext<UserContextType>({
  user: null,
  setUser: () => {},
  isLoading: true,
});

export function UserProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<UserProps | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  const fetchUserData = async () => {
    try {
      const response = await getUserInfo();
      console.log(response.data);
      setUser(response.data);
    } catch (error) {
      console.error("Failed to fetch user data:", error);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchUserData();
  }, []);

  const value = {
    user,
    setUser,
    isLoading,
  };

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
}

export const useUser = () => {
  return useContext(UserContext);
};
