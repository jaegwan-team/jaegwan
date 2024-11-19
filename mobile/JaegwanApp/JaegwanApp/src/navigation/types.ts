export type RootStackParamList = {
  Login: undefined;
  Main: undefined;
  Camera: undefined;
};

declare global {
  namespace ReactNavigation {
    interface RootParamList extends RootStackParamList {}
  }
}
