"use client";

import { useCallback, useEffect, useState } from "react";
import styles from "../../../../styles/lists.module.css";
import Image from "next/image";
import CheckedSVG from "../../../../public/Check circle.svg";
import UncheckedSVG from "../../../../public/check_indeterminate_small.svg";
import SignificantModal from "@/features/significant/significantmodal";
import { useUser } from "@/features/users/api/login/loginUsers";
import { SignificantProps } from "@/types/significantType";
import { SignificantListParams } from "@/types/mainType";
import { getSignificantList } from "@/services/api";
import { Loader2 } from "lucide-react";

export default function SignificantPage() {
  const { user } = useUser();
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [SignificantId, setSignificantId] = useState<number>(-1);
  const [SignificantDate, setSignificantDate] = useState<string>("");
  const [SignificantDetail, setSignificantDetail] = useState<string>("");
  const [significantList, setSignificatnList] = useState<SignificantProps[]>(
    []
  );
  const [isModalOpen, setIsModalOpen] = useState(false);

  const fetchSignificantList = useCallback(async () => {
    try {
      setIsLoading(true);
      if (!user?.restaurants?.[0]?.id) return;

      const params: SignificantListParams = {
        restaurantId: user.restaurants[0].id,
      };
      const response = await getSignificantList(params);
      setSignificatnList(response.data.data);
    } catch (error) {
      console.error("Failed to fetch receipts:", error);
    } finally {
      setIsLoading(false);
    }
  }, [user?.restaurants]);

  useEffect(() => {
    fetchSignificantList();
  }, [fetchSignificantList]);

  const handleDetailClick = (significant: SignificantProps) => {
    if (significant) {
      if (significant.confirmed) {
        alert("이미 확인된 영수증입니다.");
      } else {
        setSignificantId(significant.significantId);
        setSignificantDate(significant.date);
        setSignificantDetail(significant.detail);
        setIsModalOpen(true);
      }
    }
  };

  const handleCloseModal = () => {
    setSignificantId(-1);
    setSignificantDate("");
    setSignificantDetail("");
    setIsModalOpen(false);
  };

  return (
    <div className={styles.content}>
      <div className={styles.container}>
        <div className={styles.title}>특이 사항</div>
        {isLoading ? (
          <div className={styles.spinnerContainer}>
            <Loader2 className={styles.spinner} size={40} />
          </div>
        ) : (
          <div className={styles.table}>
            <div className={styles.tableHeader}>
              <div className={styles.dateColumn}>일자</div>
              <div className={styles.itemColumn}>상세</div>
              <div className={styles.checkColumn}>확인 여부</div>
            </div>
            <div className={styles.tableBody}>
              {significantList.map((significant) => (
                <div
                  key={significant.significantId}
                  className={styles.tableRow}
                  onClick={() => handleDetailClick(significant)}
                >
                  <div className={styles.dateColumn}>{significant.date}</div>
                  <div className={styles.itemColumn}>{significant.detail}</div>
                  <div className={styles.checkColumn}>
                    {significant.confirmed ? (
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
        <SignificantModal
          significantId={SignificantId}
          significantDate={SignificantDate}
          significantDetail={SignificantDetail}
          onClose={handleCloseModal}
        />
      )}
    </div>
  );
}
