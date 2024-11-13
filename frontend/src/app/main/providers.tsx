"use client";

import { UserProvider } from "../../features/users/api/login/loginUsers";

export function Providers({ children }: { children: React.ReactNode }) {
  return <UserProvider>{children}</UserProvider>;
}
