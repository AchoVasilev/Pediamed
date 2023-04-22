import { ScheduleData } from './../../models/events/schedule';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScheduleDataService {

  private entireSchedule = new BehaviorSubject<ScheduleData[]>([]);
  private schedule$ = this.entireSchedule.asObservable();

  getSchedule() {
    return this.entireSchedule.value;
  }

  getSchedule$() {
    return this.schedule$;
  }

  removeItem(itemId: string) {
    this.entireSchedule.next(this.entireSchedule.value.filter(ev => ev.id !== itemId));
  } 

  addItem(data: ScheduleData) {
    this.entireSchedule.next([...this.entireSchedule.value, data]);
  }

  addMultiple(data: ScheduleData[]) {
    this.entireSchedule.next([...this.entireSchedule.value, ...data]);
  }

  setSchedule(data: ScheduleData[]) {
    this.entireSchedule.next(data);
  }
}
