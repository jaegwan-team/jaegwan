"use client";
import Header from "@/features/ui/header";
import styles from "../../../styles/main.module.css";
import TabNavigation from "../../features/ui/TabNavigation";
import UserProvider from "../../features/users/api/login/loginUsers";

export function Providers({ children }: { children: React.ReactNode }) {
  return (
    <UserProvider>
      <div className={styles.layout}>
        <Header />
        <TabNavigation />
        {children}
      </div>
    </UserProvider>
  );
}
