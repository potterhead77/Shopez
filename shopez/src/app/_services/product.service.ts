import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../_model/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private htppClient: HttpClient ) { }

  public addProduct(product: Product){
    return this.htppClient.post<Product>("http://localhost:8080/addNewProduct",product);
  }
}
