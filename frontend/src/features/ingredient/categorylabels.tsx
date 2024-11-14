"use client";
import styles from "../../../styles/categorylabel.module.css";

export default function CategoryLabels({ category }: { category: string }) {
  return (
    <span className={`${styles.label} ${styles[category]}`}>{category}</span>
  );
}
