import { ValidatorFn, FormControl } from '@angular/forms';
import { Component, Input } from '@angular/core';
import { checkForMinLength, parseErrorMessage, shouldShowErrorForControl } from 'src/app/utils/formValidator';

@Component({
  selector: 'app-password',
  templateUrl: './password.component.html',
  styleUrls: ['./password.component.css']
})
export class PasswordComponent {

  @Input()
  validators: ValidatorFn[] = [];
  formControl: FormControl = new FormControl('', this.validators);
  hide: boolean = true;

  checkForMinLength() {
    return checkForMinLength(this.formControl);
  }

  validateForm() {
    return shouldShowErrorForControl(this.formControl);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }


  toggleHide(event: Event) {
    event.preventDefault();
    this.hide = !this.hide;
  }
}
