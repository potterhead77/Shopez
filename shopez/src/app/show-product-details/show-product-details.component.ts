  import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Product } from '../_model/product.model';
import { MatDialog } from '@angular/material/dialog';
import { ShowProductImagesDialogComponent } from '../show-product-images-dialog/show-product-images-dialog.component';
import { ImageProcessingService } from '../image-processing.service';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';



@Component({
  selector: 'app-show-product-details',
  templateUrl: './show-product-details.component.html',
  styleUrls: ['./show-product-details.component.css']
})
export class ShowProductDetailsComponent implements OnInit {

  productDetails: Product[] = [];
  displayedColumns: string[] = ['productId', 'productName', 'productDescription', 'productDiscountedPrice','productActualPrice','Images','Edit','Delete'];
  constructor(private productService: ProductService,
              public imagesDialog: MatDialog,
              private imageProcessingService : ImageProcessingService, 
              private router: Router

  ){ }
  ngOnInit(): void {
    this.getAllProducts();
  }

  public getAllProducts(){
      this.productService.getAllProducts(0)
      .pipe(
          map((x: Product[],i)=> x.map((product: Product)=> this.imageProcessingService.createImages(product))
        ) 
      )
      .subscribe(
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

  showProductImages(product: Product ){
    this.imagesDialog.open(ShowProductImagesDialogComponent,{
      data: {
          images: product.productImages
      },
      height: '500px',
      width: '800px'
    });
  }

  editProduct(productId:number) {
    this.router.navigate(['/addNewProduct',{productId: productId}]);
  }


}
