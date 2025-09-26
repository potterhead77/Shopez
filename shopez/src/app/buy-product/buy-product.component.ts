import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { OrderDetails } from '../_model/order-details-model';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from '../_model/product.model';
import { ProductService } from '../_services/product.service';

@Component({
  selector: 'app-buy-product',
  templateUrl: './buy-product.component.html',
  styleUrls: ['./buy-product.component.css']
})
export class BuyProductComponent implements OnInit {



  productDetails: Product[] = [];

  orderDetails : OrderDetails = {
    fullName: '',
    fullAddress: '',
    contactNumber: '',
    alternateContactNumber: '',
    orderProductQuantityList: [], // <-- renamed from productQuantityList
  }

  constructor(private activatedRoute: ActivatedRoute,
    private productService: ProductService,
    private router: Router,
  ) { }

  ngOnInit(): void {
    console.log('BuyProductComponent ngOnInit called');
    this.productDetails = this.activatedRoute.snapshot.data['product'] || [];
    this.productDetails.forEach(x => {
      this.orderDetails.orderProductQuantityList.push({
        productId: x.productId!,
        quantity: 1
      });
    });
    console.log(this.productDetails);
    console.log(this.orderDetails);

    
  }

  public placeOrder(orderForm: NgForm): void {
    this.productService.placeOrder(this.orderDetails).subscribe({
      next: (response) => {
        console.log('Order placed successfully:', response);
        orderForm.reset();
        this.router.navigate(['/orderConfirm']);
      },
      error: (error) => {
        console.error('Error placing order:', error);
      }
    });
  }

  getQuantityForProduct(productId: number | undefined): any {
      const filteredProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId);
      return filteredProduct.length > 0 ? filteredProduct[0].quantity : 1; 
   }  

  getCalculatedTotal(productId: number | undefined, productDiscountedPrice: number): number {
    const filteredProduct = this.orderDetails.orderProductQuantityList.filter(
      (productQuantity) => productQuantity.productId === productId);
    return filteredProduct.length > 0 ? filteredProduct[0].quantity * productDiscountedPrice : 0;
  }

  onQuantityChanged(Quantity: number, productId: number | undefined): void {
    const productQuantity = this.orderDetails.orderProductQuantityList.find(
      (pq) => pq.productId === productId
    );
    if (productQuantity) {
      productQuantity.quantity = Quantity;
    }
  }
  getCalculatedTGrandTotal() {
    let grandTotal = 0;
    this.productDetails.forEach(
      (productQuantity) => {
      const price = this.orderDetails.orderProductQuantityList.filter(pq => pq.productId === productQuantity.productId);
      if (price) {
        grandTotal += productQuantity.productDiscountedPrice * price[0].quantity;
      }
    });
    return grandTotal;
  }

}
