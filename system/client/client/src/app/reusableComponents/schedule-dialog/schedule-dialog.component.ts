import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { EventData } from './../../models/events/eventData';
import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-schedule-dialog',
  templateUrl: './schedule-dialog.component.html',
  styleUrls: ['./schedule-dialog.component.css']
})
export class ScheduleDialogComponent {

  data!: EventData[];
  selectedHour: string = '';
  selectedMinutes: number = 0;
  form: FormGroup = this.fb.group({
    hours: new FormControl('', [Validators.required]),
    intervals: new FormControl('', [Validators.required]) 
  });

  constructor(
    @Inject(MAT_DIALOG_DATA) data: EventData[], 
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ScheduleDialogComponent>) {
    this.data = data;
  }

  cancel() {
    this.dialogRef.close(false);
  }

  close() {
    this.dialogRef.close(this.form.value);
  }
}
