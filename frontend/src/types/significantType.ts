export type SignificantProps = {
  significantId: number;
  detail: string;
  date: string;
  confirmed: boolean;
};

export type SignificantModalProps = {
  significantId: number;
  significantDate: string;
  significantDetail: string;
  onClose: () => void;
};

export type SignificantParams = {
  significantId: number;
};
