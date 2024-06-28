import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../services/user.service";

@Component({
  selector: 'app-splash-screen',
  standalone: true,
  imports: [],
  templateUrl: './splash-screen.component.html',
  styleUrl: './splash-screen.component.scss'
})
export class SplashScreenComponent {

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
    setTimeout(() => {
      if (this.userService.isLoggedIn()) {
        this.router.navigate(['/films']);
      } else {
        this.router.navigate(['/login']);
      }
    }, 3500);
  }

}
