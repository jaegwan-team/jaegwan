"use client";

import { useState } from "react";
import styles from "../../../../styles/lists.module.css";
import Image from "next/image";
import CheckedSVG from "../../../../public/Check circle.svg";
import UncheckedSVG from "../../../../public/check_indeterminate_small.svg";
import SignificantModal from "@/features/significant/significantmodal";

const significantList = [
  {
    id: 1,
    date: "2021/09/21 10:30",
    detail: "까르보나라 조리 실수",
    isChecked: false,
  },
  {
    id: 2,
    date: "2012/10/17 13:20",
    detail: "밀가루 터짐",
    isChecked: true,
  },
];

export default function SignificantPage() {
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
        <div className={styles.title}>특이 사항</div>
        <div className={styles.table}>
          <div className={styles.tableHeader}>
            <div className={styles.dateColumn}>일자</div>
            <div className={styles.itemColumn}>상세</div>
            <div className={styles.checkColumn}>확인 여부</div>
          </div>
          <div className={styles.tableBody}>
            {significantList.map((significant) => (
              <div
                key={significant.id}
                className={styles.tableRow}
                onClick={() => handleDetailClick()}
              >
                <div className={styles.dateColumn}>{significant.date}</div>
                <div className={styles.itemColumn}>{significant.detail}</div>
                <div className={styles.checkColumn}>
                  {significant.isChecked ? (
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
      {isModalOpen && <SignificantModal onClose={handleCloseModal} />}
    </div>
  );
}
