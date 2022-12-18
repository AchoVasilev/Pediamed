import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OfferedServicesRoutingModule } from './offered-services-routing.module';
import { OfferedServicesComponent } from './offered-services.component';
import { NgbCarouselModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    OfferedServicesComponent
  ],
  imports: [
    CommonModule,
    OfferedServicesRoutingModule,
    NgbCarouselModule
  ]
})
export class OfferedServicesModule { }
