import { VisualizationComponentsModule } from './../../reusable-components/visualization/visualization-components.module';
import { NgModule } from '@angular/core';

import { ScheduleRoutingModule } from './schedule-routing.module';
import { ScheduleComponent } from './schedule/schedule.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { ScheduleDialogComponent } from './helper-components/schedule-dialog/schedule-dialog.component';
import { SchedulingDialogComponent } from './helper-components/scheduling-dialog/scheduling-dialog.component';
import { DoctorSchedulingDialogComponent } from './helper-components/doctor-scheduling-dialog/doctor-scheduling-dialog.component';
import { DateTimeEventDataComponent } from './helper-components/date-time-event-data/date-time-event-data.component';
import { MaterialModule } from 'src/app/material/material/material.module';
import { ScheduleDialogService } from 'src/app/services/schedule/schedule-dialog/schedule-dialog.service';
import { InputComponentsModule } from 'src/app/reusable-components/input-components/input-components.module';
import { ElementsModule } from 'src/app/reusable-components/elements/elements.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    ScheduleComponent,
    ScheduleDialogComponent,
    SchedulingDialogComponent,
    DoctorSchedulingDialogComponent,
    DateTimeEventDataComponent,
  ],
  imports: [
    ScheduleRoutingModule,
    MaterialModule,
    VisualizationComponentsModule,
    InputComponentsModule,
    ElementsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
  ],
  providers: [
    ScheduleDialogService
  ]
})
export class ScheduleModule {}
