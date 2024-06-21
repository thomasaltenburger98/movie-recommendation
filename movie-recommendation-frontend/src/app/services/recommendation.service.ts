import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {UserService} from "./user.service";
import {Observable} from "rxjs";
import {Film} from "../../models/Film";

@Injectable({
  providedIn: 'root'
})
export class RecommendationService {
  public apiUrl = 'http://127.0.0.1:8080/api/recommendations';

  constructor(private http: HttpClient, private userService: UserService) { }

  getRecommendedFilms(): Observable<Film[]> {
    const url = this.apiUrl;
    return this.http.get<Film[]>(url);
  }

}
