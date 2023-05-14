import { CabinetSchedule } from './../../models/events/schedule';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserAppointment } from 'src/app/models/scheduling/scheduling';

@Injectable({
  providedIn: 'root',
})
export class ScheduleService {
  private apiUrl: string = environment.apiUrl + '/schedule';

  constructor(private http: HttpClient) {}

  getSchedule(id: string): Observable<CabinetSchedule> {
    return this.http.get<CabinetSchedule>(`${this.apiUrl}/${id}`);
  }

  scheduleAppointment(scheduleId: string, data: any) {
    return this.http.post<any>(`${this.apiUrl}/${scheduleId}/full`, data);
  }

  scheduleDoctorAppointment(scheduleId: string, data: any) {
    return this.http.post<any>(`${this.apiUrl}/${scheduleId}/doctor`, data);
  }

  scheduleUserAppointment(scheduleId: string, userId: string, data: UserAppointment) {
    return this.http.post<any>(`${this.apiUrl}/${scheduleId}/user/${userId}`, data);
  }
}
