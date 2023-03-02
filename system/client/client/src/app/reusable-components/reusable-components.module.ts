import { MatDialogModule } from '@angular/material/dialog';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PhoneInputComponent } from './phone-input/phone-input.component';
import { ScheduleDialogComponent } from './schedule-dialog/schedule-dialog.component';
import { SchedulingDialogComponent } from './scheduling-dialog/scheduling-dialog.component';
import { FormFieldComponent } from './form-field/form-field.component';



@NgModule({
  declarations: [
    ScheduleDialogComponent,
    SchedulingDialogComponent,
    PhoneInputComponent,
    FormFieldComponent,
  ],
  imports: [
    CommonModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatDialogModule
  ],
  exports: [ScheduleDialogComponent, SchedulingDialogComponent, PhoneInputComponent]
})
export class ReusableComponentsModule { }
