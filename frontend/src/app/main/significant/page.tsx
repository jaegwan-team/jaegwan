import styles from "../../../../styles/lists.module.css";
import Image from "next/image";
import CheckedSVG from "../../../../public/Check circle.svg";
import UncheckedSVG from "../../../../public/check_indeterminate_small.svg";

export default function SignificantPage() {
  return (
    <div className={styles.content}>
      <div className={styles.listbox}>
        <div className={styles.listtitle}>특이 사항</div>
        <div className={styles.listtable}>
          <div className={styles.tablehead}>
            <div className={styles.tableheaddate}>일자</div>
            <div className={styles.tableheaditem}>상세</div>
            <div className={styles.tableheadcheck}>확인 여부</div>
          </div>
          <div className={styles.tabledetails}>
            <div className={styles.tabledetail}>
              <div className={styles.tableheaddate}>2024/10/29 13:25</div>
              <div className={styles.tableheaditem}>까르보나라 조리 실수</div>
              <div className={styles.tableheadcheck}>
                <Image
                  src={UncheckedSVG}
                  alt="checked"
                  width={15}
                  height={15}
                />
              </div>
            </div>
            <div className={styles.tabledetail}>
              <div className={styles.tableheaddate}>2024/10/29 13:25</div>
              <div className={styles.tableheaditem}>밀가루 터져서 폐기</div>
              <div className={styles.tableheadcheck}>
                <Image src={CheckedSVG} alt="checked" width={15} height={15} />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
