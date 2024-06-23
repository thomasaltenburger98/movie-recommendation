import { Injectable } from '@angular/core';
import {Observable, Subject, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  public apiUrl = 'http://127.0.0.1:8080/api/ratings';
  private ratingUpdated = new Subject<void>();
  public ratingUpdated$ = this.ratingUpdated.asObservable();

  constructor(private http: HttpClient) { }

  rateFilm(filmID: number, ratingValue: number): Observable<any> {
    const response = this.http.post(this.apiUrl, {
      film_id: filmID,
      rating_value: ratingValue
    }).pipe(tap({
      next: () => this.ratingUpdated.next(),
      error: error => console.error('Error rating film', error)
    }));

    return response;
  }

  getRatingCount(): Observable<any> {
    return this.http.get(this.apiUrl + '/count');
  }


}
