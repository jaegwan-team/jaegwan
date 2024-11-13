"use client";

import styles from "../../../styles/header.module.css";
import { useUser } from "../users/api/login/loginUsers";

export default function Header() {
  const { user, isLoading } = useUser();

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <header className={styles.layout}>
      <div className={styles.headerbox}>
        <div>식당 이름</div>
        <div>{user?.name}</div>
      </div>
    </header>
  );
}
