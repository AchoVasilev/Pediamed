import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GalleryRoutingModule } from './gallery-routing.module';
import { GalleryComponent } from './gallery/gallery.component';
import { CardComponent } from './card/card.component';
import { MaterialModule } from 'src/app/material/material/material.module';
import { ElementsModule } from 'src/app/reusable-components/elements/elements.module';


@NgModule({
  declarations: [
    GalleryComponent,
    CardComponent
  ],
  imports: [
    CommonModule,
    GalleryRoutingModule,
    MaterialModule,
    ElementsModule
  ]
})
export class GalleryModule { }
