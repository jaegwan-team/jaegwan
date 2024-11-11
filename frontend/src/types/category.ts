export enum CategoryType {
  Vegetables = "1",
  Fruits = "2",
  Meat = "3",
  Seafood = "4",
  Dairy = "5",
  Grains = "6",
  Spices = "7",
  Herbs = "8",
  Oils = "9",
}

export const CategoryLabel: Record<CategoryType, string> = {
  [CategoryType.Vegetables]: "채소",
  [CategoryType.Fruits]: "과일",
  [CategoryType.Meat]: "고기",
  [CategoryType.Seafood]: "해산물",
  [CategoryType.Dairy]: "유제품",
  [CategoryType.Grains]: "곡물",
  [CategoryType.Spices]: "향신료",
  [CategoryType.Herbs]: "허브",
  [CategoryType.Oils]: "오일",
};

export const getCategoryLabel = (categoryNumber: number): string => {
  const categoryType = String(categoryNumber) as CategoryType;
  return CategoryLabel[categoryType] || "알 수 없음";
};
