"use client";

import Image from "next/image";
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
        <div>{user?.restaurants[0]?.name}</div>
        <div className={styles.profile}>
          <Image
            src={user!.imageUrl}
            alt="kakaoprofile"
            width={22}
            height={22}
            unoptimized={true}
          ></Image>
          {user?.name}
        </div>
      </div>
    </header>
  );
}
