import { Constants } from './../../utils/constants';
import { CalendarEvent } from 'angular-calendar';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, Input, OnInit } from '@angular/core';
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
import * as moment from 'moment';
import { UserModel } from 'src/app/services/auth/authResult';

@Component({
  selector: 'app-scheduling-dialog',
  templateUrl: './scheduling-dialog.component.html',
  styleUrls: ['./scheduling-dialog.component.css'],
})
export class SchedulingDialogComponent implements OnInit {
  private dateTimePattern = 'DD/MM/YYYY HH:mm';

  date: string = '';
  dateArgs: string[] = [];

  form: FormGroup = this.fb.group({
    start: new FormControl(null, [Validators.required]),
    email: [null, [Validators.required, Validators.email]],
    firstName: [
      null,
      [Validators.required, Validators.minLength(Constants.fieldMinLength)],
    ],
    middleName: [
      null,
      [Validators.required, Validators.minLength(Constants.fieldMinLength)],
    ],
    lastName: [
      null,
      [Validators.required, Validators.minLength(Constants.fieldMinLength)],
    ],
    phoneNumber: [
      null,
      [
        Validators.required,
        Validators.minLength(Constants.phoneMinLength),
        Validators.maxLength(Constants.phoneMaxLength),
        Validators.pattern(Constants.phoneRegExp),
      ],
    ],
  });

  isLoggedIn: boolean = false;
  event: CalendarEvent;
  currentUser?: UserModel;

  fieldMinLength = Constants.fieldMinLength;

  constructor(
    @Inject(MAT_DIALOG_DATA) data: any,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<SchedulingDialogComponent>,
    private authService: AuthService
  ) {
    this.event = data.event;
    this.getUser();
  }

  ngOnInit(): void {
    this.parseDate();

    this.form.patchValue({
      start: this.date
    });
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
      this.authService.getUser().subscribe((u) => {
        this.currentUser = u;
        this.form.patchValue({
          start: this.date,
          email: u.email,
          firstName: u.firstName,
          middleName: u.middleName,
          lastName: u.lastName,
          phoneNumber: u.phoneNumber,
        });
      });
    }
  }

  private parseDate() {
    this.date = moment(this.event.start).format(this.dateTimePattern);
    this.dateArgs = this.date.split(' ');
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
