import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { ForbiddenComponent } from './forbidden/forbidden.component';
import { AuthGuard } from './_auth/auth.guard';
import { AddNewProductComponent } from './add-new-product/add-new-product.component';
import { ShowProductDetailsComponent } from './show-product-details/show-product-details.component';
import { ProductResolveService } from './product-resolve.service';

const routes: Routes = [
  {path:'', component: HomeComponent},
  {path:'admin', component: AdminComponent,canActivate:[AuthGuard],data:{roles:['admin']}},
  {path:'user', component: UserComponent,canActivate:[AuthGuard],data:{roles:['user']}},
  {path:'login', component: LoginComponent},
  {path:'forbidden', component: ForbiddenComponent},
  {path:'addNewProduct',component: AddNewProductComponent,canActivate:[AuthGuard],data:{roles:['admin']},
    resolve: {
      product: ProductResolveService
    }
  },
  {path:'showProductDetails',component: ShowProductDetailsComponent,canActivate:[AuthGuard],data:{roles:['admin']}}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
