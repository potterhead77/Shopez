import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../_services/user.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  constructor(private userService: UserService,
    private router: Router
  ) { }

  ngOnInit(): void {
    console.log('RegisterComponent loaded');
  }


  register(registerForm: NgForm) {
    console.log(registerForm.value);
    this.userService.registerUser(registerForm.value).subscribe(
      (response) => {
        console.log(response);
        alert('Registration Successful! Please proceed to login.');
        this.router.navigate(['/login']);
        registerForm.reset();
      },
      (error) => {
        console.error(error);
        alert('Registration Failed! Please try again.');
      }
    );
  } 
  
}
