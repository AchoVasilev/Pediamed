import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Constants } from 'src/app/utils/constants';
import {
  checkForMaxLength,
  checkForMinLength,
  checkForRegEx,
  parseErrorMessage,
  shouldShowErrorForControl,
} from 'src/app/utils/formValidator';

@Component({
  selector: 'app-phone-input',
  templateUrl: './phone-input.component.html',
  styleUrls: ['./phone-input.component.css'],
})
export class PhoneInputComponent {
  phoneMinLength = Constants.phoneMinLength;
  phoneMaxLength = Constants.phoneMaxLength;

  phoneNumberControl: FormControl = new FormControl('', [
    Validators.required,
    Validators.minLength(this.phoneMinLength),
    Validators.maxLength(this.phoneMaxLength),
    Validators.pattern(Constants.phoneRegExp),
  ]);

  checkForMinLength() {
    return checkForMinLength(this.phoneNumberControl);
  }

  checkForMaxLength() {
    return checkForMaxLength(this.phoneNumberControl);
  }

  checkForRegExp() {
    return checkForRegEx(this.phoneNumberControl);
  }

  validateForm() {
    return shouldShowErrorForControl(this.phoneNumberControl);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }
}
