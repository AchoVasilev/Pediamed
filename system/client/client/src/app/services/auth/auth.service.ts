import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EMPTY, Observable, mergeMap, shareReplay, tap } from 'rxjs';
import { RegisterParent } from 'src/app/models/user/registerParent';
import { environment } from 'src/environments/environment';
import { AuthResult, UserModel } from './authResult';
import { UserDataService } from '../data/user-data.service';
import { isAfter, parseJSON, toDate } from 'date-fns';
import { Router } from '@angular/router';
import { PatientService } from '../patient/patient.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly apiUrl = environment.apiUrl;
  private readonly apiUrlWithPrefix: string = this.apiUrl + '/auth';
  private readonly httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };
  
  constructor(
    private httpClient: HttpClient, 
    private userDataService: UserDataService, 
    private router: Router,
    private patientService: PatientService) { }

  login(email: string, password: string, persist: boolean): Observable<any> {
    return this.httpClient.post<AuthResult>(this.apiUrlWithPrefix + '/login', {
      email,
      password,
      persist
    }, this.httpOptions)
      .pipe(
        tap(data => {
          this.setSession(data);
          this.userDataService.setLogin(true);
          this.userDataService.setUser(data.user);
        }),
        mergeMap((res) => this.userDataService.isDoctor() ? EMPTY : this.patientService.getPatientByParentId(res.user.id)),
        shareReplay());
  }

  register(register: RegisterParent): Observable<any> {
    return this.httpClient.post<any>(this.apiUrlWithPrefix + '/register', register, this.httpOptions);
  }

  logout() {
    this.userDataService.onLogOut();
    this.httpClient.post(this.apiUrlWithPrefix + '/logout', this.httpOptions)
    .subscribe(r => {
        localStorage.removeItem('expiresAt');
        localStorage.removeItem('token');
        localStorage.removeItem('patients');
        localStorage.removeItem('user');
        this.router.navigateByUrl('');
      });
  }

  isLoggedIn(): boolean {
    const isLogged = isAfter(this.getExpiration(), new Date()) && this.getToken() !== null;
    this.userDataService.setLogin(isLogged);
    if (isLogged && !this.userDataService.getUser()) {
      this.userDataService.setUser(JSON.parse(localStorage.getItem('user')!));
    }

    if (this.userDataService.getUser() && !this.userDataService.isDoctor()) {
      this.userDataService.setPatients(JSON.parse(localStorage.getItem('patients')!));
    }

    return isLogged;
  }

  getToken() {
    return localStorage.getItem('token');
  }

  removeToken() {
    localStorage.clear();
  }

  getUser(): Observable<UserModel>{
    return this.httpClient.get<UserModel>(this.apiUrl + '/user').pipe(
      tap(user => {
        this.userDataService.setUser(user);
      })
    );
  }

  getExpiration() {
    const expiration = localStorage.getItem("expiresAt");
    const expiresAt = JSON.parse(expiration!);
    
    return toDate(expiresAt);
  }

  private setSession(authResult: AuthResult) {
    const expiresAt = parseJSON(authResult.tokenModel.expiresAt);
    localStorage.setItem('token', authResult.tokenModel.token);
    localStorage.setItem('expiresAt', JSON.stringify(expiresAt.getTime()));
    localStorage.setItem('user', JSON.stringify(authResult.user));
  }
}
