import { EventData } from './../../models/events/eventData';
import { Component } from '@angular/core';

@Component({
  selector: 'app-schedule-dialog',
  templateUrl: './schedule-dialog.component.html',
  styleUrls: ['./schedule-dialog.component.css']
})
export class ScheduleDialogComponent {

  eventData!: EventData[];
  selectedHour: string = '';
  selectedMinutes: number = 0;
  
  constructor() {}
}
