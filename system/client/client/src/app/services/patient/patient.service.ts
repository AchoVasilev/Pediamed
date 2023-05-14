import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { PatientView } from 'src/app/models/user/patient';
import { environment } from 'src/environments/environment';
import { UserDataService } from '../data/user-data.service';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private userDataService: UserDataService
  ) {}

  getPatientByParentId(parentId: string) {
    return this.http
      .get<PatientView[]>(`${this.apiUrl}/patient/parent/${parentId}`)
      .pipe(
        tap((data) => {
          this.userDataService.setPatients(data);
          localStorage.setItem('patients', JSON.stringify(data));
        })
      );
  }

  searchPatient(query: string) {
    return this.http.get<PatientView[]>(`${this.apiUrl}/patient/search`, {
      params: {
        name: query,
      },
    });
  }
}
