import { Injectable } from '@angular/core';
import {Observable, Subject, tap} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  public apiUrl = 'http://127.0.0.1:8080/api/ratings';
  private ratingIncreased = new Subject<void>();
  public ratingIncreased$ = this.ratingIncreased.asObservable();
  private ratingDecreased = new Subject<void>();
  public ratingDecreased$ = this.ratingDecreased.asObservable();

  constructor(private http: HttpClient) { }

  rateFilm(filmID: number, ratingValue: number, filmIsRated: boolean = false): Observable<any> {
    const response = this.http.post(this.apiUrl, {
      film_id: filmID,
      rating_value: ratingValue
    }).pipe(tap({
      next: () => {
        if (!filmIsRated) {
          this.ratingIncreased.next()
        }
      },
      error: error => console.error('Error rating film', error)
    }));

    return response;
  }
  unrateFilm(filmID: number, ratingValue: number): Observable<any> {
    const response = this.http.post(this.apiUrl + "/delete", {
      film_id: filmID,
      rating_value: ratingValue
    }).pipe(tap({
      next: () => this.ratingDecreased.next(),
      error: error => console.error('Error rating film', error)
    }));

    return response;
  }

  getRatingCount(): Observable<any> {
    return this.http.get(this.apiUrl + '/count');
  }


}
