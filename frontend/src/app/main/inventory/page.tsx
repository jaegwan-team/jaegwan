"use client";

import { useCallback, useEffect, useState } from "react";
import styles from "../../../../styles/inventory.module.css";
import Image from "next/image";
import DropdownSVG from "../../../../public/drop_down.svg";
import DroprightSVG from "../../../../public/drop_right.svg";
import CategoryLabels from "@/features/ingredient/categorylabels";
import { getIngredientDetail, getIngredientList } from "@/services/api";
import { useUser } from "@/features/users/api/login/loginUsers";
import { IngredientDetailType, IngredientType } from "@/types/itemType";

export default function InventoryPage() {
  const { user } = useUser();
  const [ingredientList, setIngredientList] = useState<IngredientType[]>([]);
  const [expandedItems, setExpandedItems] = useState(new Set<number>());
  const [ingredientDetails, setIngredientDetails] = useState<
    Record<number, IngredientDetailType[]>
  >({});

  const fetchIngredientList = useCallback(async () => {
    const params = {
      restaurantId: user!.restaurants[0]!.id,
    };
    const response = await getIngredientList(params);
    console.log(response.data);
    setIngredientList(response.data.data);
    return response;
  }, [user]);

  const fetchIngredeintDetails = useCallback(async (itemId: number) => {
    try {
      const response = await getIngredientDetail(itemId);
      console.log(response);
      setIngredientDetails((prev) => ({
        ...prev,
        [itemId]: response.data.data,
      }));
    } catch (error) {
      console.error(`Failed to fetch details for item ${itemId}:`, error);
    }
  }, []);

  const toggleExpand = async (itemId: number) => {
    setExpandedItems((prev) => {
      const newSet = new Set(prev);
      if (newSet.has(itemId)) {
        newSet.delete(itemId);
      } else {
        newSet.add(itemId);
        if (!ingredientDetails[itemId]) {
          fetchIngredeintDetails(itemId);
        }
      }
      return newSet;
    });
  };

  useEffect(() => {
    fetchIngredientList();
  }, [fetchIngredientList]);

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
            {ingredientList.map((item) => (
              <div key={item.id}>
                <div
                  className={styles.tabledetail}
                  onClick={() => toggleExpand(item.id)}
                >
                  <div className={styles.itemname}>{item.name}</div>
                  <div className={styles.itemcategory}>
                    <CategoryLabels category={item.category} />
                  </div>
                  <div className={styles.itemamount}>
                    {item.totalAmount}
                    {item.unit}
                  </div>
                  <div className={styles.itemexpiredate}>
                    D{item.leftExpirationDay * -1}
                  </div>
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
                    {ingredientDetails[item.id].map((detail, index) => (
                      <div key={index} className={styles.detailRow}>
                        <div className={styles.itemname}></div>
                        <div className={styles.itemname}>
                          구매일: {detail.purchaseDate}
                        </div>
                        <div className={styles.itemamount}>
                          {detail.amount}
                          {item.unit}
                        </div>
                        <div className={styles.itemexpiredate}>
                          D{detail.leftExpirationDay * -1}
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
