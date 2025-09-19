import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { OrderDetails } from '../_model/order-details-model';
import { ActivatedRoute } from '@angular/router';
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
    private productService: ProductService
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
      },
      error: (error) => {
        console.error('Error placing order:', error);
      }
    });
  }

}
