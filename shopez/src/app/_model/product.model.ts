import { FileHandle } from "./file.handle.model";

 export interface Product{
    productId?: number; // <- Add this line
    productName: string,
    productDescription: string,
    productDiscountedPrice: number,
    productActualPrice: number,
    productImages: FileHandle[]
 }