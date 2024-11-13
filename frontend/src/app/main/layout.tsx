import type { Metadata } from "next";
import "../../../styles/globals.css";
import { Providers } from "../providers";
import MainTemplate from "./template";

export const metadata: Metadata = {
  title: "재관둥이",
  description: "식당 재고 관리 서비스",
};

export default function MainLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <body className="font-pretendard">
        <Providers>
          <MainTemplate>{children}</MainTemplate>
        </Providers>
      </body>
    </html>
  );
}
