import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-product-view-details',
  templateUrl: './product-view-details.component.html',
  styleUrls: ['./product-view-details.component.css']
})
export class ProductViewDetailsComponent implements OnInit {

    product: Product | undefined;
    constructor(private activatedRoute: ActivatedRoute,
      private router: Router,
      private productService: ProductService) {}

    ngOnInit(): void {
        this.product = this.activatedRoute.snapshot.data['product'];
        console.log(this.product);
    }

    buyProduct(productId: number): void {
            this.router.navigate(['/buyProduct'], { 
        queryParams: { isSingleProductCheckout: true, id: productId } 
      });
    }

    addToCart(productId: number): void {
      this.productService.addToCart(productId).subscribe(
        (response: string) => {
          console.log(response);
          alert('Product added to cart successfully!');
        },
        (error: any) => {
          console.error('Error adding product to cart:', error);
          alert('Failed to add product to cart. Please try again.');
        }
      );
    }
}
