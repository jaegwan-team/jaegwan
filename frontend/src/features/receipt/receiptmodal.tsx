import { useState, useEffect, useCallback } from "react";
import { X, ChevronLeft, ChevronRight, Check, Trash2 } from "lucide-react";
import styles from "../../../styles/modals.module.css";
import { ItemType, UnitStatus } from "@/types/itemType";
import { ModalProps } from "@/types/modalType";
import { CategoryLabel } from "@/types/category";

const UNITS: UnitStatus[] = ["kg", "g", "l", "ml", "개"];
const pList: ItemType[] = [
  {
    id: 1,
    name: "양파",
    unit: "kg",
    amount: 2,
    expireDate: "2024/10/29 13:25",
    isChecked: "Yet",
    category: 1,
  },
  {
    id: 2,
    name: "대파",
    unit: "g",
    amount: 300,
    expireDate: "2024/10/29 13:25",
    isChecked: "Yet",
    category: 1,
  },
  {
    id: 3,
    name: "우유",
    unit: "l",
    amount: 3,
    expireDate: "2024/10/29 13:25",
    isChecked: "Yet",
    category: 5,
  },
  {
    id: 4,
    name: "두부",
    unit: "개",
    amount: 4,
    expireDate: "2024/10/29 13:25",
    isChecked: "Yet",
    category: 4,
  },
];

export default function ReceiptModal({ restaurantId, onClose }: ModalProps) {
  const [currentIndex, setCurrentIndex] = useState(0);
  const [slideDirection, setSlideDirection] = useState("right");
  const [isAllProcessed, setIsAllProcessed] = useState(false);
  const [purchaseList, setPurchaseList] = useState<ItemType[]>(pList);

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

  const handleComplete = () => {
    const updatedItems = purchaseList.filter(
      (item) => !(item.isChecked == "Deleted")
    );

    console.log("Updated items:", updatedItems);

    onClose();
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

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContainer}>
        <div className={styles.modalHeader}>
          <h2 className={styles.modalTitle}>2019.12.24 구매내역</h2>
          <button onClick={onClose} className={styles.closeButton}>
            <X size={24} />
          </button>
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
                value={purchaseList[currentIndex].expireDate}
                onChange={(e) => handleChange("expireDate", e.target.value)}
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
      </div>
    </div>
  );
}
