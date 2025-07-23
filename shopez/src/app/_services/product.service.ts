import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../_model/product.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private httpClient: HttpClient ) { }

  public addProduct(product: FormData): Observable<Product>{
    return this.httpClient.post<Product>("http://localhost:8080/addNewProduct",product);
  }
}
