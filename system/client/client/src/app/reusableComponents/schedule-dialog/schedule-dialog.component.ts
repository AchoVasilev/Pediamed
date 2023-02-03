import { EventDataCreate } from './../../models/events/schedule';
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
} from '@angular/forms';
import { EventDataInput } from '../../models/events/schedule';
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {
  parseErrorMessage,
  shouldShowErrorForControl,
} from 'src/app/utils/formValidator';

@Component({
  selector: 'app-schedule-dialog',
  templateUrl: './schedule-dialog.component.html',
  styleUrls: ['./schedule-dialog.component.css'],
})
export class ScheduleDialogComponent {
  data: EventDataInput;
  form: FormGroup = this.fb.group({
    hours: new FormControl('', [Validators.required]),
    endHour: new FormControl('', [Validators.required]),
    intervals: new FormControl('', [Validators.required]),
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) data: EventDataInput,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ScheduleDialogComponent>
  ) {
    this.data = data;
  }

  cancel() {
    this.dialogRef.close(false);
  }

  close() {
    const date = this.data.date;
    const { hours, endHour, intervals } = this.form.value;

    const eventData: EventDataCreate = {
      date,
      startHour: hours,
      endHour,
      intervals
    }
    
    this.dialogRef.close(eventData);
  }

  validateForm(control: string, form: FormGroup = this.form) {
    return shouldShowErrorForControl(control, form);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }
}
