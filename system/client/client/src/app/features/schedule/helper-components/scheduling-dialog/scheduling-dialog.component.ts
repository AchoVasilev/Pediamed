import { CalendarEvent } from 'angular-calendar';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { ScheduleService } from 'src/app/services/schedule/schedule.service';
import { Constants } from 'src/app/utils/constants';
import { UserModel } from 'src/app/services/auth/authResult';

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
  currentUser?: UserModel

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

  event: CalendarEvent;
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
    this.currentUser = data.currentUser;
  }

  ngOnInit(): void {
    this.form.patchValue({
      start: this.startTime,
      end: this.endTime,
    });
  }

  getControl(name: string): FormControl {
    return this.form.get(name) as FormControl;
  }

  close() {
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

  populateWithUserData() {
    if (!this.currentUser) {
      return;
    }

    
  }
}
