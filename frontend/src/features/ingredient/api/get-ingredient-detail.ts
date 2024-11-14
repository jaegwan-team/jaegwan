// import { QueryConfig } from "@/lib/react-query";
// import { getIngredientDetail } from "@/services/api"
// import { queryOptions, useQuery } from "@tanstack/react-query";

// export const fetchGetIngredientDetail = async (id: number) => {
//     const response = await getIngredientDetail(id);
//     return response.data;
// };

// // 실제로 데이터 가져오는 함수
// export const getIngredientListQueryOptions = (id : number) => {
//     return queryOptions({
//         queryKey: ["ingredientDetail", id],
//         queryFn: () => fetchGetIngredientDetail(id),
//     });
// };

// type useIngredientDetailOptions = {
//     id: number,
//     queryConfig?: QueryConfig<typeof getIngredientListQueryOptions>;
// };

// export const useGetIngredientDetail = ({
//     id,
//     queryConfig,
// }: useIngredientDetailOptions) => {
//     return useQuery({
//         ...getIngredientListQueryOptions(id),
//         ...queryConfig,
//     })
// };
