import { ScheduleData } from './../../models/events/schedule';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ScheduleDataService {

  private calendarEvents = new BehaviorSubject<ScheduleData[]>(null!);
  private appointments = new BehaviorSubject<ScheduleData[]>(null!);
  private entireSchedule = new BehaviorSubject<ScheduleData[]>(null!);
  private schedule$ = this.entireSchedule.asObservable();

  getSchedule() {
    return this.entireSchedule.value;
  }

  getSchedule$() {
    return this.schedule$;
  }

  removeItem(itemId: string) {
    this.entireSchedule.next([...this.entireSchedule.value.filter(ev => ev.id !== itemId)]);
  } 

  addItem(data: ScheduleData) {
    this.entireSchedule.next([...this.entireSchedule.value, data]);
  }

  setSchedule(data: ScheduleData[]) {
    this.entireSchedule.next(data);
  }

  getAllEvents() {
    return this.calendarEvents.value;
  }

  getAllAppointments() {
    return this.appointments.value;
  }

  setAllEvents(events: ScheduleData[]) {
    this.calendarEvents.next(events);
  }

  setAllAppointments(appointments: ScheduleData[]) {
    this.appointments.next(appointments);
  }

  addEvent(event: ScheduleData) {
    this.calendarEvents.next([...this.calendarEvents.value, event]);
  }

  addAppointment(appointment: ScheduleData) {
    this.appointments.next([...this.appointments.value, appointment]);
  }

  removeEvent(event: ScheduleData) {
    this.calendarEvents.next(this.calendarEvents.value.filter(ev => ev.id !== event.id));
  }

  removeAppointment(appointment: ScheduleData) {
    this.appointments.next(this.appointments.value.filter(ap => ap.id !== appointment.id));
  }
}
