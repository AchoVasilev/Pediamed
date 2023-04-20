import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PhoneInputComponent } from './phone-input/phone-input.component';
import { ScheduleDialogComponent } from './schedule-dialog/schedule-dialog.component';
import { SchedulingDialogComponent } from './scheduling-dialog/scheduling-dialog.component';
import { FormFieldComponent } from './form-field/form-field.component';
import { EmailComponent } from './email/email.component';
import { PasswordComponent } from './password/password.component';
import { PasswordsComponent } from './passwords/passwords.component';
import { AppointmentCauseSelectComponent } from './appointment-cause-select/appointment-cause-select.component';
import { DoctorSchedulingDialogComponent } from './doctor-scheduling-dialog/doctor-scheduling-dialog.component';
import { RegisteredUserSchedulingDialogComponent } from './registered-user-scheduling-dialog/registered-user-scheduling-dialog.component';
import { MaterialModule } from '../material/material/material.module';

@NgModule({
  declarations: [
    ScheduleDialogComponent,
    SchedulingDialogComponent,
    PhoneInputComponent,
    FormFieldComponent,
    EmailComponent,
    PasswordComponent,
    PasswordsComponent,
    AppointmentCauseSelectComponent,
    DoctorSchedulingDialogComponent,
    RegisteredUserSchedulingDialogComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  exports: [
    ScheduleDialogComponent,
    SchedulingDialogComponent,
    PhoneInputComponent,
    FormFieldComponent,
    EmailComponent,
    PasswordComponent,
    PasswordsComponent
  ],
})
export class ReusableComponentsModule {}
