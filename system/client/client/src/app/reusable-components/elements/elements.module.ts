import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material/material.module';
import { AutocompleteComponent } from './autocomplete/autocomplete.component';
import { ButtonComponent } from './button/button.component';
import { FlatButtonComponent } from './flat-button/flat-button.component';
import { PaginationComponent } from './pagination/pagination.component';



@NgModule({
  declarations: [
    AutocompleteComponent,
    ButtonComponent,
    FlatButtonComponent,
    PaginationComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    AutocompleteComponent,
    ButtonComponent,
    FlatButtonComponent,
    PaginationComponent
  ]
})
export class ElementsModule { }
