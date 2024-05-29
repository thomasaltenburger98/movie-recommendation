import { Component } from '@angular/core';
import {UserService} from "./user.service";
import {tap} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'movie-recommendation-frontend';

  constructor(private userService: UserService) {
  }

  ngOnInit() {

  }

}
