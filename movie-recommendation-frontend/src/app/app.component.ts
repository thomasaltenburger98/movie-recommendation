import { Component } from '@angular/core';
import {UserService} from "./user.service";
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
    this.userService.logoutUser().subscribe(response => {
      if (response.status === 200) {
        this.router.navigate(['/login']);
      }
    });
  }

}
