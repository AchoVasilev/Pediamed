import { Constants } from './../../utils/constants';
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
import * as moment from 'moment';
import { UserModel } from 'src/app/services/auth/authResult';
import { Roles } from 'src/app/models/enums/roleEnum';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { AppointmentCauseService } from 'src/app/services/appointment-cause/appointment-cause.service';

@Component({
  selector: 'app-scheduling-dialog',
  templateUrl: './scheduling-dialog.component.html',
  styleUrls: ['./scheduling-dialog.component.css'],
})
export class SchedulingDialogComponent implements OnInit {
  private dateTimePattern = 'DD/MM/YYYY HH:mm';

  startTime: string = '';
  endTime: string = '';
  dateArgs: string[] = [];
  isDoctor: boolean = false;
  appointmentCauses: AppointmentCauseResponse[] = [];
  form: FormGroup = this.fb.group({
    start: new FormControl(null, [Validators.required]),
    end: new FormControl(null, [Validators.required]),
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
    private authService: AuthService,
    private appointmentCauseService: AppointmentCauseService
  ) {
    this.event = data.event;
    this.getUser();
  }

  ngOnInit(): void {
    this.parseDate();
    this.getAppointmentCauses();
    this.form.patchValue({
      start: { value: this.startTime, disabled: true },
    });
  }

  getStartTime(): FormControl {
    return this.form.get('start') as FormControl;
  }

  getEndTime(): FormControl {
    return this.form.get('end') as FormControl;
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
        this.isDoctor = u.roles.some((r) => r === Roles.Doctor);

        if (!this.isDoctor) {
          this.form.patchValue({
            start: this.startTime,
            email: u.email,
            firstName: u.firstName,
            middleName: u.middleName,
            lastName: u.lastName,
            phoneNumber: u.phoneNumber,
          });
        }
      });
    }
  }

  getAppointmentCauses() {
    this.appointmentCauseService
      .getAppointmentCauses()
      .subscribe((a) => (this.appointmentCauses = a));
  }

  private parseDate() {
    this.startTime = moment(this.event.start).format(this.dateTimePattern);
    this.dateArgs = this.startTime.split(' ');
    this.endTime = moment(this.event.end).format(this.dateTimePattern);
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
