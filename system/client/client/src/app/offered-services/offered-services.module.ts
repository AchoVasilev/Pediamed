import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { OfferedServicesRoutingModule } from './offered-services-routing.module';
import { OfferedServicesComponent } from './offered-services/offered-services.component';


@NgModule({
  declarations: [
    OfferedServicesComponent,
  ],
  imports: [
    CommonModule,
    OfferedServicesRoutingModule,
    CarouselModule
  ]
})
export class OfferedServicesModule { }
