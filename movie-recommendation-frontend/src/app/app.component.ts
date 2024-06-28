import { Component } from '@angular/core';
import {UserService} from "./services/user.service";
import {catchError, tap} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'movie-recommendation-frontend';

  constructor(protected userService: UserService, private router: Router) {

  }

  ngOnInit() {

  }

  logout() {
    this.userService.logoutUser().subscribe({
      next: (response) => {
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.log(error);
        this.router.navigate(['/login']);
      }
    });
  }

}
