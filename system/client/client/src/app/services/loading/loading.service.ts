import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
  private isLoading: boolean = false;

  setLoading(loading:boolean) {
    this.isLoading = loading;
  }

  getLoading() {
    return this.isLoading;
  }
}
