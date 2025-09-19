import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../_model/product.model';
import { Observable } from 'rxjs';
import { OrderDetails } from '../_model/order-details-model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient ) { }

  public addProduct(product: FormData): Observable<Product>{
    return this.httpClient.post<Product>("http://localhost:8080/addNewProduct",product);
  }

  public getAllProducts(){
    return this.httpClient.get<Product[]>("http://localhost:8080/getAllProducts")
  }

  public deleteProduct(productId: number): Observable<string> {
    return this.httpClient.delete("http://localhost:8080/deleteProduct/" + productId, {
      responseType: 'text'
    });
  }

  public getProductbyId(productId: number): Observable<Product>{
    return this.httpClient.get<Product>("http://localhost:8080/getProductbyId/"+productId,{
      responseType: 'json'
    });
  }

  public getProductDetails(isSingleProductCheckout: boolean ,productId: number ): Observable<Product[]> {
    return this.httpClient.get<Product[]>("http://localhost:8080/getProductDetails/"+isSingleProductCheckout+"/"+productId,{
      responseType: 'json'
    });
  }
  
  public placeOrder(orderDetails: OrderDetails): Observable<string> {
    return this.httpClient.post("http://localhost:8080/placeOrder", orderDetails, {
      responseType: 'text'
    });
  }


}
