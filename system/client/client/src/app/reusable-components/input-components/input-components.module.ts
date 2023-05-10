import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material/material/material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormFieldComponent } from './form-field/form-field.component';
import { PasswordComponent } from './password/password.component';
import { PasswordsComponent } from './passwords/passwords.component';
import { PhoneInputComponent } from './phone-input/phone-input.component';
import { EmailComponent } from './email/email.component';
import { ParentFormComponent } from './parent-form/parent-form.component';
import { AutocompleteComponent } from './autocomplete/autocomplete.component';



@NgModule({
  declarations: [
    FormFieldComponent,
    PasswordComponent,
    PasswordsComponent,
    PhoneInputComponent,
    EmailComponent,
    ParentFormComponent,
    AutocompleteComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule
  ],
  exports: [
    CommonModule,
    ReactiveFormsModule,
    FormFieldComponent,
    PasswordComponent,
    PasswordsComponent,
    PhoneInputComponent,
    EmailComponent,
    ParentFormComponent,
    AutocompleteComponent
  ]
})
export class InputComponentsModule { }
