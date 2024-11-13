export interface LoginProps {
  email: string;
  password: string;
}

export type Restaurant = {
  id: number;
  name: string;
  registerNumber: string;
};

export type UserProps = {
  id: number;
  name: string;
  role: string;
  email: string;
  imageUrl: string;
  restaurants: Restaurant[];
};
