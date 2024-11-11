export type CheckStatus = "Yet" | "Checked" | "Deleted";

export type UnitStatus = "kg" | "g" | "l" | "ml" | "개";

export type ItemType = {
  id?: number;
  name?: string;
  unit?: UnitStatus;
  amount?: number;
  expireDate?: string;
  isChecked?: CheckStatus;
  category?: number;
};
