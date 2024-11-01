"use client";
import Link from "next/link";
import { usePathname } from "next/navigation";
import styles from "../../../styles/main.module.css";
import { Home } from "lucide-react";

export default function TabNavigation() {
  const pathname = usePathname();

  const tabs = [
    { name: <Home size={17} />, path: "/main" },
    { name: "재고 현황", path: "/main/inventory" },
    { name: "구매 내역", path: "/main/purchase" },
    { name: "특이 사항", path: "/main/significant" },
  ];

  return (
    <div className={styles.tabs}>
      {tabs.map((tab) => (
        <Link
          key={tab.path}
          href={tab.path}
          className={`${styles.tab} ${
            pathname === tab.path ? styles.activeTab : ""
          }`}
        >
          {tab.name}
        </Link>
      ))}
    </div>
  );
}
