import { CabinetResponse } from './../../models/events/schedule';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CabinetService {
  private apiUrl: string = environment.apiUrl + '/cabinet'

  constructor(private httpClinet: HttpClient) { }

  getCabinets(): Observable<CabinetResponse> {
    return this.httpClinet.get<CabinetResponse>(this.apiUrl);
  }
}
