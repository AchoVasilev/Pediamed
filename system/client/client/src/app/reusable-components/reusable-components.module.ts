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

@NgModule({
  declarations: [
    ScheduleDialogComponent,
    SchedulingDialogComponent,
    PhoneInputComponent,
    FormFieldComponent,
    EmailComponent,
  ],
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatOptionModule,
    MatIconModule,
  ],
  exports: [
    ScheduleDialogComponent,
    SchedulingDialogComponent,
    PhoneInputComponent,
    FormFieldComponent,
    EmailComponent
  ],
})
export class ReusableComponentsModule {}