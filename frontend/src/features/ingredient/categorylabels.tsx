"use client";
import styles from "../../../styles/categorylabel.module.css";
import { CategoryType, CategoryLabel } from "@/types/category";

interface CategoryLabelProps {
  category: CategoryType;
}

export default function CategoryLabels({ category }: CategoryLabelProps) {
  return (
    <span className={`${styles.label} ${styles[`category${category}`]}`}>
      {CategoryLabel[category]}
    </span>
  );
}
