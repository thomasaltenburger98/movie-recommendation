import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FilmService} from "../services/film.service";
import {Film} from "../../models/Film";
import {getRuntimeAsTimeString} from "../utils/utils";
import { getDirectorOfCrew } from "../utils/utils";
import { getScreenplayOfCrew } from "../utils/utils";
import {Crew} from "../../models/FilmDetail";

@Component({
  selector: 'app-film-details',
  templateUrl: './film-details.component.html',
  styleUrls: ['./film-details.component.scss']
})
export class FilmDetailsComponent {

  film: Film | undefined;

  cast = [
    { name: 'Sam Worthiness', role: 'Jake Sully' },
    { name: 'Sam Worthington', role: 'Jake Sully' },
    { name: 'Sam Worthington', role: 'Jake Sully' },
    { name: 'Sam Worthington', role: 'Jake Sully' },
    { name: 'Sam Worthington', role: 'Jake Sully' },
    { name: 'Sam Worthington', role: 'Jake Sully' },
  ];

  similarMovies = [
    { img: 'path-to-similar-movie-1.jpg', title: 'Film 1' },
    { img: 'path-to-similar-movie-2.jpg', title: 'Film 2' },
    { img: 'path-to-similar-movie-3.jpg', title: 'Film 3' },
  ];

  constructor(private route: ActivatedRoute, private filmService: FilmService) { }

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null) {
      const id = +idParam;
      this.filmService.getFilmById(id).subscribe(film => {
        this.film = film;
        this.filmService.getFilmDetails(id).subscribe(filmDetail => {
          if (this.film !== undefined) {
            this.film.filmDetail = filmDetail;
            console.log(this.film);
          }
        });
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

}
