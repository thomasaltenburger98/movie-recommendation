import { Component } from '@angular/core';
import {UserService} from "../services/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string = "";
  password: string = "";

  constructor(private userService: UserService, private router: Router) {}

  onSubmit(): void {
    this.userService.loginUser(this.username, this.password).subscribe(response => {
      if (response && response.tokenValue) {
        this.router.navigate(['/films']);  // Leiten Sie den Benutzer zur Dashboard-Ansicht weiter
      } else {
        // TODO Handle login failure...

      }
    });
  }
}
