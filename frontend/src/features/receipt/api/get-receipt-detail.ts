import { getReceiptDetail } from "@/services/api";
import { useQuery } from "@tanstack/react-query";
import { ItemType } from "@/types/itemType";

// useQuery hooks
export const useReceiptDetail = (id: number) => {
  return useQuery<ItemType[]>({
    queryKey: ["receiptDetail", id] as const,
    queryFn: async () => {
      const response = await getReceiptDetail(id);
      return response.data;
    },
  });
};
