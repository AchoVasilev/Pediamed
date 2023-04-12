import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, shareReplay, tap } from 'rxjs';
import { RegisterParent } from 'src/app/models/user/registerParent';
import { environment } from 'src/environments/environment';
import { AuthResult, UserModel } from './authResult';
import * as moment from 'moment';

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

  private readonly currentUserSource = new BehaviorSubject<any>(null);
  readonly currentUser$ = this.currentUserSource.asObservable();
  
  constructor(private httpClient: HttpClient) { }

  login(email: string, password: string, persist: boolean): Observable<AuthResult> {
    return this.httpClient.post<AuthResult>(this.apiUrl + '/login', {
      email,
      password,
      persist
    }, this.httpOptions)
      .pipe(
        tap(data => {
          this.setSession(data);
        }),
        shareReplay());
  }

  register(register: RegisterParent): Observable<any> {
    return this.httpClient.post<any>(this.apiUrl + '/register', register, this.httpOptions);
  }

  logout() {
    this.httpClient.post(this.apiUrl + '/logout', {})
      .subscribe(r => {
        localStorage.removeItem('token');
        localStorage.removeItem('expiresAt');
      })
  }

  isLoggedIn() {
    return moment().isBefore(this.getExpiration()) && this.getToken() !== null;
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

  getToken() {
    return localStorage.getItem('token');
  }

  getUser() {

  }

  getExpiration() {
    const expiration = localStorage.getItem("expiresAt");
    const expiresAt = JSON.parse(expiration!);
    return moment(expiresAt);
  }

  private setSession(authResult: AuthResult) {
    const expiresAt = moment().add(authResult.tokenModel.expiresAt);
    localStorage.setItem('token', authResult.tokenModel.token);
    localStorage.setItem('expiresAt', JSON.stringify(expiresAt.valueOf()))
    localStorage.setItem('userId', authResult.id);
  }
}
