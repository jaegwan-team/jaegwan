import type { Metadata } from "next";
import "../../../styles/globals.css";
import Header from "@/features/ui/header";

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
    <html lang="en" className="font-pretendard">
      <body className="font-pretendard">
        <Header />
        {children}
      </body>
    </html>
  );
}
