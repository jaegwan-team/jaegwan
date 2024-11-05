"use client";

import { useState } from "react";
import styles from "../../../../styles/lists.module.css";
import Image from "next/image";
import CheckedSVG from "../../../../public/Check circle.svg";
import UncheckedSVG from "../../../../public/check_indeterminate_small.svg";
import ReceiptModal from "../../../features/receipt/receiptmodal";

const purchaseData = [
  {
    id: 1,
    date: "2024/10/29 13:25",
    summary: "양파 외 25개 품목",
    isChecked: false,
    items: [
      {
        id: 1,
        name: "양파",
        amount: "2kg",
        expireDate: "2024-11-05",
        isChecked: false,
      },
      {
        id: 2,
        name: "당근",
        amount: "1kg",
        expireDate: "2024-11-07",
        isChecked: false,
      },
      {
        id: 3,
        name: "감자",
        amount: "3kg",
        expireDate: "2024-11-10",
        isChecked: false,
      },
    ],
  },
  {
    id: 2,
    date: "2024/10/29 13:25",
    summary: "양고기 외 25개 품목",
    isChecked: true,
    items: [
      {
        id: 1,
        name: "양고기",
        amount: "1kg",
        expireDate: "2024-11-03",
        isChecked: true,
      },
      {
        id: 2,
        name: "마늘",
        amount: "500g",
        expireDate: "2024-11-15",
        isChecked: true,
      },
      {
        id: 3,
        name: "양배추",
        amount: "1개",
        expireDate: "2024-11-08",
        isChecked: true,
      },
    ],
  },
];

export default function PurchasePage() {
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
            {purchaseData.map((purchase) => (
              <div
                key={purchase.id}
                className={styles.tableRow}
                onClick={() => handleDetailClick()}
              >
                <div className={styles.dateColumn}>{purchase.date}</div>
                <div className={styles.itemColumn}>{purchase.summary}</div>
                <div className={styles.checkColumn}>
                  {purchase.isChecked ? (
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
      {isModalOpen && <ReceiptModal onClose={handleCloseModal} />}
    </div>
  );
}
