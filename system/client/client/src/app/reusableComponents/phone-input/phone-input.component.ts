import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-phone-input',
  templateUrl: './phone-input.component.html',
  styleUrls: ['./phone-input.component.css'],
})
export class PhoneInputComponent {

  @Input()
  phoneNumber!: string;
  
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
}
