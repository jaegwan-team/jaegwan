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
import { useRouter, usePathname } from "next/navigation";

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

const PROTECTED_PATHS = [
  "/main",
  "/main/purchase",
  "/main/inventory",
  "/main/significant",
];

export default function UserProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<UserProps | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const router = useRouter();
  const pathname = usePathname();

  const fetchUserData = useCallback(async () => {
    try {
      console.log("Fetching user data...");
      const response = await getUserInfo();
      console.log("Raw response:", response);
      console.log("User data:", response.data);
      const userData = response.data.data;
      setUser(userData);

      if (
        PROTECTED_PATHS.includes(pathname) &&
        (!userData.restaurants || userData.restaurants.length === 0)
      ) {
        console.log("No restaurants found, redirecting to register page...");
        router.push("/register");
        return;
      }

      if (
        pathname === "/register" &&
        userData.restaurants &&
        userData.restaurants.length > 0
      ) {
        console.log("Restaurants exist, redirecting to main page...");
        router.push("/main");
        return;
      }
    } catch (error) {
      console.error("Failed to fetch user data:", error);
      if (error instanceof Error) {
        console.error("Error details:", error.message);
      }
    } finally {
      setIsLoading(false);
    }
  }, [router, pathname]);

  useEffect(() => {
    console.log("UserProvider mounted");
    fetchUserData();
  }, [fetchUserData]);

  useEffect(() => {
    if (user && PROTECTED_PATHS.includes(pathname)) {
      if (!user.restaurants || user.restaurants.length === 0) {
        router.push("/register");
      }
    }
  }, [pathname, user, router]);

  const value = {
    user,
    setUser,
    isLoading,
  };

  if (isLoading && PROTECTED_PATHS.includes(pathname)) {
    return <div>Loading...</div>;
  }

  return <UserContext.Provider value={value}>{children}</UserContext.Provider>;
}

export const useUser = () => {
  return useContext(UserContext);
};
