import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PhoneInputComponent } from './phone-input/phone-input.component';
import { ScheduleDialogComponent } from './schedule-dialog/schedule-dialog.component';
import { SchedulingDialogComponent } from './scheduling-dialog/scheduling-dialog.component';
import { FormFieldComponent } from './form-field/form-field.component';
import { MatOptionModule } from '@angular/material/core';
import { MatInputModule } from '@angular/material/input';
import { EmailComponent } from './email/email.component';
import { PasswordComponent } from './password/password.component';
import { PasswordsComponent } from './passwords/passwords.component';

@NgModule({
  declarations: [
    ScheduleDialogComponent,
    SchedulingDialogComponent,
    PhoneInputComponent,
    FormFieldComponent,
    EmailComponent,
    PasswordComponent,
    PasswordsComponent,
  ],
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatOptionModule,
    MatIconModule,
    MatButtonModule
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
