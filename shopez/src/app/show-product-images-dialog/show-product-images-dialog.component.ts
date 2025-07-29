import { Component, OnInit } from '@angular/core';
import {
  MatDialog,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { Inject } from '@angular/core';
import { FileHandle } from '../_model/file.handle.model';

@Component({
  selector: 'app-show-product-images-dialog',
  templateUrl: './show-product-images-dialog.component.html',
  styleUrls: ['./show-product-images-dialog.component.css']
})
export class ShowProductImagesDialogComponent implements OnInit{

  constructor(@Inject(MAT_DIALOG_DATA) public data: { images: FileHandle[] }){}

  ngOnInit(): void {
    this.receiveImages();
  }

  receiveImages(){
    console.log(this.data);
  }

}
