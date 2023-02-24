import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppointmentCauseService {
  private apiUrl: string = environment.apiUrl;
  
  constructor(private http: HttpClient) { }
}
