import { getIngredientDetail } from "@/services/api"
import { useQuery } from "@tanstack/react-query";

export const fetchGetIngredientDetail = async (id : number) => {
    const response = await getIngredientDetail(id);
    return response.data;
}

export const useGetIngredientDetail = (id : number) => {
    return useQuery({
        queryKey: [],
        queryFn: () => getIngredientDetail(id),
    });
}