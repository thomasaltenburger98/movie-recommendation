import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable, of, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  //private ApiURL = "http://127.0.0.1:8000/user/newid";
  private ApiURL = "http://127.0.0.1:8080/api/auth/register";
  private ApiURLRegister = "http://127.0.0.1:8080/api/auth/register";
  private ApiURLLogin = "http://127.0.0.1:8080/api/auth/login";
  private userID: number;

  constructor(private http: HttpClient) {
    this.userID = -1;
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
          this.setToken(response.tokenValue);  // Speichern Sie den Token
        }
      })
    );
  }

  private setToken(token: string) {
    localStorage.setItem('authToken', token);
  }

  public getUserID(): Observable<number> {
    let userID = this.userID;
    if (this.userID < 0) {
      userID = this.getUserIDFromStorage();
      if (userID < 0) {
        return this.fetchUserID().pipe(
          tap((result: any) => {
            userID = parseInt(result['user_id']);
            this.userID = userID;
            this.saveUserIDInStorage(userID);
          }),
          map(() => userID)
        );
      }
    }
    return of(userID);
  }

  private fetchUserID(): Observable<any> {
    return this.http.get(this.ApiURL);
  }

  private getUserIDFromStorage(): number {
    const value = localStorage.getItem('user-id');
    return value ? parseInt(value) : -1;
  }
  private saveUserIDInStorage(userID: number) {
    localStorage.setItem('user-id', userID+"");
  }

}
