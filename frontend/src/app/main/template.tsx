"use client";

import Header from "@/features/ui/header";
import styles from "../../../styles/main.module.css";
import TabNavigation from "../../features/ui/TabNavigation";

export default function MainTemplate({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <>
      <Header />
      <div className={styles.layout}>
        <TabNavigation />
        {children}
      </div>
    </>
  );
}
