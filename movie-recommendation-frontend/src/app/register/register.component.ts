import { Component } from '@angular/core';
import {UserService} from "../services/user.service";
import {Router, RouterLink} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {MatFormField, MatLabel} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  username: string = "";
  password: string = "";
  passwordRepeat: string = "";
  errorMessage: string = "";

  constructor(private userService: UserService, private router: Router) {}

  onSubmit(): void {
    if (this.password !== this.passwordRepeat) {
      this.errorMessage = "Passwords do not match!";
      return;
    }

    this.userService.registerUser(this.username, this.password).subscribe({
      next: (response) => {
        if (response && response.status === 200) {
          this.router.navigate(['/login']);
        }
      }, error: (error) => {
        if (error.error.message) {
          this.errorMessage = error.error.message;
        } else {
          this.errorMessage = "An error occurred while registering!";
        }
      }
    });
  }

}
