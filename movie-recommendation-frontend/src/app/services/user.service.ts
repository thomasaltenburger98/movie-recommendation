import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {catchError, map, Observable, of, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  //private ApiURL = "http://127.0.0.1:8000/user/newid";
  private ApiURL = "http://127.0.0.1:8080/api/auth/register";
  private ApiURLRegister = "http://127.0.0.1:8080/api/auth/register";
  private ApiURLLogin = "http://127.0.0.1:8080/api/auth/login";
  private ApiURLLogout = "http://127.0.0.1:8080/api/auth/logout";

  constructor(private http: HttpClient) {

  }

  registerUser(username: string, password: string): Observable<any> {
    return this.http.post(this.ApiURLRegister, {
      username: username, password: password
    });
  }

  loginUser(username: string, password: string): Observable<any> {
    return this.http.post(this.ApiURLLogin, {
      username: username, password: password
    }).pipe(
      tap((response: any) => {
        if (response && response.tokenValue) {
          this.setToken(response.tokenValue);
        }
      })
    );
  }

  logoutUser(): Observable<any> {
    return this.http.post<Response>(this.ApiURLLogout, {
      token: this.getToken()
    }).pipe(
      tap((response) => {
        if (response.status === 200) {
          this.deleteToken();
        }
      })
    );
  }

  public getToken() {
    return localStorage.getItem('authToken');
  }
  public setToken(token: string) {
    localStorage.setItem('authToken', token);
  }
  public deleteToken() {
    localStorage.removeItem('authToken');
  }
  isLoggedIn(): boolean {
    return this.getToken() !== null;
  }

}
