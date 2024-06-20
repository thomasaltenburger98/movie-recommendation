import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FilmService} from "../services/film.service";

@Component({
  selector: 'app-film-details',
  templateUrl: './film-details.component.html',
  styleUrls: ['./film-details.component.scss']
})
export class FilmDetailsComponent {

  film: any;

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
      this.film = this.filmService.getFilmById(id);
    }
  }

}
