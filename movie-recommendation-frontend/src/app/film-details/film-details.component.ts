import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {FilmService} from "../services/film.service";
import {Film} from "../../models/Film";
import {getCastsOfFilm, getRuntimeAsTimeString} from "../utils/utils";
import { getDirectorOfCrew } from "../utils/utils";
import { getScreenplayOfCrew } from "../utils/utils";
import {Cast, Crew} from "../../models/FilmDetail";
import {DomSanitizer, SafeResourceUrl} from "@angular/platform-browser";
import {WatchProviders} from "../../models/WatchProviders";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-film-details',
  templateUrl: './film-details.component.html',
  styleUrls: ['./film-details.component.scss']
})
export class FilmDetailsComponent {

  film: Film | undefined;
  providers: WatchProviders | undefined;

  casts: Cast[] = [];

  similarMovies = [
    { img: 'path-to-similar-movie-1.jpg', title: 'Film 1' },
    { img: 'path-to-similar-movie-2.jpg', title: 'Film 2' },
    { img: 'path-to-similar-movie-3.jpg', title: 'Film 3' },
  ];

  constructor(private route: ActivatedRoute,
              private filmService: FilmService,
              private userService: UserService,
              private sanitizer: DomSanitizer,
              private router: Router) { }

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null) {
      const id = +idParam;
      this.filmService.getFilmById(id).subscribe({
        next: (film) => {
          console.log(film);
          this.film = film;
          this.filmService.getFilmDetails(id).subscribe({
            next: (filmDetail) => {
              if (this.film !== undefined) {
                this.film.filmDetail = filmDetail;
                console.log(this.film);
                this.casts = getCastsOfFilm(filmDetail.credits.cast);
              }
            }
          });
          this.filmService.getWatchProviders(film.tmdbId).subscribe({
            next: (providers) => {
              console.log(providers);
              this.providers = providers;
            }
          });
        },
        error: (error) => {
          if (error.status === 403) {
            this.router.navigate(['/login']);
            this.userService.deleteToken();
          }
        },
      });
    }
  }

  protected getRuntimeAsTimeString(runtime: number|undefined): string {
    if (runtime != undefined) {
      return getRuntimeAsTimeString(runtime);
    }
    return "";
  }
  protected getDirector(crew: Crew[]|undefined): string {
    if (crew !== undefined && crew !== null) {
      return getDirectorOfCrew(crew);
    }
    return "";
  }
  protected getScreenplay(crew: Crew[]|undefined): string {
    if (crew !== undefined && crew !== null) {
      return getScreenplayOfCrew(crew);
    }
    return "";
  }

  getSafeUrlForVideo(videoId: string | undefined): SafeResourceUrl {
    if (videoId === undefined) {
      videoId = '';
    }
    const url = `https://www.youtube.com/embed/${videoId}`;
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

  getRoundedRating(rating: number|undefined): number {
    if (rating === undefined) {
      return 0;
    }
    return Math.round(rating * 2) / 2;
  }
  protected createRange(number: number): number[] {
    const items: number[] = [];
    console.log('range: ' + number);
    number = Math.round(number);
    for (let i = 1; i <= number; i++) {
      items.push(i);
    }
    return items;
  }
  protected isRatingStarHalf(rating: number, starNumber: number): boolean {
    console.log('rating: ' + rating + ', starNumber: ' + starNumber);
    console.log('result: ' + (rating - starNumber === -0.5));
    return rating - starNumber === -0.5;
  }

}
