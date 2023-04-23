import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay, tap } from 'rxjs';
import { RegisterParent } from 'src/app/models/user/registerParent';
import { environment } from 'src/environments/environment';
import { AuthResult, UserModel } from './authResult';
import { UserDataService } from '../data/user-data.service';
import { isAfter, parseJSON, toDate } from 'date-fns';
import { Router } from '@angular/router';

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
  
  constructor(private httpClient: HttpClient, private userDataService: UserDataService, private router: Router) { }

  login(email: string, password: string, persist: boolean): Observable<AuthResult> {
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
        shareReplay());
  }

  register(register: RegisterParent): Observable<any> {
    return this.httpClient.post<any>(this.apiUrlWithPrefix + '/register', register, this.httpOptions);
  }

  logout() {
    this.httpClient.post(this.apiUrlWithPrefix + '/logout', this.httpOptions)
      .subscribe(r => {
        localStorage.removeItem('token');
        localStorage.removeItem('expiresAt');
        this.userDataService.setLogin(false);
        this.router.navigateByUrl('');
      })
  }

  isLoggedIn(): boolean {
    const isLogged = isAfter(this.getExpiration(), new Date()) && this.getToken() !== null;
    this.userDataService.setLogin(isLogged);
    if (isLogged && !this.userDataService.getUser()) {
      this.userDataService.setUser(JSON.parse(localStorage.getItem('user')!));
    }

    return isLogged;
  }

  getToken() {
    return localStorage.getItem('token');
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
