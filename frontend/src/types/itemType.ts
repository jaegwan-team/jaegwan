export type CheckStatus = "Yet" | "Checked" | "Deleted";

export type UnitStatus = "kg" | "g" | "l" | "ml" | "개";

export type ItemType = {
  receiptIngredientId?: number;
  name?: string;
  unit?: UnitStatus;
  amount?: number;
  expirationDate?: string;
  isChecked?: CheckStatus;
  category?: number;
};
