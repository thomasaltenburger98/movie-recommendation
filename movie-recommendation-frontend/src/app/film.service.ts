import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {flatMap, map, Observable, switchMap, tap} from "rxjs";
import {Film} from "../models/Film";
import {UserService} from "./user.service";

@Injectable({
  providedIn: 'root'
})
export class FilmService {
  private apiUrl = 'http://127.0.0.1:8000/films';

  constructor(private http: HttpClient, private userService: UserService) { }

  getFilms(): Observable<Film[]> {
    return this.http.get<Film[]>(this.apiUrl);
  }

  getFilmById(id: number): Observable<Film> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Film>(url);
  }

  createFilm(film: Film): Observable<Film> {
    return this.http.post<Film>(this.apiUrl, film);
  }

  updateFilm(id: number, film: Film): Observable<Film> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.put<Film>(url, film);
  }

  deleteFilm(id: number): Observable<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete<void>(url);
  }

  rateFilm(filmID: number, ratingValue: number): Observable<any> {
    return this.userService.getUserID().pipe(
      switchMap((userId: number) => {
        //let userId = -1;
        let url = this.apiUrl + "/" + filmID + "/user/" + userId + "/rate/" + ratingValue;
        console.log(url);

        return this.http.get(url);
      })

      /*flatMap((userId: number) => {
        let userID = -1;
        let url = this.apiUrl + "/" + filmID + "/user/" + userID + "/rate/" + ratingValue;

        return this.http.get(url);
      })*/
    );
  }

}
