import { ValidatorFn, FormControl } from '@angular/forms';
import { Component, Input } from '@angular/core';
import { checkForMinLength, checkPasswordsMatch, parseErrorMessage, shouldShowErrorForControl } from 'src/app/utils/formValidator';
import { Constants } from 'src/app/utils/constants';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent {

  @Input()
  label: string = '';
  @Input()
  control!: FormControl;

  fieldMinLength: number = Constants.fieldMinLength;
  hide: boolean = true;

  checkForMinLength() {
    debugger;
    return checkForMinLength(this.control);
  }

  validateForm() {
    return shouldShowErrorForControl(this.control);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }

  checkPasswordsMatch() {
    return checkPasswordsMatch(this.control);
  }


  toggleHide(event: Event) {
    event.preventDefault();
    this.hide = !this.hide;
  }
}
