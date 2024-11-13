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
    <UserProvider>
      <Header />
      <div className={styles.layout}>
        <TabNavigation />
        {children}
      </div>
    </UserProvider>
  );
}
