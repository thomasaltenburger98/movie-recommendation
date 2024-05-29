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
  userID: number = -1;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.getUserID().subscribe((userID: number) => {
      this.userID = userID;
    });
  }

}
