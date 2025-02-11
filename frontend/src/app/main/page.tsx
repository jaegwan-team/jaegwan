"use client";
import Image from "next/image";
import CheckedSVG from "../../../public/Check circle.svg";
import UncheckedSVG from "../../../public/check_indeterminate_small.svg";
import ArrowSVG from "../../../public/Arrow right.svg";

import styles from "../../../styles/main.module.css";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ChartOptions,
} from "chart.js";
import { Bar } from "react-chartjs-2";
import { useUser } from "@/features/users/api/login/loginUsers";
import { ReceiptProps } from "@/types/receiptType";
import { useCallback, useEffect, useState } from "react";
import { getReceiptList, getSignificantListUnchecked } from "@/services/api";
import { ReceiptListParams, SignificantListParams } from "@/types/mainType";
import { SignificantProps } from "@/types/significantType";
import { useRouter } from "next/navigation";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

export default function MainPage() {
  const today = new Date();
  const year = today.getFullYear();
  const month = today.getMonth() + 1;
  const date = today.getDate();

  const { user } = useUser();

  // 요일 계산
  const days = ["일", "월", "화", "수", "목", "금", "토"];
  const dayName = days[today.getDay()];

  // 주차 계산
  const getWeekNumber = (date: Date) => {
    const firstDayOfMonth = new Date(date.getFullYear(), date.getMonth(), 1);
    const firstWeekday = firstDayOfMonth.getDay();
    return Math.ceil((date.getDate() + firstWeekday) / 7);
  };
  const weekNumber = getWeekNumber(today);

  const weekdays = [
    "월요일",
    "화요일",
    "수요일",
    "목요일",
    "금요일",
    "토요일",
    "일요일",
  ];

  const data = {
    labels: weekdays,
    datasets: [
      {
        label: "점심 매출",
        data: [450000, 380000, 0, 0, 0, 0, 0],
        backgroundColor: "rgba(152, 251, 152, 0.7)",
        borderWidth: 1,
      },
      {
        label: "저녁 매출",
        data: [380000, 200000, 0, 0, 0, 0, 0],
        borderWidth: 1,
        backgroundColor: "rgba(162, 236, 216, 0.7)",
      },
    ],
  };
  const options: ChartOptions<"bar"> = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: {
        position: "top" as const,
      },
      title: {
        display: false,
      },
    },
    scales: {
      y: {
        type: "linear",
        beginAtZero: true,
        ticks: {
          callback: function (value: number | string) {
            if (typeof value === "number") {
              return value.toLocaleString() + "원";
            }
            return value;
          },
        },
      },
    },
  };

  const router = useRouter();

  const handleGotoPurchase = () => {
    router.push("/main/purchase");
  };

  const handleGotoSignificant = () => {
    router.push("/main/significant");
  };

  const [uncheckedReceipts, setUncheckedReceipts] = useState<ReceiptProps[]>(
    []
  );

  const [uncheckedSignificants, setUncheckedSignificants] = useState<
    SignificantProps[]
  >([]);

  const fetchData = useCallback(async () => {
    if (!user?.restaurants?.[0]?.id) return;

    const params: ReceiptListParams = {
      restaurantId: user.restaurants[0].id,
      isAll: false,
    };

    const sparams: SignificantListParams = {
      restaurantId: user.restaurants[0].id,
    };

    const response = await getReceiptList(params);
    console.log(response);
    setUncheckedReceipts(response.data.data);

    const sresponse = await getSignificantListUnchecked(sparams);
    console.log(sresponse);
    setUncheckedSignificants(sresponse.data.data);
  }, [user?.restaurants]);

  useEffect(() => {
    fetchData();
  }, [fetchData]);

  return (
    <div className={styles.content}>
      <div className={styles.maincontent}>
        <div className={styles.overview}>
          <div className={styles.overviewtxt}>
            <div className={styles.overviewtitle}>
              {year}년 {month}월 {weekNumber}주차 매출
            </div>
            <div className={styles.overviewdate}>
              {month}월 {date}일 {dayName}요일
            </div>
            <div className={styles.overviewsale}>
              <span className={styles.saleamount}>1,350,000</span>원
            </div>
            <div className={styles.overviewcompare}>
              지난 주 대비 : +{" "}
              <span className={styles.compareamount}>230,000</span>원
            </div>
          </div>
          <div className={styles.overviewchart}>
            <Bar options={options} data={data} />
          </div>
        </div>

        <div className={styles.lists}>
          <div className={styles.listbox} onClick={handleGotoPurchase}>
            <div className={styles.listtitle}>
              <div>
                구매 내역{" "}
                <span className={styles.newlist}>
                  (신규 : {uncheckedReceipts?.length}건)
                </span>
              </div>
              <div>
                <Image src={ArrowSVG} alt="checked" width={20} height={20} />
              </div>
            </div>
            <div className={styles.listtable}>
              <div className={styles.tablehead}>
                <div className={styles.tableheaddate}>일자</div>
                <div className={styles.tableheaditem}>품목</div>
                <div className={styles.tableheadcheck}>확인 여부</div>
              </div>
              {(uncheckedReceipts || [])
                .slice(0, 3)
                .map((receipt: ReceiptProps) => {
                  return (
                    <div className={styles.tabledetail} key={receipt.id}>
                      <div className={styles.tableheaddate}>
                        {receipt.createdDate}
                      </div>
                      <div className={styles.tableheaditem}>
                        {receipt.mainIngredientName
                          ? `${receipt.mainIngredientName} 외 `
                          : ``}
                        {receipt.leftCount > 0 && `${receipt.leftCount}개 품목`}
                      </div>
                      <div className={styles.tableheadcheck}>
                        <Image
                          src={receipt.confirmed ? CheckedSVG : UncheckedSVG}
                          alt={receipt.confirmed ? "checked" : "unchecked"}
                          width={15}
                          height={15}
                        />
                      </div>
                    </div>
                  );
                })}
            </div>
          </div>

          <div className={styles.listbox} onClick={handleGotoSignificant}>
            {" "}
            <div className={styles.listtitle}>
              <div>
                특이 사항{" "}
                <span className={styles.newlist}>
                  (신규 {uncheckedSignificants?.length}건)
                </span>
              </div>
              <div>
                <Image src={ArrowSVG} alt="checked" width={20} height={20} />
              </div>
            </div>
            <div className={styles.listtable}>
              <div className={styles.tablehead}>
                <div className={styles.tableheaddate}>일자</div>
                <div className={styles.tableheaditem}>상세</div>
                <div className={styles.tableheadcheck}>확인 여부</div>
              </div>
              {(uncheckedSignificants || [])
                .slice(0, 3)
                .map((significant: SignificantProps) => {
                  return (
                    <div
                      className={styles.tabledetail}
                      key={significant.significantId}
                    >
                      <div className={styles.tableheaddate}>
                        {significant.date}
                      </div>
                      <div className={styles.tableheaditem}>
                        {significant.detail}
                      </div>
                      <div className={styles.tableheadcheck}>
                        <Image
                          src={
                            significant.confirmed ? CheckedSVG : UncheckedSVG
                          }
                          alt={significant.confirmed ? "checked" : "unchecked"}
                          width={15}
                          height={15}
                        />
                      </div>
                    </div>
                  );
                })}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
