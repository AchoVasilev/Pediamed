import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import { Observable, retry, share } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WebSocketService {
  private connection$?: WebSocketSubject<any>;
  private id?: string;
  private baseUrl: string = "ws://localhost:8080/ws/schedule";

  constructor() {}

  connect(id: string): Observable<any> {
    this.id = id;
    this.connection$ = this.createConnection();

    return this.connection$.pipe(
      share(),
      retry({delay: 2000, count: 20})
    );
  }

  send(payload: string) {
    if (this.connection$) {
      this.connection$.next({
        cabinetId: payload,
      });
    } else {
      this.connection$ = this.createConnection();
      this.connection$.next({
        cabinetId: payload,
      });
    }
  }

  createConnection() {
    const url = `${this.baseUrl}/${this.id}`;

    return webSocket({
      url: url,
      protocol: ['websocket', 'xhr-polling'],
    });
  }
}
