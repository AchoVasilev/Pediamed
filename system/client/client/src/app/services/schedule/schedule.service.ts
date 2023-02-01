import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EventData } from 'src/app/models/events/schedule';

@Injectable({
  providedIn: 'root'
})
export class ScheduleService {
  private apiUrl: string = environment.apiUrl + '/schedule';

  constructor(private http: HttpClient) { }

  getEventData(): Observable<EventData[]> {
    return this.http.get<EventData[]>(this.apiUrl + '/event-data');
  }
}
