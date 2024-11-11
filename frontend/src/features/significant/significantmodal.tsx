import styles from "../../../styles/modals.module.css";
import { X } from "lucide-react";

export type ThisProps = {
  onClose: () => void;
};

export default function SignificantModal({ onClose }: ThisProps) {
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
