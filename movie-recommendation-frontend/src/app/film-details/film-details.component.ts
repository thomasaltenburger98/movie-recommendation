import { Component } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {FilmService} from "../film.service";

@Component({
  selector: 'app-film-details',
  templateUrl: './film-details.component.html',
  styleUrls: ['./film-details.component.scss']
})
export class FilmDetailsComponent {

  film: any;

  constructor(private route: ActivatedRoute, private filmService: FilmService) { }

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam !== null) {
      const id = +idParam;
      this.film = this.filmService.getFilmById(id);
    }
  }

}
