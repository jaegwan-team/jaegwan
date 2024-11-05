import { ModalProps } from "@/types/modalType";
import styles from "../../../styles/modals.module.css";
import { X } from "lucide-react";

export default function SignificantModal({ onClose }: ModalProps) {
  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContainer}>
        <div className={styles.modalHeader}>
          <h2 className={styles.modalTitle}>2014.02.13 특이사항</h2>
          <button onClick={onClose} className={styles.closeButton}>
            <X size={24} />
          </button>
        </div>
      </div>
    </div>
  );
}
