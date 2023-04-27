import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
} from '@angular/forms';
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {
  parseErrorMessage,
  shouldShowErrorForControl,
} from 'src/app/utils/formValidator';
import { ScheduleService } from 'src/app/services/schedule/schedule.service';
import {
  validateEndDate,
  validateStartDate,
} from 'src/app/utils/dateTimeValidator';
import {
  EventDataCreate,
  EventDataInput,
} from 'src/app/models/events/schedule';

@Component({
  selector: 'app-schedule-dialog',
  templateUrl: './schedule-dialog.component.html',
  styleUrls: ['./schedule-dialog.component.css'],
})
export class ScheduleDialogComponent {
  data: EventDataInput;
  form: FormGroup;

  constructor(
    @Inject(MAT_DIALOG_DATA) data: EventDataInput,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ScheduleDialogComponent>,
    private scheduleService: ScheduleService
  ) {
    this.data = data;

    const hours = new FormControl('', [
      Validators.required,
      validateStartDate(this.data.date),
    ]);

    this.form = this.fb.group({
      hoursGroup: this.fb.group({
        hours: hours,
        endHour: new FormControl('', [
          Validators.required,
          validateEndDate(this.data.date, hours),
        ]),
      }),
      intervals: new FormControl('', [Validators.required]),
    });
  }

  close() {
    const date = this.data.date;
    const { hoursGroup, intervals } = this.form.value;
    
    const eventData: EventDataCreate = {
      startDateTime: `${date} ${hoursGroup.hours}`,
      endDateTime: `${date} ${hoursGroup.endHour}`,
      intervals,
      cabinetName: this.data.cabinetName,
    };

    this.scheduleService
      .postEventData(eventData)
      .subscribe((events) => this.dialogRef.close({ events }));
  }

  get hoursGroup() {
    return this.form.controls['hoursGroup'] as FormGroup;
  }

  validateForm(control: string, formGroup: FormGroup = this.form) {
    return shouldShowErrorForControl(formGroup.controls[control]);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }
}
