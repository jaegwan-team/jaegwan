import { getReceiptList } from "@/services/api";
import { useQuery } from "@tanstack/react-query";

export const fetchReceiptList = async ( restuarent: Record<string, number> ) => {
    const response = await getReceiptList(restuarent);
    return response.data;
}

export const useReceiptList = (restuarent: Record<string, number>) => {
    return useQuery({
        queryKey: ["receiptList", restuarent],
        queryFn: () => fetchReceiptList(restuarent),
    });
}