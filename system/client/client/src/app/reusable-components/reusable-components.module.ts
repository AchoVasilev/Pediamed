import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PhoneInputComponent } from './phone-input/phone-input.component';
import { ScheduleDialogComponent } from './schedule-dialog/schedule-dialog.component';
import { SchedulingDialogComponent } from './scheduling-dialog/scheduling-dialog.component';



@NgModule({
  declarations: [
    ScheduleDialogComponent,
    SchedulingDialogComponent,
    PhoneInputComponent,
  ],
  imports: [
    CommonModule
  ],
  //@ts-ignore
  exports: [ScheduleDialogComponent, SchedulingDialogComponent, PhoneInputComponent]
})
export class ReusableComponentsModule { }
