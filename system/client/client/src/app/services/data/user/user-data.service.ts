import { UserModel } from './userModel';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UserDataService {
  private user: UserModel = {
    id: '',
    firstName: '',
    lastName: '',
    email: '',
    role: '',
  };

  private readonly currentUserSource = new BehaviorSubject<UserModel>(
    this.user
  );

  readonly currentUser$ = this.currentUserSource.asObservable();

  constructor() {}

  getUser(): UserModel {
    return this.currentUserSource.getValue();
  }

  isLoggedIn(): boolean {
    return this.getUser().id !== '';
  }

  setUser(user: UserModel): void {
    this.currentUserSource.next(user);
  }

  removeUser(): void {
    this.currentUserSource.next(this.user);
  }
}
