import { Component, OnInit } from '@angular/core';
import { ProductService } from '../_services/product.service';
import { Product } from '../_model/product.model';
import { map } from 'rxjs';
import { ImageProcessingService } from '../image-processing.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  productDetails: Product[] = []; 

  constructor(private productService: ProductService,
              private imageProcessingService: ImageProcessingService,
              private router: Router
  ){}

  ngOnInit(): void {  
       this.getAllProducts();
  }

  public getAllProducts(){
    this.productService.getAllProducts()
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

  showProductDetails(productId: number | undefined) {
    if (productId) {
      // Navigate to the product details page
      this.router.navigate(['/productViewDetails', {productId: productId}]);
    } else {
      throw new Error('Invalid product ID');
    }
  }
}
