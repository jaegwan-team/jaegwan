import type { Metadata } from "next";
import "../../styles/globals.css";
import { AppProvider } from "./provider";

export const metadata: Metadata = {
  title: "재관둥이",
  description: "식당 재고 관리 서비스",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body className="font-pretendard">
        <AppProvider>{children}</AppProvider></body>
    </html>
  );
}
