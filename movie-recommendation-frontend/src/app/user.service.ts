import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable, of, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private ApiURL = "http://127.0.0.1:8000/user/newid";
  private userID: number;

  constructor(private http: HttpClient) {
    this.userID = -1;
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
