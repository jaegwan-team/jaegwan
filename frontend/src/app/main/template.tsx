"use client";

import Header from "@/features/ui/header";
import styles from "../../../styles/main.module.css";
import TabNavigation from "../../features/ui/TabNavigation";
import UserProvider from "../../features/users/api/login/loginUsers";

export default function MainTemplate({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className={styles.layout}>
    
      <UserProvider>
        <Header />
        <TabNavigation />
        {children}
      </UserProvider>
      </div>
  );
}
