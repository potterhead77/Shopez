import { HttpClient, HttpHeaderResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAuthService } from './user-auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  PATH_OF_API ="http://localhost:8080";
  requestHeader = new HttpHeaders(
      {"No-Auth":"True"}
  );
  constructor(private htppclient: HttpClient,
    private userAuthService : UserAuthService
  ) {}

  public login(loginData: { userName: string; userPassword: string }){
    return this.htppclient.post(this.PATH_OF_API+ "/authenticate",loginData,{headers: this.requestHeader});
  }
  public forUser(){
    return this.htppclient.get(this.PATH_OF_API+'/auth/forUser',{responseType:"text"});
  }
  public forAdmin(){
    return this.htppclient.get(this.PATH_OF_API+'/auth/forAdmin',{responseType:"text"});
  }
  public roleMatch(allowedRoles : string[]) : boolean{
      let isMatch = false;
      const userRoles : any = this.userAuthService.getRoles();

      if(userRoles!=null && userRoles){
        for(let i=0;i< userRoles.length;i++){
          for(let j=0;j<allowedRoles.length;j++){
            if(userRoles[i].roleName.toUpperCase() === allowedRoles[j].toUpperCase()){
              isMatch = true;
              return isMatch;
            } 
          }
        }
      }
      return false;
  }

  public registerUser(registerData: { userName: string; userPassword: string, userEmail: string}){
    return this.htppclient.post(this.PATH_OF_API+ "/registerNewUser",registerData,{headers: this.requestHeader});
  } 

}
