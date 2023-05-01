import { Component, Inject } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { EventData } from 'src/app/models/events/schedule';
import { UserModel } from 'src/app/services/auth/authResult';
import { ScheduleService } from 'src/app/services/schedule/schedule.service';

@Component({
  selector: 'app-registered-user-scheduling-dialog',
  templateUrl: './registered-user-scheduling-dialog.component.html',
  styleUrls: ['./registered-user-scheduling-dialog.component.css'],
})
export class RegisteredUserSchedulingDialogComponent {

  currentUser?: UserModel;
  event?: EventData;
  appointmentCauses: AppointmentCauseResponse[] = [];
  dateTimeArgs: string[] = [];
  scheduleId?: string;
  
  constructor(
    @Inject(MAT_DIALOG_DATA) data: any,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<RegisteredUserSchedulingDialogComponent>,
    private scheduleService: ScheduleService
  ) {

  }


}
