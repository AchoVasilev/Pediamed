import { UserModel } from 'src/app/services/auth/authResult';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Roles } from 'src/app/models/enums/roleEnum';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  private loggedInSubject = new BehaviorSubject<boolean>(false);
  private isDoctorSubject = new BehaviorSubject<boolean>(false);
  private userSubject = new BehaviorSubject<UserModel>(null!);

  constructor() { }

  getLogin() {
    return this.loggedInSubject.value;
  }

  setLogin(value: boolean) {
    this.loggedInSubject.next(value);
  }

  isDoctor() {
    return this.isDoctorSubject.value;
  }

  getUser() {
    return this.userSubject.value;
  }

  setUser(user: UserModel) {
    this.userSubject.next(user);
    const isDoctor = this.getUser().roles.some(r => r === Roles.Doctor);
    this.isDoctorSubject.next(isDoctor);
  }
}
