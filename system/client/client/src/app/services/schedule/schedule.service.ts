import { CabinetSchedule, ScheduleData } from './../../models/events/schedule';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EventData, EventDataCreate } from 'src/app/models/events/schedule';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {
  
  private apiUrl: string = environment.apiUrl + '/schedule';

  constructor(private http: HttpClient) { }

  getEventData(): Observable<EventData[]> {
    return this.http.get<EventData[]>(this.apiUrl + '/event-data');
  }

  postEventData(data: EventDataCreate): Observable<ScheduleData[]> {
    return this.http.post<ScheduleData[]>(this.apiUrl + '/event-data', data);
  }

  getSchedule(id: string): Observable<CabinetSchedule> {
    return this.http.get<CabinetSchedule>(`${this.apiUrl}/${id}`);
  }

  scheduleAppointment(scheduleId: string, data: any) {
    return this.http.post<any>(`${this.apiUrl}/${scheduleId}/full`, data);
  }
}
