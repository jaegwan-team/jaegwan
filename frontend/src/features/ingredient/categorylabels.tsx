"use client";
import styles from "../../../styles/categorylabel.module.css";

const CategoryMap: Record<string, number> = {
  채소: 1,
  과일: 2,
  고기: 3,
  해산물: 4,
  유제품: 5,
  곡물: 6,
  향신료: 7,
  허브: 8,
  오일: 9,
  음료: 10,
};

export default function CategoryLabels({ category }: { category: string }) {
  const categoryNumber = CategoryMap[category] || 1;

  return (
    <span className={`${styles.label} ${styles[`category${categoryNumber}`]}`}>
      {category}
    </span>
  );
}
