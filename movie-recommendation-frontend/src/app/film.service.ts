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

  // get recommended films of user
  getFilms(): Observable<Film[]> {
    return this.http.get<Film[]>(this.apiUrl);
  }

  // for film detail page
  getFilmById(id: number): Observable<Film> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Film>(url);
  }

  // is it necessary???
  /*createFilm(film: Film): Observable<Film> {
    //return this.http.post<Film>(this.apiUrl, film);
  }*/

  // is it necessary???
  /*updateFilm(id: number, film: Film): Observable<Film> {
    return new Observable();
    //return this.http.put<Film>(url, film);
  }*/

  // is it necessary???
  /*deleteFilm(id: number): Observable<void> {
    return new Observable<void>();
    //return this.http.delete<void>(url);
  }*/

  rateFilm(filmID: number, ratingValue: number): Observable<any> {
    return new Observable<any>();
    /*return this.userService.getUserID().pipe(
      switchMap((userId: number) => {
        let url = this.apiUrl + "/" + filmID + "/user/" + userId + "/rate/" + ratingValue;

        return this.http.get(url);
      })
    );*/
  }

  // TODO: delete rate of film

}
