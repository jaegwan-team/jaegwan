import { useState, useEffect, useCallback } from "react";
import {
  X,
  ChevronLeft,
  ChevronRight,
  Check,
  Trash2,
  Plus,
  Image as ImageIcon,
} from "lucide-react";
import { useReceiptDetail } from "./api/get-receipt-detail";
import styles from "../../../styles/modals.module.css";
import { ItemType, UnitStatus } from "@/types/itemType";
import { ModalProps } from "@/types/modalType";
import { CategoryLabel } from "@/types/category";

const UNITS: UnitStatus[] = ["kg", "g", "l", "ml", "개"];

// 새로운 아이템 생성을 위한 기본 값
const DEFAULT_ITEM: ItemType = {
  name: "",
  unit: "개",
  amount: 0,
  expirationDate: new Date().toISOString().split("T")[0],
  category: 1,
  isChecked: "Yet",
};

export default function ReceiptModal({ receiptId, onClose }: ModalProps) {
  const { data: receiptDetail } = useReceiptDetail(receiptId);
  const [isImageModalOpen, setIsImageModalOpen] = useState(false);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [slideDirection, setSlideDirection] = useState("right");
  const [isAllProcessed, setIsAllProcessed] = useState(false);
  const [purchaseList, setPurchaseList] = useState<ItemType[]>([]);

  // 데이터 초기화
  useEffect(() => {
    if (receiptDetail) {
      // 받아온 데이터에 isChecked 추가
      const processedData = receiptDetail.map((item: ItemType) => ({
        ...item,
        isChecked: "Yet" as const,
      }));
      setPurchaseList(processedData);
    }
  }, [receiptDetail]);

  const checkAllProcessed = useCallback(() => {
    const allProcessed = purchaseList.every((item) => item.isChecked !== "Yet");
    setIsAllProcessed(allProcessed);
  }, [purchaseList]);

  useEffect(() => {
    checkAllProcessed();
  }, [checkAllProcessed]);

  const handleChange = <K extends keyof ItemType>(
    field: K,
    value: ItemType[K]
  ) => {
    setPurchaseList((prevList) => {
      const newList = [...prevList];
      newList[currentIndex] = {
        ...newList[currentIndex],
        [field]: value,
      };
      return newList;
    });
  };

  const handleNext = () => {
    if (currentIndex < purchaseList.length - 1) {
      setSlideDirection("right");
      setCurrentIndex((prev) => prev + 1);
    }
  };

  const handlePrevious = () => {
    if (currentIndex > 0) {
      setSlideDirection("left");
      setCurrentIndex((prev) => prev - 1);
    }
  };

  const handleCheckItem = () => {
    if (purchaseList[currentIndex].isChecked === "Checked") {
      handleChange("isChecked", "Yet");
    } else {
      handleChange("isChecked", "Checked");
    }
    checkAllProcessed();
  };

  const handleDeleteItem = () => {
    if (purchaseList[currentIndex].isChecked === "Deleted") {
      handleChange("isChecked", "Yet");
    } else {
      handleChange("isChecked", "Deleted");
    }
    checkAllProcessed();
  };

  // 이미지 모달
  const ImageModal = () => (
    <div
      className={styles.imageModalOverlay}
      onClick={() => setIsImageModalOpen(false)}
    >
      <div
        className={styles.imageModalContent}
        onClick={(e) => e.stopPropagation()}
      >
        <img
        // src={`/api/receipt/image/${receiptId}`}
        // alt="영수증"
        // className={styles.receiptImage}
        />
        <button
          onClick={() => setIsImageModalOpen(false)}
          className={styles.closeButton}
        >
          <X size={24} />
        </button>
      </div>
    </div>
  );

  // 새 아이템 추가
  const handleAddItem = () => {
    setPurchaseList((prev) => [...prev, { ...DEFAULT_ITEM }]);
    setCurrentIndex(purchaseList.length);
    setSlideDirection("right");
  };

  const handleComplete = () => {
    const updatedItems = purchaseList
      .filter((item) => item.isChecked !== "Deleted")
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      .map(({ isChecked, ...item }) => item);

    console.log("Updated items:", updatedItems);
    // API 호출 로직 추가 필요
    onClose();
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContainer}>
        <div className={styles.modalHeader}>
          <h2 className={styles.modalTitle}>구매내역</h2>
          <div className={styles.headerButtons}>
            <button
              onClick={() => setIsImageModalOpen(true)}
              className={styles.iconButton}
            >
              <ImageIcon size={20} />
              영수증 보기
            </button>
            <button onClick={onClose} className={styles.closeButton}>
              <X size={24} />
            </button>
          </div>
        </div>

        <div className={styles.progressBar}>
          {purchaseList.map((item, idx) => {
            let statusClass = styles.incomplete;
            if (idx === currentIndex) {
              statusClass = styles.active;
            } else if (item.isChecked === "Deleted") {
              statusClass = styles.deleted;
            } else if (item.isChecked === "Checked") {
              statusClass = styles.completed;
            }
            return (
              <div
                key={idx}
                className={`${styles.progressStep} ${statusClass}`}
              />
            );
          })}
        </div>

        <div className={styles.formContainer}>
          <div
            key={currentIndex}
            className={`${styles.formSlide} ${
              slideDirection === "right" ? styles.slideRight : styles.slideLeft
            }`}
          >
            <div className={styles.formGroup}>
              <label className={styles.formLabel}>품목명:</label>
              <input
                type="text"
                value={purchaseList[currentIndex].name}
                onChange={(e) => handleChange("name", e.target.value)}
                className={styles.formInput}
                disabled={purchaseList[currentIndex].isChecked === "Deleted"}
              />
            </div>

            <div className={styles.formGroup}>
              <label className={styles.formLabel}>카테고리:</label>
              <select
                value={String(purchaseList[currentIndex].category)}
                onChange={(e) =>
                  handleChange("category", Number(e.target.value))
                }
                className={styles.unitSelect}
                disabled={purchaseList[currentIndex].isChecked === "Deleted"}
              >
                {Object.entries(CategoryLabel).map(([value, label]) => (
                  <option key={value} value={value}>
                    {label}
                  </option>
                ))}
              </select>
            </div>

            <div className={styles.formGroup}>
              <label className={styles.formLabel}>수량:</label>
              <div className={styles.amountInputGroup}>
                <input
                  type="number"
                  value={purchaseList[currentIndex].amount}
                  onChange={(e) =>
                    handleChange("amount", Number(e.target.value))
                  }
                  className={styles.amountInput}
                  disabled={purchaseList[currentIndex].isChecked === "Deleted"}
                />
                <select
                  value={purchaseList[currentIndex].unit}
                  onChange={(e) =>
                    handleChange("unit", e.target.value as UnitStatus)
                  }
                  className={styles.unitSelect}
                  disabled={purchaseList[currentIndex].isChecked === "Deleted"}
                >
                  {UNITS.map((u) => (
                    <option key={u} value={u}>
                      {u}
                    </option>
                  ))}
                </select>
              </div>
            </div>

            <div className={styles.formGroup}>
              <label className={styles.formLabel}>유통기한:</label>
              <input
                type="date"
                value={purchaseList[currentIndex].expirationDate}
                onChange={(e) => handleChange("expirationDate", e.target.value)}
                className={styles.formInput}
                disabled={purchaseList[currentIndex].isChecked === "Deleted"}
              />
            </div>

            <div className={styles.actionButtons}>
              <button
                onClick={handleCheckItem}
                className={`${styles.actionButton} ${
                  purchaseList[currentIndex].isChecked === "Checked"
                    ? styles.checked
                    : ""
                }`}
                disabled={purchaseList[currentIndex].isChecked === "Deleted"}
              >
                <Check size={20} />
                확인
              </button>
              <button
                onClick={handleDeleteItem}
                className={`${styles.actionButton} ${
                  purchaseList[currentIndex].isChecked === "Deleted"
                    ? styles.deleted
                    : ""
                }`}
              >
                <Trash2 size={20} />
                삭제
              </button>
            </div>
          </div>
        </div>

        <div className={styles.navigationButtons}>
          <button
            onClick={handlePrevious}
            disabled={currentIndex === 0}
            className={styles.navButton}
          >
            <ChevronLeft size={24} />
          </button>

          {currentIndex === purchaseList.length - 1 ? (
            <button
              onClick={handleComplete}
              disabled={!isAllProcessed}
              className={`${styles.completeButton} ${
                isAllProcessed ? styles.active : ""
              }`}
            >
              완료
            </button>
          ) : (
            <button
              onClick={handleNext}
              disabled={currentIndex === purchaseList.length - 1}
              className={styles.navButton}
            >
              <ChevronRight size={24} />
            </button>
          )}
        </div>
        <div className={styles.navigationButtons}>
          <button
            onClick={handlePrevious}
            disabled={currentIndex === 0}
            className={styles.navButton}
          >
            <ChevronLeft size={24} />
          </button>

          <button onClick={handleAddItem} className={styles.addButton}>
            <Plus size={20} />새 품목 추가
          </button>

          {currentIndex === purchaseList.length - 1 ? (
            <button
              onClick={handleComplete}
              disabled={!isAllProcessed}
              className={`${styles.completeButton} ${
                isAllProcessed ? styles.active : ""
              }`}
            >
              완료
            </button>
          ) : (
            <button
              onClick={handleNext}
              disabled={currentIndex === purchaseList.length - 1}
              className={styles.navButton}
            >
              <ChevronRight size={24} />
            </button>
          )}
        </div>
      </div>

      {isImageModalOpen && <ImageModal />}
    </div>
  );
}
