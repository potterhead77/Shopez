import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Router } from '@angular/router';
import { Product } from './_model/product.model';
import { Observable, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { ProductService } from './_services/product.service';
import { ImageProcessingService } from './image-processing.service';

@Injectable({
  providedIn: 'root'
})
export class ProductResolveService implements Resolve<Product> {

  constructor(
    private productService: ProductService,
    private imageProcessingService: ImageProcessingService,
    private router: Router
  ) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product> {
    const id = Number(route.paramMap.get("productId"));

    if (id) {
      return this.productService.getProductbyId(id).pipe(
        map(p => {
          if (p) {
            return this.imageProcessingService.createImages(p);
          } else {
            this.router.navigate(['/']); // redirect if product is null
            return this.getProductDetails();
          }
        }),
        catchError(err => {
          console.error("Error fetching product:", err);
          this.router.navigate(['/']); // redirect if backend throws error
          return of(this.getProductDetails());
        })
      );
    } else {
      // invalid id in URL → redirect
      this.router.navigate(['/']);
      return of(this.getProductDetails());
    }
  }

  private getProductDetails(): Product {
    return {
      productId: 0,
      productName: "",
      productDescription: "",
      productDiscountedPrice: 0,
      productActualPrice: 0,
      productImages: []
    };
  }
}
