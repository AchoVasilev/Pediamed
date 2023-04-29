import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { OfferedServiceView } from 'src/app/models/offeredServiceView';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OfferedServiceService {
  private apiUrl: string = environment.apiUrl;
  private offeredService$?: Observable<OfferedServiceView[]>;

  constructor(private httpClient: HttpClient) { }

  getOfferedServices(): Observable<OfferedServiceView[]> {
    if (!this.offeredService$) {
      this.offeredService$ = this.httpClient
        .get<OfferedServiceView[]>(`${this.apiUrl}/offered-service`)
        .pipe(shareReplay(1));
    }

    return this.offeredService$;
  }
}
