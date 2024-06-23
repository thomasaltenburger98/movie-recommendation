import { Component } from '@angular/core';
import {NgIf, NgStyle} from "@angular/common";
import {RatingService} from "../services/rating.service";

@Component({
  selector: 'app-progressbar',
  standalone: true,
  imports: [
    NgIf,
    NgStyle
  ],
  templateUrl: './progressbar.component.html',
  styleUrl: './progressbar.component.scss'
})
export class ProgressbarComponent {

  goalReached = false;
  currentCount = 0; // Aktuelle Anzahl
  progress = 0;
  totalCount = 10; // Gesamtzahl Ziel

  constructor(private ratingService: RatingService) {
    this.ratingService.ratingUpdated$.subscribe(() => {
      this.increaseCount();
    });

    this.ratingService.getRatingCount().subscribe((count: any) => {
      this.currentCount = count;
      this.updateProgress();
    });
  }

  increaseCount(increaseBy: number = 1) {
    this.currentCount += increaseBy;
    this.updateProgress();
    if (this.goalReached) {
      this.onGoalReached();
    }
  }

  updateProgress() {
    this.progress = (this.currentCount / this.totalCount) * 100;
    this.goalReached = this.currentCount >= this.totalCount;
  }

  onGoalReached() {
    console.log('Ziel erreicht!');
  }

}
