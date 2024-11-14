// import { QueryConfig } from "@/lib/react-query";
// import { getIngredientList } from "@/services/api";
// import { queryOptions, useQuery } from "@tanstack/react-query";

// export const fetchGetIngredientList = async (restuarant: Record<string, number>) => {
//     const response = await getIngredientList(restuarant);
//     return response.data;
// };

// // 실제로 데이터 가져오는 함수
// export const getIngredientListQueryOptions = (restuarant: Record<string, number>) => {
//     return queryOptions({
//         queryKey: ["ingredientList", restuarant],
//         queryFn: () => fetchGetIngredientList(restuarant),
//     });
// };

// // 이 코드가 없으면, 실제 api 활용 시, 타입 에러가 발생하게 됨..
// type useGetIngredientListOptions = {
//     restuarant: Record<string, number>;
//     queryConfig?: QueryConfig<typeof getIngredientListQueryOptions>;
// }

// // fetch로 데이터 추출해서 형식에 맞게 반환하는 useQuery hooks
// // 실제 사용 위해선 타입이 필요 -> useGetIngredientListOptions 커스텀 타입 설정
// export const useGetIngredientList = ({
//     restuarant,
//     queryConfig,
// } : useGetIngredientListOptions) => {
//     return useQuery({
//         ...getIngredientListQueryOptions(restuarant),
//         ...queryConfig,
//     });
// };
