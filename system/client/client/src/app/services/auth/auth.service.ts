import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegisterPatient } from 'src/app/models/user/registerPatient';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl: string = environment.apiUrl + '/auth';
  private readonly httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  constructor(private httpClient: HttpClient) { }

  login(email: string, password: string): Observable<string> {
    return this.httpClient.post<string>(this.apiUrl + '/login', {
      email,
      password
    }, this.httpOptions);
  }

  register(register: RegisterPatient): Observable<any> {
    return this.httpClient.post<any>(this.apiUrl + '/register', {
      register
    }, this.httpOptions);
  }
}
