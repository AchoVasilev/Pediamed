import { AbstractControl, Validators } from '@angular/forms';
import { FormControl } from '@angular/forms';
import { Constants } from './../../utils/constants';
import { Component, Input } from '@angular/core';
import { parseErrorMessage, shouldShowErrorForControl } from 'src/app/utils/formValidator';

@Component({
  selector: 'app-form-field',
  templateUrl: './form-field.component.html',
  styleUrls: ['./form-field.component.css'],
})
export class FormFieldComponent {
  fieldMinLength: number = Constants.fieldMinLength;

  @Input()
  label: string = '';

  @Input()
  control!: FormControl;

  @Input()
  materialSuffix: string = '';

  @Input()
  placeholder: string = '';

  validateForm() {
    return shouldShowErrorForControl(this.control);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }
}
