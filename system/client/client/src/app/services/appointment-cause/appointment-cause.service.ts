import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { Observable, shareReplay } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AppointmentCauseService {
  private apiUrl: string = environment.apiUrl;
  private appointmentCauses$?: Observable<AppointmentCauseResponse[]>;

  constructor(private http: HttpClient) { }

  getAppointmentCauses(): Observable<AppointmentCauseResponse[]> {
    if (!this.appointmentCauses$) {
      this.appointmentCauses$ = this.http
        .get<AppointmentCauseResponse[]>(this.apiUrl + '/appointment-cause')
        .pipe(shareReplay(1));
    }

    return this.appointmentCauses$;
  }
}
