import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PatientView } from 'src/app/models/user/patient';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PatientService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getPatientByParentId(parentId: string) {
    return this.http.get<PatientView[]>(`${this.apiUrl}/patient/parent/${parentId}`);
  }
}
