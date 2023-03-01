import { Component, Input } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Constants } from 'src/app/utils/constants';
import { checkForMaxLength, checkForMinLength, parseErrorMessage, shouldShowErrorForControl } from 'src/app/utils/formValidator';

@Component({
  selector: 'app-phone-input',
  templateUrl: './phone-input.component.html',
  styleUrls: ['./phone-input.component.css'],
})
export class PhoneInputComponent {
  phoneMinLength = Constants.phoneMinLength;
  phoneMaxLength = Constants.phoneMaxLength;
  
  @Input()
  phoneNumber!: FormControl;
  
  @Input()
  form!: FormGroup;
  
  checkForMinLength(property: string, form: FormGroup = this.form): any {
    checkForMinLength(property, form);
  }

  checkForMaxLength(property: string, form: FormGroup = this.form): any {
    checkForMaxLength(property, form);
  }

  validateForm(control: string, form: FormGroup = this.form) {
    return shouldShowErrorForControl(control, form);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }
}
