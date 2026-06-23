export {};

declare global {
    interface Window {
        checkout: () => void;
        decrease: (id: number) => void;
        increase: (id: number) => void;
        removeItem: (id: number) => void;
        addProduct: (product: any) => void;
    }
}