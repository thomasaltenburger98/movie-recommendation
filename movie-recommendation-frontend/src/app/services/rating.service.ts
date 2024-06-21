import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RatingService {
  public apiUrl = 'http://127.0.0.1:8080/api/ratings';

  constructor(private http: HttpClient) { }

  rateFilm(filmID: number, ratingValue: number): Observable<any> {
    const response = this.http.post(this.apiUrl, {
      film_id: filmID,
      rating_value: ratingValue
    });

    return response;
  }


}
