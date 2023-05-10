import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material/material.module';
import { ButtonComponent } from './button/button.component';
import { FlatButtonComponent } from './flat-button/flat-button.component';
import { PaginationComponent } from './pagination/pagination.component';



@NgModule({
  declarations: [
    ButtonComponent,
    FlatButtonComponent,
    PaginationComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    ButtonComponent,
    FlatButtonComponent,
    PaginationComponent
  ]
})
export class ElementsModule { }
