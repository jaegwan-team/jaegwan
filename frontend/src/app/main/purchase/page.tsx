"use client";

import { useCallback, useEffect, useState } from "react";
import styles from "../../../../styles/lists.module.css";
import Image from "next/image";
import CheckedSVG from "../../../../public/Check circle.svg";
import UncheckedSVG from "../../../../public/check_indeterminate_small.svg";
import ReceiptModal from "../../../features/receipt/receiptmodal";
import { getReceiptList } from "@/services/api";
import { useUser } from "@/features/users/api/login/loginUsers";
import { ReceiptProps } from "@/types/receiptType";

export default function PurchasePage() {
  const { user } = useUser();
  const [purchaseData, setPurchasedata] = useState<
    ReceiptProps[] | undefined
  >();
  const fetchReceipt = useCallback(async () => {
    if (!user?.restaurants?.[0]?.id) return;

    type ReceiptListParams = {
      restaurantId: number | undefined;
      all: boolean;
    };

    const params: ReceiptListParams = {
      restaurantId: user.restaurants[0].id,
      all: false,
    };
    const response = await getReceiptList(params);
    setPurchasedata(response.data);
  }, [user?.restaurants]);

  useEffect(() => {
    fetchReceipt();
  }, [fetchReceipt]);

  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleDetailClick = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className={styles.content}>
      <div className={styles.container}>
        <h1 className={styles.title}>구매 내역</h1>

        <div className={styles.table}>
          <div className={styles.tableHeader}>
            <div className={styles.dateColumn}>일자</div>
            <div className={styles.itemColumn}>품목</div>
            <div className={styles.checkColumn}>확인 여부</div>
          </div>

          <div className={styles.tableBody}>
            {purchaseData?.map((purchase) => (
              <div
                key={purchase.id}
                className={styles.tableRow}
                onClick={() => handleDetailClick()}
              >
                <div className={styles.dateColumn}>{purchase.createdDate}</div>
                <div className={styles.itemColumn}>
                  {purchase.mainIngredientName}
                  {purchase.leftCount > 0 && ` 외 ${purchase.leftCount}개 품목`}
                </div>
                <div className={styles.checkColumn}>
                  {purchase.confirmed ? (
                    <Image
                      src={CheckedSVG}
                      alt="checked"
                      width={15}
                      height={15}
                    />
                  ) : (
                    <Image
                      src={UncheckedSVG}
                      alt="checked"
                      width={15}
                      height={15}
                    />
                  )}
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
      {isModalOpen && <ReceiptModal receiptId={1} onClose={handleCloseModal} />}
    </div>
  );
}
