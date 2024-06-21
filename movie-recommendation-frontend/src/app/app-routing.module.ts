import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {FilmListComponent} from "./film-list/film-list.component";
import {FilmDetailsComponent} from "./film-details/film-details.component";
import {LoginComponent} from "./login/login.component";
import {authGuard} from "./auth.guard";
import {RegisterComponent} from "./register/register.component";
import {RecommendationListComponent} from "./recommendation-list/recommendation-list.component";

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'films', component: FilmListComponent, canActivate: [authGuard] },
  { path: 'films/:id', component: FilmDetailsComponent, canActivate: [authGuard] },
  { path: 'recommendations', component: RecommendationListComponent, canActivate: [authGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
