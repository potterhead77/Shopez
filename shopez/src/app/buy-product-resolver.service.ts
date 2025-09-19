import { Injectable } from '@angular/core';
import { Product } from './_model/product.model';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable, of, OperatorFunction, pipe } from 'rxjs';
import { ProductService } from './_services/product.service';
import { ImageProcessingService } from './image-processing.service';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BuyProductResolverService implements Resolve<Product[]> {
  constructor(private productService: ProductService,
    private imageProcessingService: ImageProcessingService
  ) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Product[]> {
    const idParam = route.queryParamMap.get('id');
    const checkoutParam = route.queryParamMap.get('isSingleProductCheckout');

    if (!idParam || !checkoutParam) {
      console.error("Missing route params for checkout");
      return of([]);
    }

    const id = Number(idParam);
    const isSingleProductCheckout = checkoutParam === 'true';

    return this.productService.getProductDetails(isSingleProductCheckout, id).pipe(
      map((x: Product[]) => x.map((product: Product) => this.imageProcessingService.createImages(product)))
    );
  }
}

