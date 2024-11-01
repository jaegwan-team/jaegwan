import styles from "../../../styles/header.module.css";

export default function Header() {
  return (
    <header className={styles.layout}>
      <div className={styles.headerbox}>
        <div>식당 이름</div>
        <div>사람 이름</div>
      </div>
    </header>
  );
}
