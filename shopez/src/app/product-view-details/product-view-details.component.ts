import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../_model/product.model';

@Component({
  selector: 'app-product-view-details',
  templateUrl: './product-view-details.component.html',
  styleUrls: ['./product-view-details.component.css']
})
export class ProductViewDetailsComponent implements OnInit {

    product: Product | undefined;
    
    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit(): void {
        this.product = this.activatedRoute.snapshot.data['product'];
        console.log(this.product);
    }
}
