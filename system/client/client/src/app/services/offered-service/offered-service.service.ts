import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OfferedServiceView } from 'src/app/models/offeredServiceView';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OfferedServiceService {
  private apiUrl: string = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  getOfferedServices(): Observable<OfferedServiceView[]> {
    return this.httpClient.get<OfferedServiceView[]>(`${this.apiUrl}/offered-service`);
  }
}
