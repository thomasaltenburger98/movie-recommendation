import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {flatMap, map, Observable, switchMap, tap} from "rxjs";
import {Film} from "../../models/Film";
import {UserService} from "./user.service";
import {FilmDetail} from "../../models/FilmDetail";

@Injectable({
  providedIn: 'root'
})
export class FilmService {
  public apiUrl = 'http://127.0.0.1:8080/api/films';

  constructor(private http: HttpClient, private userService: UserService) { }

  // get recommended films of user
  getFilms(): Observable<Film[]> {
    return this.http.get<Film[]>(this.apiUrl);
  }

  // get film pagination
  getFilmsPage(page: number): Observable<Film[]> {
    const url = `${this.apiUrl}/page/${page}`;
    return this.http.get<Film[]>(url);
  }

  getFilmsPageAndFilterByTitle(page: number, title: string = ""): Observable<Film[]> {
    if (title.length === 0) {
      return this.getFilmsPage(page);
    }
    const url = `${this.apiUrl}/page/${page}?search=${title}`;
    return this.http.get<Film[]>(url);
  }

  // for film detail page
  getFilmById(id: number): Observable<Film> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.get<Film>(url);
  }

  // get film details
  getFilmDetails(id: number): Observable<FilmDetail> {
    const url = `${this.apiUrl}/detail/${id}`;
    return this.http.get<FilmDetail>(url);
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

}
