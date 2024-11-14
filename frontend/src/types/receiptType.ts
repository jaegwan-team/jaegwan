import { UnitStatus } from "./itemType";

export type CheckStatus = "Yet" | "Checked" | "Deleted";

export type ReceiptProps = {
  id: number;
  mainIngredientName?: string;
  leftCount: number;
  createdDate?: string;
  confirmed: boolean;
};

export type ReceiptDetailTypes = {
  id: number;
  name: string;
  category: string;
  price: number;
  amount: number;
  unit: UnitStatus;
  expirationDate: string;
};

export type NewReceiptDetailTypes = {
  id: number;
  name: string;
  category: string;
  price: number;
  amount: number;
  unit: UnitStatus;
  expirationDate: string;
  isChecked: CheckStatus;
};

export type UpdatedReceiptDetailTypes = {
  receiptIngredientId: number;
  name: string;
  category: string;
  price: number;
  amount: number;
  unit: UnitStatus;
  expirationDate: string;
};
