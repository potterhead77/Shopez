  import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Product } from '../_model/product.model';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-show-product-details',
  templateUrl: './show-product-details.component.html',
  styleUrls: ['./show-product-details.component.css']
})
export class ShowProductDetailsComponent implements OnInit {

  productDetails: Product[] = [];
  displayedColumns: string[] = ['productId', 'productName', 'productDescription', 'productDiscountedPrice','productActualPrice','Delete'];
  constructor(private productService: ProductService,
              private cdRef: ChangeDetectorRef
  ){ }
  ngOnInit(): void {
    this.getAllProducts();
  }

  public getAllProducts(){
      this.productService.getAllProducts().subscribe(
        (resp: Product[]) =>{
          console.log(resp);
          this.productDetails = [...resp];
        },
        (error: HttpErrorResponse) =>{
          console.log(error);
        }
      );
  }

  deleteProduct(productId: number) {
  this.productService.deleteProduct(productId).subscribe({
    next: (resp: string) => {
      console.log(resp); // "Product deleted successfully"
      this.getAllProducts(); // refresh the list
    },
    error: (err) => {
      console.error("Delete failed:", err);
    }
  });
  }

}
