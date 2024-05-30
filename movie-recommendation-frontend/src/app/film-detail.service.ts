import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {delay, Observable, of, switchMap} from "rxjs";
import {FilmDetail} from "../models/FilmDetail";

@Injectable({
  providedIn: 'root'
})
export class FilmDetailService {
  private apiUrl = 'http://www.omdbapi.com/?i=tt3896198&apikey=a1b0d937';

  constructor(private http : HttpClient) { }

  getFilmDetailByTitleAndYear(title: string, year: string): Observable<FilmDetail> {
    let apiUrlWithParams = `${this.apiUrl}&t=${title}&y=${year}`;
    apiUrlWithParams.replace(':title', title);
    apiUrlWithParams.replace(':year', year);


    // Delay fetch for 500ms for test purpose
    return of([]).pipe(
      delay(500),
      switchMap(() => this.http.get<FilmDetail>(apiUrlWithParams))
    );

    //return this.http.get<FilmDetail>(apiUrlWithParams);
  }

}
