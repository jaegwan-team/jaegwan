export type UnitStatus = "kg" | "g" | "l" | "ml" | "개";

export type IngredientType = {
  id: number;
  name: string;
  category: string;
  totalAmount: number;
  unit: UnitStatus;
  leftExpirationDay: number;
};

export type IngredientDetailType = {
  purchaseDate: string;
  amount: number;
  leftExpirationDay: number;
};
