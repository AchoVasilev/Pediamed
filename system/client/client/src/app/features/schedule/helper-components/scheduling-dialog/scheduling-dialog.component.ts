import { PatientService } from './../../../../services/patient/patient.service';
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
import { PatientView } from 'src/app/models/user/patient';

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
  currentUser?: UserModel;
  patients?: PatientView[];

  form: FormGroup = this.fb.group({
    start: this.fb.control({ value: null, disabled: true }, [
      Validators.required,
    ]),
    end: this.fb.control({ value: null, disabled: true }, [
      Validators.required,
    ]),
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
    appointmentCauseId: [null, [Validators.required]],
  });

  event: CalendarEvent;
  fieldMinLength = Constants.fieldMinLength;

  constructor(
    @Inject(MAT_DIALOG_DATA) data: any,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<SchedulingDialogComponent>,
    private scheduleService: ScheduleService,
    private patientService: PatientService
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
    this.buildForm();
    this.patchValues();
  }

  getControl(name: string): FormControl {
    return this.form.get(name) as FormControl;
  }

  close() {
    if (this.patients && this.patients.length > 1) {
      const { appointmentCauseId, patientId } = this.form.value;
      const data = { appointmentCauseId, patientId, eventId: this.event.id };

      this.scheduleService
        .scheduleUserAppointment(this.scheduleId, this.currentUser!.id, data)
        .subscribe((app) => this.dialogRef.close({ app }));
    } else {
      let {
        email,
        parentFirstName,
        parentLastName,
        phoneNumber,
        patientFirstName,
        patientLastName,
        appointmentCauseId,
      } = this.form.value;

      const data = {
        email,
        parentFirstName,
        parentLastName,
        phoneNumber,
        patientFirstName,
        patientLastName,
        appointmentCauseId,
        eventId: this.event.id,
      };

      this.scheduleService
        .scheduleAppointment(this.scheduleId, data)
        .subscribe((appointment) => {
          this.dialogRef.close({ appointment });
        });
    }
  }

  buildForm() {
    if (this.patients && this.patients?.length > 1) {
      this.form.addControl(
        'patientId',
        this.fb.control([null, [Validators.required]])
      );
    } else {
      this.form.addControl(
        'patientFirstName',
        this.fb.control(null, [
          Validators.required,
          Validators.minLength(Constants.fieldMinLength),
        ])
      );
      this.form.addControl(
        'patientLastName',
        this.fb.control(null, [
          Validators.required,
          Validators.minLength(Constants.fieldMinLength),
        ])
      );
    }
  }

  patchValues() {
    this.form.patchValue({
      start: this.startTime,
      end: this.endTime,
    });

    if (!this.currentUser) {
      return;
    }

    this.form.patchValue({
      email: this.currentUser.email,
      parentFirstName: this.currentUser.firstName,
      parentLastName: this.currentUser.lastName,
      phoneNumber: this.currentUser.phoneNumber,
    });
    
    this.getControl('email').disable();

    this.patientService
      .getPatientByParentId(this.currentUser.id)
      .subscribe((res) => (this.patients = res));

    if (this.patients && this.patients.length === 1) {
      this.form.patchValue({
        childFirstName: this.patients[0].firstName,
        childLastName: this.patients[0].lastName,
      });
    }
  }
}
