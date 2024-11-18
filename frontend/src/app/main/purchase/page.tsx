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
import { ReceiptListParams } from "@/types/mainType";

export default function PurchasePage() {
  const { user } = useUser();
  const [receiptId, setReceiptId] = useState<number>(-1);
  const [isLoading, setIsLoading] = useState(true);
  const [purchaseData, setPurchasedata] = useState<
    ReceiptProps[] | undefined
  >();

  const fetchReceipt = useCallback(async () => {
    try {
      setIsLoading(true);
      if (!user?.restaurants?.[0]?.id) return;

      const params: ReceiptListParams = {
        restaurantId: user.restaurants[0].id,
        isAll: true,
      };
      const response = await getReceiptList(params);
      setPurchasedata(response.data.data);
    } catch (error) {
      console.error("Failed to fetch receipts:", error);
    } finally {
      setIsLoading(false); // 데이터 가져온 후 로딩 종료
    }
  }, [user?.restaurants]);

  useEffect(() => {
    fetchReceipt();
  }, [fetchReceipt]);

  const [isModalOpen, setIsModalOpen] = useState(false);

  const handleDetailClick = (receiptId: number) => {
    const selectedReceipt = purchaseData?.find(
      (purchase) => purchase.id === receiptId
    );

    if (selectedReceipt) {
      if (selectedReceipt.confirmed) {
        alert("이미 확인된 영수증입니다.");
      } else {
        setReceiptId(receiptId);
        setIsModalOpen(true);
      }
    }
  };

  const handleCloseModal = () => {
    setReceiptId(-1);
    setIsModalOpen(false);
  };

  return (
    <div className={styles.content}>
      <div className={styles.container}>
        <h1 className={styles.title}>구매 내역</h1>
        {isLoading ? (
          <div className={styles.loading}>로딩 중...</div>
        ) : (
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
                  onClick={() => handleDetailClick(purchase.id)}
                >
                  <div className={styles.dateColumn}>
                    {purchase.createdDate}
                  </div>
                  <div className={styles.itemColumn}>
                    {purchase.mainIngredientName
                      ? `${purchase.mainIngredientName} 외 `
                      : ``}
                    {purchase.leftCount > 0 && `${purchase.leftCount}개 품목`}
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
        )}
      </div>
      {isModalOpen && (
        <ReceiptModal receiptId={receiptId} onClose={handleCloseModal} />
      )}
    </div>
  );
}
