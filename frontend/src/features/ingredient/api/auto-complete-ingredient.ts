// import { queryOptions } from "@tanstack/react-query";
// import { autocompleteIngredient } from "@/services/api";
// import { QueryConfig } from "@/lib/react-query";
// import { useQuery } from "@tanstack/react-query";

// interface AutocompleteRequest {
//   restaurantId: number;
//   word: string;
// }

// export const fetchAutocompleteIngredient = async (
//   request: AutocompleteRequest
// ) => {
//   const response = await autocompleteIngredient(request);
//   return response.data;
// };

// export const getAutocompleteQueryOptions = (request: AutocompleteRequest) => {
//   return queryOptions({
//     queryKey: ["ingredientAutocomplete", request],
//     queryFn: () => fetchAutocompleteIngredient(request),
//   });
// };

// type UseAutocompleteOptions = {
//   request: AutocompleteRequest;
//   queryConfig?: QueryConfig<typeof getAutocompleteQueryOptions>;
// };

// export const useAutocompleteIngredient = ({
//   request,
//   queryConfig,
// }: UseAutocompleteOptions) => {
//   return useQuery({
//     ...getAutocompleteQueryOptions(request),
//     ...queryConfig,
//   });
// };
