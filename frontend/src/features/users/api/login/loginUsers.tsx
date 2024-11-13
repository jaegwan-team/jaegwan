"use client";

import {
  createContext,
  useContext,
  useEffect,
  useState,
  useCallback,
  ReactNode,
} from "react";
import { getUserInfo } from "../../../../services/api";
import type { UserProps } from "@/types/user";
import { useRouter } from "next/navigation";

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

export default function UserProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<UserProps | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const router = useRouter();

  const fetchUserData = useCallback(async () => {
    try {
      console.log("Fetching user data..."); // 요청 시작
      const response = await getUserInfo();
      console.log("Raw response:", response); // 전체 응답
      console.log("User data:", response.data); // 실제 데이터
      const userData = response.data.data;
      setUser(userData);

      if (userData.restaurants.length === 0) {
        console.log("No restaurants found, redirecting to register page...");
        router.push("/register");
        return;
      }
    } catch (error) {
      console.error("Failed to fetch user data:", error);
      // 에러 상세 정보 출력
      if (error instanceof Error) {
        console.error("Error details:", error.message);
      }
    } finally {
      setIsLoading(false);
    }
  }, [router]);

  useEffect(() => {
    console.log("UserProvider mounted");
    fetchUserData();
  }, [fetchUserData]);

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
