import { Component, Inject, OnInit } from '@angular/core';
import {
  FormGroup,
  Validators,
  FormBuilder,
  FormControl,
} from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CalendarEvent } from 'angular-calendar';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { UserDataService } from 'src/app/services/data/user-data.service';
import { ScheduleService } from 'src/app/services/schedule/schedule.service';
import { Constants } from 'src/app/utils/constants';
import { SchedulingDialogComponent } from '../scheduling-dialog/scheduling-dialog.component';
import { PatientView } from 'src/app/models/user/patient';

@Component({
  selector: 'app-doctor-scheduling-dialog',
  templateUrl: './doctor-scheduling-dialog.component.html',
  styleUrls: ['./doctor-scheduling-dialog.component.css'],
})
export class DoctorSchedulingDialogComponent implements OnInit {
  startTime: string = '';
  endTime: string = '';
  dateTimeArgs: string[] = [];
  appointmentCauses: AppointmentCauseResponse[] = [];
  scheduleId: string;

  form: FormGroup = this.fb.group({
    start: this.fb.control({ value: null, disabled: true }, [
      Validators.required,
    ]),
    end: this.fb.control({ value: null, disabled: true }, [
      Validators.required,
    ]),
    patientFirstName: [
      null,
      [Validators.required, Validators.minLength(Constants.fieldMinLength)],
    ],
    patientLastName: [
      null,
      [Validators.required, Validators.minLength(Constants.fieldMinLength)],
    ],
    appointmentCauseId: [null, [Validators.required]],
  });

  event: CalendarEvent;
  fieldMinLength = Constants.fieldMinLength;
  loading: boolean = false;

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

  getControl(name: string): FormControl {
    return this.form.get(name) as FormControl;
  }

  close() {
    this.loading = true;
    let { patientId, patientFirstName, patientLastName, appointmentCauseId } =
      this.form.value;

    const data = {
      patientId,
      patientFirstName,
      patientLastName,
      appointmentCauseId,
      eventId: this.event.id,
    };

    this.scheduleService
      .scheduleDoctorAppointment(this.scheduleId, data)
      .subscribe((appointment) => {
        this.loading = false;
        this.dialogRef.close({ appointment });
      });
  }

  onPatientSelected(patient: PatientView) {
    if (patient) {
      this.form.addControl('patientId', this.fb.control([patient.id]));

      this.form.patchValue({
        patientFirstName: patient.firstName,
        patientLastName: patient.lastName,
      });
    }
  }
}
