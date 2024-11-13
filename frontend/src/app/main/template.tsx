"use client";

import Header from "@/features/ui/header";
import styles from "../../../styles/main.module.css";
import TabNavigation from "../../features/ui/TabNavigation";
import UserProvider from "../../features/users/api/login/loginUsers";

export default function MainTemplate({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <UserProvider>
      <div className={styles.layout}>
        <Header />
        <TabNavigation />
        {children}
<<<<<<< HEAD:frontend/src/app/main/providers.tsx
      </div>  
    </UserProvider>
      
=======
      </div>
    </UserProvider>
>>>>>>> 9ce8e581082a55c51ae2e829176c6a9501b06133:frontend/src/app/main/template.tsx
  );
}
