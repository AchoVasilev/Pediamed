import { Injectable, NgZone } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import {EventSourcePolyfill} from 'event-source-polyfill';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class EventSourceService {
  private apiUrl = environment.apiUrl;

  constructor(private zone: NgZone, private authService: AuthService) {}

  getCalendarSource$(id: string): Observable<string> {
    let eventSource: EventSourcePolyfill;

    if(this.authService.isLoggedIn()) {
      const options = {
        headers: {
          'Authorization': 'Bearer ' + this.authService.getToken()
        },
        withCredentials: true,
      }

      eventSource = new EventSourcePolyfill(`${this.apiUrl}/schedule/${id}/stream`, options);
    } else {
      eventSource = new EventSourcePolyfill(`${this.apiUrl}/schedule/${id}/stream`);
    }

    return new Observable((observer) => {
      eventSource.addEventListener('message', (event) => {
        this.zone.run(() => {
          observer.next(event.data);
        });
      });

      return () => {
        eventSource.close();
      };
    });
  }
}
