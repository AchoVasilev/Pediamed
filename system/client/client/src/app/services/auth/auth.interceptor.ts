import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {

    if (this.authService.isLoggedIn()) {
      const clonedReq = request.clone({
        headers: request.headers.set(
          'Authorization',
          'Bearer ' + this.authService.getToken()
        ),
        withCredentials: true,
      });

      return next.handle(clonedReq).pipe(
        tap({
          next: () => {},
          error: (err) => {
            if (err.status == 401) {
              this.authService.removeToken();

              setTimeout(() => {
                this.router.navigateByUrl('/auth/login');
              }, 500);
            }
          }
        })
      );
    }

    return next.handle(request);
  }
}

export const AuthInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
];
