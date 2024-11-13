export interface LoginProps {
  email: string;
  password: string;
}

export type Restaurant = {
  id: number | undefined;
  name: string | undefined;
  registerNumber: string | undefined;
};

export type UserProps = {
  id: number;
  name: string;
  role: string;
  email: string;
  imageUrl: string;
  restaurants: Restaurant[];
};
