  import { Injectable } from '@angular/core';

  @Injectable({
    providedIn: 'root'
  })
  export class UserAuthService {

    constructor() { }

    public setRoles(roles:[]){
      localStorage.setItem("roles",JSON.stringify(roles));
    }
    public getRoles():string[]{
      return JSON.parse(localStorage.getItem("roles")||'[]');
    }
    public setToken(jwtToken : string){
      localStorage.setItem("jwtToken",jwtToken);
    }
    public getToken(): string | null{
        return localStorage.getItem("jwtToken");
    }

    public clear(){
      localStorage.clear();
    }
    public isloggedIn(){
      return this.getRoles() && this.getToken();
    }
    public isAdmin(){
      const roles: any[] = this.getRoles();
      console.log(roles);
      return roles[0].roleName === 'admin';
    }
    public isUser(){
      const roles: any[] = this.getRoles();
      
      return roles[0].roleName === 'user';
    }
  }

