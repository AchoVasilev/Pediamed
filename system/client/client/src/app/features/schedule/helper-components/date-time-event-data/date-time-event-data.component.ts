import { Component, Input } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { EventData } from 'src/app/models/events/schedule';
import { checkEndIsBefore, checkStartIsBefore, parseErrorMessage, shouldShowErrorForControl } from 'src/app/utils/formValidator';

@Component({
  selector: 'app-date-time-event-data',
  templateUrl: './date-time-event-data.component.html',
  styleUrls: ['./date-time-event-data.component.css']
})
export class DateTimeEventDataComponent {
  @Input()
  timesGroup!: FormGroup;

  @Input()
  eventData?: EventData[];

  get startTime() {
    return this.timesGroup?.get('hours') as FormControl;
  }

  get endTime() {
    return this.timesGroup?.get('endHour') as FormControl;
  }

  validateForm(control: FormControl) {
    return shouldShowErrorForControl(control);
  }

  startIsBefore() {
    return checkStartIsBefore(this.startTime);
  }

  endIsBefore() {
    return checkEndIsBefore(this.endTime);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }
}
