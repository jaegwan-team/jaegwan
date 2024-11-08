'use client';

import { useGetIngredientList } from "@/features/ingredient/api/get-ingredient-list";

export default function Home() {
  
  const restuarant: Record<string, number> = {
    restaurantId: 1,
  };
    
  const ingredientQuery = useGetIngredientList({ restuarant, });

  if (ingredientQuery.isLoading) {
    return (<div>Loading...</div>);
  }

  const ingredient = ingredientQuery.data?.data;

  if (!ingredient) {
    console.log("not render!")
    return null;
  }

  return (
    <div>
      <h1 className="text-3xl font-bold underline">Hello world!</h1>
      {/* test react-query */}
      <ul>
        {ingredient.map((data : Record<string, string | number>) => (
          <li key={data.id}>{data.category}</li>
        ))}
      </ul>
    </div>
  );
}
