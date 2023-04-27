import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ScheduleRoutingModule } from './schedule-routing.module';
import { ScheduleComponent } from './schedule/schedule.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { ScheduleDialogComponent } from './helper-components/schedule-dialog/schedule-dialog.component';
import { SchedulingDialogComponent } from './helper-components/scheduling-dialog/scheduling-dialog.component';
import { DoctorSchedulingDialogComponent } from './helper-components/doctor-scheduling-dialog/doctor-scheduling-dialog.component';
import { RegisteredUserSchedulingDialogComponent } from './helper-components/registered-user-scheduling-dialog/registered-user-scheduling-dialog.component';
import { DateTimeEventDataComponent } from './helper-components/date-time-event-data/date-time-event-data.component';
import { MaterialModule } from 'src/app/material/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { ReusableComponentsModule } from 'src/app/reusable-components/reusable-components.module';

@NgModule({
  declarations: [
    ScheduleComponent,
    ScheduleDialogComponent,
    SchedulingDialogComponent,
    DoctorSchedulingDialogComponent,
    RegisteredUserSchedulingDialogComponent,
    DateTimeEventDataComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ScheduleRoutingModule,
    MaterialModule,
    ReusableComponentsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
  ],
})
export class ScheduleModule {}
