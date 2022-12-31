import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { checkForMaxLength, checkForMinLength, parseErrorMessage, shouldShowErrorForControl } from 'src/app/shared/utils/formValidator';
import { passwordMatch } from 'src/app/shared/utils/passwordValidator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  fieldMinLength: number = 4;
  phoneMinLength: number = 10;
  phoneMaxLength: number = 13;
  private passwordControl = new FormControl('', [Validators.required, Validators.minLength(this.fieldMinLength)]);
  hide: boolean = true;
  form: FormGroup;

  constructor() {
    this.form = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      passwords: new FormGroup({
        password: this.passwordControl,
        repeatPassword: new FormControl('', [passwordMatch(this.passwordControl)])
      }),
      firstName: new FormControl('', [Validators.required, Validators.minLength(this.fieldMinLength)]),
      middleName: new FormControl('', [Validators.required, Validators.minLength(this.fieldMinLength)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(this.fieldMinLength)]),
      phoneNumber: new FormControl('', [Validators.required, Validators.minLength(this.phoneMinLength), Validators.maxLength(this.phoneMaxLength)])
    });
  }

  get passwordsGroup(): FormGroup {
    return this.form.controls['passwords'] as FormGroup;
  }

  validateForm(control: string, form: FormGroup = this.form) {
    return shouldShowErrorForControl(control, form);
  }

  checkForMinLength(control: string, formGroup: FormGroup = this.form) {
    return checkForMinLength(control, formGroup);
  }

  checkForMaxLength(control: string, formGroup: FormGroup = this.form) {
    return checkForMaxLength(control, formGroup);
  }

  getErrorMessage(control: string, numberOfSymbols?: number) {

    return parseErrorMessage(control, numberOfSymbols);
  }

  toggleHide(event: Event) {
    event.preventDefault();
    this.hide = !this.hide;
  }
}
