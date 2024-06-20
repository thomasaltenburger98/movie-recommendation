import { RouterModule } from '@angular/router';

import { MatSidenavModule } from '@angular/material/sidenav';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FilmListComponent } from './film-list/film-list.component';
import { FilmDetailsComponent } from './film-details/film-details.component';
import {CommonModule} from "@angular/common";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatCardModule} from "@angular/material/card";
import {MatGridListModule} from "@angular/material/grid-list";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatListModule} from "@angular/material/list";
import {MatFormFieldModule} from "@angular/material/form-field";
import {FormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {
  HTTP_INTERCEPTORS,
  HttpClient,
  HttpHandler,
  provideHttpClient,
  withInterceptorsFromDi
} from "@angular/common/http";
import { LoginComponent } from './login/login.component';
import {MatButton, MatButtonModule} from "@angular/material/button";
import {RegisterComponent} from "./register/register.component";
import {AuthInterceptorService} from "./auth-interceptor.service";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatChipsModule} from "@angular/material/chips";

@NgModule({ declarations: [
        AppComponent,
        FilmListComponent,
        FilmDetailsComponent,
        LoginComponent,
        RegisterComponent
    ],
    bootstrap: [AppComponent],
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        CommonModule,
        BrowserAnimationsModule,
        MatProgressSpinnerModule,
        MatListModule,
        MatFormFieldModule,
        MatInputModule,
        MatIconModule,
        MatSidenavModule,
        MatButton,
        MatToolbarModule,
        MatButtonModule,
        MatCardModule,
        MatChipsModule], providers:
    [
      provideHttpClient(withInterceptorsFromDi()),
      { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true }
    ] })
export class AppModule { }
