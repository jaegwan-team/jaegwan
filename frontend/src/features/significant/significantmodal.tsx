import { SignificantModalProps } from "@/types/significantType";
import styles from "../../../styles/modals.module.css";
import { X, Check, Trash2 } from "lucide-react";
import { useState, useEffect } from "react";
import { confirmSignificant, deleteSignificant } from "@/services/api";

export default function SignificantModal({
  significantId,
  significantDate,
  significantDetail,
  onClose,
}: SignificantModalProps) {
  const [isLoading, setIsLoading] = useState(false);
  const [isMounted, setIsMounted] = useState(false);

  useEffect(() => {
    setIsMounted(true);
  }, []);

  const formattedDate = new Date(significantDate).toLocaleDateString("ko-KR", {
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  });

  const handleConfirm = async () => {
    try {
      setIsLoading(true);
      const params = {
        significantId: significantId,
      };
      await confirmSignificant(params);
      onClose();
    } catch (error) {
      console.error("Failed to confirm significant:", error);
      // 에러 처리 로직 추가 가능
    } finally {
      setIsLoading(false);
    }
  };

  const handleDelete = async () => {
    try {
      setIsLoading(true);
      const params = {
        significantId: significantId,
      };
      await deleteSignificant(params);
      onClose();
    } catch (error) {
      console.error("Failed to delete significant:", error);
      // 에러 처리 로직 추가 가능
    } finally {
      setIsLoading(false);
    }
  };

  if (!isMounted) {
    return null;
  }

  return (
    <div className={styles.modalOverlay}>
      <div className={`${styles.modalContainer} ${styles.significantModal}`}>
        {isLoading ? (
          <div className={styles.loading}>처리 중...</div>
        ) : (
          <>
            <div className={styles.modalHeader}>
              <h2 className={styles.modalTitle}>{formattedDate} 특이사항</h2>
              <button onClick={onClose} className={styles.closeButton}>
                <X size={24} />
              </button>
            </div>

            <div className={styles.formContainer}>
              <div className={styles.significantContent}>
                <p>{significantDetail}</p>
              </div>
            </div>

            <div className={styles.actionButtons}>
              <button
                onClick={handleConfirm}
                className={`${styles.actionButton} ${styles.checked}`}
                disabled={isLoading}
              >
                <Check size={20} />
                {isLoading ? "처리 중..." : "확인"}
              </button>
              <button
                onClick={handleDelete}
                className={`${styles.actionButton} ${styles.deleted}`}
                disabled={isLoading}
              >
                <Trash2 size={20} />
                {isLoading ? "처리 중..." : "삭제"}
              </button>
            </div>
          </>
        )}
      </div>
    </div>
  );
}
