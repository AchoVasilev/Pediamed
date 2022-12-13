import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OfferedServicesRoutingModule } from './offered-services-routing.module';
import { OfferedServicesComponent } from './offered-services.component';


@NgModule({
  declarations: [
    OfferedServicesComponent
  ],
  imports: [
    CommonModule,
    OfferedServicesRoutingModule
  ]
})
export class OfferedServicesModule { }
