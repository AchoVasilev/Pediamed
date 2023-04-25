import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PhoneInputComponent } from './phone-input/phone-input.component';
import { SchedulingDialogComponent } from '../features/schedule/helper-components/scheduling-dialog/scheduling-dialog.component';
import { FormFieldComponent } from './form-field/form-field.component';
import { EmailComponent } from './email/email.component';
import { PasswordComponent } from './password/password.component';
import { PasswordsComponent } from './passwords/passwords.component';
import { AppointmentCauseSelectComponent } from './appointment-cause-select/appointment-cause-select.component';
import { MaterialModule } from '../material/material/material.module';
import { ButtonComponent } from './button/button.component';
import { AutocompleteComponent } from './autocomplete/autocomplete.component';

@NgModule({
  declarations: [
    PhoneInputComponent,
    FormFieldComponent,
    EmailComponent,
    PasswordComponent,
    PasswordsComponent,
    AppointmentCauseSelectComponent,
    ButtonComponent,
    AutocompleteComponent,
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  exports: [
    PhoneInputComponent,
    FormFieldComponent,
    EmailComponent,
    PasswordComponent,
    PasswordsComponent,
    AppointmentCauseSelectComponent,
    ButtonComponent
  ],
})
export class ReusableComponentsModule {}
