import { Constants } from './../../utils/constants';
import { UserModel } from './../../services/data/user/userModel';
import { UserDataService } from './../../services/data/user/user-data.service';
import { CalendarEvent } from 'angular-calendar';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  checkForMaxLength,
  checkForMinLength,
  parseErrorMessage,
  shouldShowErrorForControl,
} from 'src/app/utils/formValidator';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-scheduling-dialog',
  templateUrl: './scheduling-dialog.component.html',
  styleUrls: ['./scheduling-dialog.component.css'],
})
export class SchedulingDialogComponent implements OnInit {
  form = this.fb.group({
    start: new FormControl('', [Validators.required]),
    email: ['', [Validators.required, Validators.email]],
    firstName: ['', [Validators.required, Validators.minLength(Constants.fieldMinLength)]],
    middleName: ['', [Validators.required, Validators.minLength(Constants.fieldMinLength)]],
    lastName: ['', [Validators.required, Validators.minLength(Constants.fieldMinLength)]],
    phoneNumber: ['', [
      Validators.required,
      Validators.minLength(Constants.phoneMinLength),
      Validators.maxLength(Constants.phoneMaxLength),
      Validators.pattern(Constants.phoneRegExp),
    ]],
  });

  isLoggedIn: boolean = false;
  data: CalendarEvent;
  currentUser: UserModel | undefined;

  fieldMinLength = Constants.fieldMinLength;

  constructor(
    @Inject(MAT_DIALOG_DATA) data: CalendarEvent,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<SchedulingDialogComponent>,
    private userService: UserDataService,
    private authService: AuthService
  ) {
    this.data = data;
  }

  ngOnInit(): void {
    this.getUser();
    console.log(this.data);
    
  }

  get emailControl(): FormControl {
    return this.form.get('email') as FormControl;
  }

  get firstName(): FormControl {
    return this.form.get('firstName') as FormControl;
  }

  get middleName(): FormControl {
    return this.form.get('middleName') as FormControl;
  }

  get lastName(): FormControl {
    return this.form.get('lastName') as FormControl;
  }

  get phoneNumber(): FormControl {
    return this.form.get('phoneNumber') as FormControl;
  }

  getUser() {
    this.isLoggedIn = this.authService.isLoggedIn();
    if (this.isLoggedIn) {
      this.currentUser = this.userService.getUser();
    }
  }

  close() {
    // const date = this.data.date;
    // const { hours, endHour, intervals } = this.form.value;

    // const eventData: EventDataCreate = {
    //   startDateTime: `${date} ${hours}`,
    //   endDateTime: `${date} ${endHour}`,
    //   intervals,
    //   cabinetName: this.data.cabinetName,
    // };

    // this.scheduleService.postEventData(eventData).subscribe({
    //   next: (res) => {
    //     openSnackBar(this.snackBar, res.message);
    //   },
    // });

    this.dialogRef.close(true);
  }

  checkForMinLength(control: string, formGroup: FormGroup = this.form): any {
    checkForMinLength(formGroup.controls[control]);
  }

  checkForMaxLength(control: string, formGroup: FormGroup = this.form): any {
    checkForMaxLength(formGroup.controls[control]);
  }

  validateForm(control: string, formGroup: FormGroup = this.form) {
    return shouldShowErrorForControl(formGroup.controls[control]);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }
}
