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
import { UserModel } from 'src/app/services/auth/authResult';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { ScheduleService } from 'src/app/services/schedule/schedule.service';

@Component({
  selector: 'app-scheduling-dialog',
  templateUrl: './scheduling-dialog.component.html',
  styleUrls: ['./scheduling-dialog.component.css'],
})
export class SchedulingDialogComponent implements OnInit {
  startTime: string = '';
  endTime: string = '';
  dateTimeArgs: string[] = [];
  appointmentCauses: AppointmentCauseResponse[] = [];
  scheduleId: string;

  form: FormGroup = this.fb.group({
    start: new FormControl({value: null, disabled: true}, [Validators.required]),
    end: new FormControl({value: null, disabled: true}, [Validators.required]),
    email: [null, [Validators.required, Validators.email]],
    parentFirstName: [
      null,
      [Validators.required, Validators.minLength(Constants.fieldMinLength)],
    ],
    parentLastName: [
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

    patientFirstName: [
      null,
      [Validators.required, Validators.minLength(Constants.fieldMinLength)]
    ],
    patientLastName: [
      null,
      [Validators.required, Validators.minLength(Constants.fieldMinLength)]
    ],
    appointmentCauseId: [
      null,
      [Validators.required]
    ]
  });

  isLoggedIn: boolean = false;
  event: CalendarEvent;
  currentUser?: UserModel;

  fieldMinLength = Constants.fieldMinLength;

  constructor(
    @Inject(MAT_DIALOG_DATA) data: any,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<SchedulingDialogComponent>,
    private scheduleService: ScheduleService
  ) {
    this.event = data.event;
    this.startTime = data.startTime;
    this.endTime = data.endTime;
    this.dateTimeArgs = data.dateTimeArgs;
    this.appointmentCauses = data.appointmentCauses;
    this.scheduleId = data.scheduleId;
  }

  ngOnInit(): void {    
    this.form.patchValue({
      start: this.startTime,
      end: this.endTime,
    });
  }

  get startTimeControl(): FormControl {
    return this.form.get('start') as FormControl;
  }

  get endTimeControl(): FormControl {
    return this.form.get('end') as FormControl;
  }

  get emailControl(): FormControl {
    return this.form.get('email') as FormControl;
  }

  get firstName(): FormControl {
    return this.form.get('parentFirstName') as FormControl;
  }

  get lastName(): FormControl {
    return this.form.get('parentLastName') as FormControl;
  }

  get phoneNumber(): FormControl {
    return this.form.get('phoneNumber') as FormControl;
  }

  get patientFirstName(): FormControl {
    return this.form.get('patientFirstName') as FormControl;
  }

  get patientLastName(): FormControl {
    return this.form.get('patientLastName') as FormControl;
  }

  get appointmentCause(): FormControl {
    return this.form.get('appointmentCauseId') as FormControl;
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

    let {email, parentFirstName, parentLastName, phoneNumber, patientFirstName, patientLastName, appointmentCauseId} = this.form.value;

    const data = {
      email,
      parentFirstName,
      parentLastName,
      phoneNumber,
      patientFirstName,
      patientLastName,
      appointmentCauseId,
      eventId: this.event.id
    };

    this.scheduleService.scheduleAppointment(this.scheduleId, data)
      .subscribe(appointment => {        
        this.dialogRef.close({appointment});
      });
  }
}
