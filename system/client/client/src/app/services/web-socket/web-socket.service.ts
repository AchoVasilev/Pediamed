import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private connection$?: WebSocketSubject<any>;

  constructor() {}

  connect(id: string): Observable<any> {
    this.connection$ = webSocket({
      url: `ws://localhost:8080/ws/schedule/schedule/${id}`,
      protocol: ['websocket'],
    });

    return this.connection$;
  }

  send(payload: string) {
    if (this.connection$) {
      this.connection$.next({
        scheduleId: payload,
      });
    }
  }

  // getSchedule(scheduleId: string) {
  //   this.socket.emit('schedule', scheduleId);
  // }

  // getSchedule$() {
  //   return this.socket.fromEvent<CabinetSchedule>('schedule');
  // }
}
