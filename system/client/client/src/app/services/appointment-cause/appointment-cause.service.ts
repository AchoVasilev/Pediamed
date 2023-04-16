import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';

@Injectable({
  providedIn: 'root'
})
export class AppointmentCauseService {
  private apiUrl: string = environment.apiUrl;
  
  constructor(private http: HttpClient) { }

  getAppointmentCauses() {
    return this.http.get<AppointmentCauseResponse[]>(this.apiUrl + '/appointment-cause');
  }
}
