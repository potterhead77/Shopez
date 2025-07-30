import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Product } from './_model/product.model';
import { map, Observable, of } from 'rxjs';
import { ProductService } from './_services/product.service';
import { ImageProcessingService } from './image-processing.service';

@Injectable({
  providedIn: 'root'
})
export class ProductResolveService implements Resolve<Product> {

  constructor(private productService: ProductService, private imageProcessingService: ImageProcessingService) { }
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product> {
    const id=Number(route.paramMap.get("productId"));

    if(id){
        return this.productService.getProductbyId(id)
        .pipe(
            map(p => this.imageProcessingService.createImages(p))
        );
    }else{
      return of(this.getProductDetails());
    }
  }

  getProductDetails(){
    return{
      productName: "",
      productDescription: "",
      productDiscountedPrice : 0,
      productActualPrice: 0,
      productImages:[]
    };
  }
}
