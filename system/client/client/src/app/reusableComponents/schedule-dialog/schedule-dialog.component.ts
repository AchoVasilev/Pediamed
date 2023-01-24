import { EventData } from './../../models/events/eventData';
import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-schedule-dialog',
  templateUrl: './schedule-dialog.component.html',
  styleUrls: ['./schedule-dialog.component.css']
})
export class ScheduleDialogComponent {

  data!: EventData[];
  selectedHour: string = '';
  selectedMinutes: number = 0;

  constructor(@Inject(MAT_DIALOG_DATA) data: EventData[]) {
    this.data = data;
  }
}
