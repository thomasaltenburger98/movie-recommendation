import { Injectable } from '@angular/core';
import {HttpHandler, HttpRequest} from "@angular/common/http";
import {UserService} from "./services/user.service";
import {FilmService} from "./services/film.service";
import {RecommendationService} from "./services/recommendation.service";
import {RatingService} from "./services/rating.service";

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService {

  constructor(private userService: UserService,
              private filmService: FilmService,
              private recommendationService: RecommendationService,
              private ratingService: RatingService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    if (req.url.startsWith(this.filmService.apiUrl)
      || req.url.startsWith(this.recommendationService.apiUrl)
      || req.url.startsWith(this.ratingService.apiUrl)) {
      const authToken = this.userService.getToken();

      // Clone the request and replace the original headers with
      // cloned headers, updated with the authorization.
      const authReq = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + authToken)
      });

      // send cloned request with header to the next handler.
      return next.handle(authReq);
    }
    return next.handle(req);
  }

}
