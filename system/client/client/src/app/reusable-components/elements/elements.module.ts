import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material/material.module';
import { ButtonComponent } from './button/button.component';
import { FlatButtonComponent } from './flat-button/flat-button.component';
import { PaginationComponent } from './pagination/pagination.component';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { PaginatorIntl } from './pagination/internationalization/paginator-intl';



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
  ],
  providers:[
    {provide: MatPaginatorIntl, useClass: PaginatorIntl}
  ]
})
export class ElementsModule { }
