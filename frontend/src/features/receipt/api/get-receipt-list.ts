import { getReceiptList } from "@/services/api";
import { useQuery } from "@tanstack/react-query";
import { ReceiptProps } from "@/types/receiptType";

export const fetchReceiptList = async (restuarent: Record<string, number>) => {
  const response = await getReceiptList(restuarent);
  return response.data;
};

export const useReceiptList = (restuarent: Record<string, number>) => {
  return useQuery<ReceiptProps[]>({
    queryKey: ["receiptList", restuarent],
    queryFn: () => fetchReceiptList(restuarent),
  });
};
