import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';

@Component({
  selector: 'app-appointment-cause-select',
  templateUrl: './appointment-cause-select.component.html',
  styleUrls: ['./appointment-cause-select.component.css']
})
export class AppointmentCauseSelectComponent {

  @Input()
  appointmentCauses: AppointmentCauseResponse[] = [];

  @Input()
  control!: FormControl;
}
