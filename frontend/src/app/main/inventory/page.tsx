import styles from "../../../../styles/inventory.module.css";
import CategoryLabels from "@/features/ingredient/categorylabels";
import { CategoryType } from "@/types/category";

export default function InventoryPage() {
  return (
    <div className={styles.content}>
      <div className={styles.inventorybox}>
        <div className={styles.inventoryheader}>
          <div className={styles.inventorytitle}>재고 현황</div>
          <div>여기는 검색존</div>
        </div>
        <div className={styles.inventorytable}>
          <div className={styles.tablehead}>
            <div className={styles.itemname}>재고명</div>
            <div className={styles.itemcategory}>분류</div>
            <div className={styles.itemamount}>용량</div>
            <div className={styles.itemexpiredate}>유통기한</div>
          </div>
          <div className={styles.tabledetails}>
            <div className={styles.tabledetail}>
              <div className={styles.itemname}>양파</div>
              <div className={styles.itemcategory}>
                <CategoryLabels category={CategoryType.Vegetables} />
              </div>
              <div className={styles.itemamount}>2kg</div>
              <div className={styles.itemexpiredate}>D-2</div>
            </div>
            <div className={styles.tabledetail}>
              <div className={styles.itemname}>양고기</div>
              <div className={styles.itemcategory}>
                <CategoryLabels category={CategoryType.Meat} />
              </div>
              <div className={styles.itemamount}>2kg</div>
              <div className={styles.itemexpiredate}>D-2</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
