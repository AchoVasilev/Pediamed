import { CalendarEvent } from 'angular-calendar';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { parseErrorMessage, shouldShowErrorForControl } from 'src/app/utils/formValidator';

@Component({
  selector: 'app-scheduling-dialog',
  templateUrl: './scheduling-dialog.component.html',
  styleUrls: ['./scheduling-dialog.component.css']
})
export class SchedulingDialogComponent {

  form = this.fb.group({
    start: new FormControl('', [Validators.required])
  });
  data: CalendarEvent;
  constructor(
    @Inject(MAT_DIALOG_DATA) data: CalendarEvent,
    private fb: FormBuilder, 
    private dialogRef: MatDialogRef<SchedulingDialogComponent>) {
      this.data = data;
    }

  validateForm(control: string, form: FormGroup = this.form) {
    return shouldShowErrorForControl(control, form);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }

  close() {
    const date = this.data.date;
    const { hours, endHour, intervals } = this.form.value;

    const eventData: EventDataCreate = {
      startDateTime: `${date} ${hours}`,
      endDateTime: `${date} ${endHour}`,
      intervals,
      cabinetName: this.data.cabinetName
    }
    
    this.scheduleService.postEventData(eventData)
      .subscribe({
        next: (res) => {
          openSnackBar(this.snackBar, res.message)}
      });
      
    this.dialogRef.close(true);
  }
}
