import { UserModel } from 'src/app/services/auth/authResult';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Roles } from 'src/app/models/enums/roleEnum';
import { PatientView } from 'src/app/models/user/patient';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  private loggedInSubject = new BehaviorSubject<boolean>(false);
  private isDoctorSubject = new BehaviorSubject<boolean>(false);
  private userSubject = new BehaviorSubject<UserModel>(null!);
  private patientsSubject = new BehaviorSubject<PatientView[]>([]);

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
    const isDoctor = user.roles.some(r => r === Roles.Doctor);
    
    this.isDoctorSubject.next(isDoctor);
  }

  setPatients(patients: PatientView[]) {
    this.patientsSubject.next(patients);
  }

  getPatients() {
    return this.patientsSubject.value;
  }

  onLogOut() {
    this.userSubject.next(null!);
    this.loggedInSubject.next(false);
    this.patientsSubject.next([]);
    this.isDoctorSubject.next(false);
  }
}
