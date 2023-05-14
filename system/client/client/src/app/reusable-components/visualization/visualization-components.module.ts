import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppointmentCauseSelectComponent } from './appointment-cause-select/appointment-cause-select.component';
import { MaterialModule } from 'src/app/material/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    AppointmentCauseSelectComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  exports: [
    AppointmentCauseSelectComponent
  ]
})
export class VisualizationComponentsModule { }
