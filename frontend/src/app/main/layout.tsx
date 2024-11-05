import type { Metadata } from "next";
import "../../../styles/globals.css";
import Header from "@/features/ui/header";
import styles from "../../../styles/main.module.css";
import TabNavigation from "../../features/ui/TabNavigation";

export const metadata: Metadata = {
  title: "재관둥이",
  description: "식당 재고 관리 서비스",
};

export default function MainLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="font-pretendard">
        <Header />
        <div className={styles.layout}>
          <TabNavigation />
          {children}
        </div>
      </body>
    </html>
  );
}
