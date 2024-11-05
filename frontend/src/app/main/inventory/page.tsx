"use client";

import { useState } from "react";
import styles from "../../../../styles/inventory.module.css";
import Image from "next/image";
import DropdownSVG from "../../../../public/drop_down.svg";
import DroprightSVG from "../../../../public/drop_right.svg";
import CategoryLabels from "@/features/ingredient/categorylabels";
import { CategoryType } from "@/types/category";

const inventoryData = [
  {
    id: 1,
    name: "양파",
    category: CategoryType.Vegetables,
    totalAmount: "2kg",
    expireDate: "D-2",
    details: [
      { purchaseDate: "2024-03-01", amount: "1kg", expireDate: "2024-03-10" },
      { purchaseDate: "2024-03-05", amount: "1kg", expireDate: "2024-03-15" },
    ],
  },
  {
    id: 2,
    name: "양고기",
    category: CategoryType.Meat,
    totalAmount: "2kg",
    expireDate: "D-2",
    details: [
      { purchaseDate: "2024-03-02", amount: "1.5kg", expireDate: "2024-03-09" },
      { purchaseDate: "2024-03-04", amount: "0.5kg", expireDate: "2024-03-11" },
    ],
  },
];

export default function InventoryPage() {
  const [expandedItems, setExpandedItems] = useState(new Set());

  const toggleExpand = (itemId: number) => {
    setExpandedItems((prev) => {
      const newSet = new Set(prev);
      if (newSet.has(itemId)) {
        newSet.delete(itemId);
      } else {
        newSet.add(itemId);
      }
      return newSet;
    });
  };

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
            {inventoryData.map((item) => (
              <div key={item.id}>
                <div
                  className={styles.tabledetail}
                  onClick={() => toggleExpand(item.id)}
                >
                  <div className={styles.itemname}>{item.name}</div>
                  <div className={styles.itemcategory}>
                    <CategoryLabels category={item.category} />
                  </div>
                  <div className={styles.itemamount}>{item.totalAmount}</div>
                  <div className={styles.itemexpiredate}>{item.expireDate}</div>
                  <div className={styles.itemarrow}>
                    <Image
                      src={
                        expandedItems.has(item.id) ? DropdownSVG : DroprightSVG
                      }
                      alt="expand"
                      width={25}
                      height={25}
                    />
                  </div>
                </div>
                {expandedItems.has(item.id) && (
                  <div className={styles.itemDetails}>
                    {item.details.map((detail, index) => (
                      <div key={index} className={styles.detailRow}>
                        <div className={styles.itemname}></div>
                        <div className={styles.itemname}>
                          구매일: {detail.purchaseDate}
                        </div>
                        <div className={styles.itemamount}>{detail.amount}</div>
                        <div className={styles.itemexpiredate}>
                          유통기한: {detail.expireDate}
                        </div>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
