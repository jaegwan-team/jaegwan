export type UnitStatus = "kg" | "g" | "l" | "ml" | "개";

export type IngredientType = {
  id: number;
  name: string | undefined;
  category: string | undefined;
  totalAmount: number | undefined;
  unit: UnitStatus | undefined;
  leftExpirationDay: number | undefined;
};

export type IngredientDetailType = {
  purchaseDate: string | undefined;
  amount: number | undefined;
  leftExpirationDay: number | undefined;
};
