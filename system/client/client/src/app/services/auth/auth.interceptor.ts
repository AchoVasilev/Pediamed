import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HTTP_INTERCEPTORS,
} from '@angular/common/http';
import { Observable, finalize, tap } from 'rxjs';
import { AuthService } from './auth.service';
import { Router } from '@angular/router';
import { LoadingService } from '../loading/loading.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(
    private authService: AuthService,
    private router: Router,
    private loadingService: LoadingService
  ) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    this.loadingService.setLoading(true);

    if (this.authService.isLoggedIn()) {
      const clonedReq = request.clone({
        headers: request.headers.set(
          'Authorization',
          'Bearer ' + localStorage.getItem('token')
        ),
        withCredentials: true,
      });

      return next.handle(clonedReq).pipe(
        tap({
          next: () => {},
          error: (err) => {
            if (err.status == 401) {
              localStorage.removeItem('token');

              setTimeout(() => {
                this.router.navigateByUrl('/auth/login');
              }, 500);
            }
          },
          finalize: () => {
            this.loadingService.setLoading(false);
          },
        })
      );
    }

    return next.handle(request).pipe(
      finalize(() => {
        this.loadingService.setLoading(false);
      })
    );
  }
}

export const AuthInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
];
