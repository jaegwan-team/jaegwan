"use client";

import styles from "../../../styles/header.module.css";
import { useUser } from "../users/api/login/loginUsers";

export default function Header() {
  const { user } = useUser();
  return (
    <header className={styles.layout}>
      <div className={styles.headerbox}>
        <div>식당 이름</div>
        <div>{user?.name}</div>
      </div>
    </header>
  );
}
