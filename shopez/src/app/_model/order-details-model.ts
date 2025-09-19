import { OrderQuantity } from "./order-quanity-model";

export interface OrderDetails {
  fullName: string;
  fullAddress: string;
  contactNumber: string;
  alternateContactNumber: string;
  orderProductQuantityList: OrderQuantity[];
}


