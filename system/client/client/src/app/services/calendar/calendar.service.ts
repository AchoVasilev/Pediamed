import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { EventData, EventDataCreate } from 'src/app/models/events/schedule';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {
  private apiUrl = environment.apiUrl + '/calendar';
  private eventData$?: Observable<EventData[]>;

  constructor(private http: HttpClient) { }

  getEventData(): Observable<EventData[]> {
    if (!this.eventData$) {
      this.eventData$ = this.http
        .get<EventData[]>(this.apiUrl + '/event-data')
        .pipe(shareReplay(1));
    }

    return this.eventData$;
  }

  postEventData(data: EventDataCreate): Observable<any> {
    return this.http.post<any>(this.apiUrl, data);
  }
}
