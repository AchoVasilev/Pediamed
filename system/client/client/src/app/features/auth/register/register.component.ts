import { ValidatorFn } from '@angular/forms';
import { Constants } from './../../../utils/constants';
import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RegisterParent } from 'src/app/models/user/registerParent';
import { AuthService } from 'src/app/services/auth/auth.service';
import { parseErrorMessage, shouldShowErrorForControl } from 'src/app/utils/formValidator';
import { openSnackBar } from 'src/app/utils/matSnackBarUtil';
import { passwordMatch } from 'src/app/utils/passwordValidator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  fieldMinLength: number = Constants.fieldMinLength;
  phoneMinLength: number = Constants.phoneMinLength;
  phoneMaxLength: number = Constants.phoneMaxLength;
  phoneRegExp: string = Constants.phoneRegExp;
  private passwordControl = new FormControl('', [Validators.required, Validators.minLength(this.fieldMinLength)]);
  hide: boolean = true;
  loading: boolean = false;
  form: FormGroup;
  passwordValidators = [Validators.required, Validators.minLength(this.fieldMinLength)];
  repeatPasswordValidators: ValidatorFn[];

  constructor(
    private authService: AuthService, 
    private router: Router, 
    private matSnackBar: MatSnackBar, 
    private fb: FormBuilder) {

    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      passwords: this.fb.group({
        password: this.passwordControl,
        repeatPassword: ['', passwordMatch(this.passwordControl)]
      }),
      firstName: ['', [Validators.required, Validators.minLength(this.fieldMinLength)]],
      middleName: ['', [Validators.required, Validators.minLength(this.fieldMinLength)]],
      lastName: ['', [Validators.required, Validators.minLength(this.fieldMinLength)]],
      phoneNumber: ['', [
        Validators.required,
        Validators.minLength(this.phoneMinLength),
        Validators.maxLength(this.phoneMaxLength),
        Validators.pattern(this.phoneRegExp),
      ]],
      terms: [false, [Validators.requiredTrue]]
    });

    this.repeatPasswordValidators = [passwordMatch(this.passwordsGroup.controls['password'])];
  }

  get passwordsGroup(): FormGroup {
    return this.form.controls['passwords'] as FormGroup;
  }

  get emailControl(): FormControl {
    return this.form.get('email') as FormControl;
  }

  get firstName(): FormControl {
    return this.form.get('firstName') as FormControl;
  }

  get middleName(): FormControl {
    return this.form.get('middleName') as FormControl;
  }

  get lastName(): FormControl {
    return this.form.get('lastName') as FormControl;
  }

  get phoneNumber(): FormControl {
    return this.form.get('phoneNumber') as FormControl;
  }

  validateForm(control: string, formGroup: FormGroup = this.form) {
    return shouldShowErrorForControl(formGroup.controls[control]);
  }

  getErrorMessage(control: string, numberOfSymbols?: number) {
    return parseErrorMessage(control, numberOfSymbols);
  }

  openSnackBar(message: string) {
    openSnackBar(this.matSnackBar, message);
  }

  submitRegisterForm() {
    this.loading = true;
    const { email, firstName, middleName, lastName, phoneNumber, passwords } = this.form.value;
    const registerParent: RegisterParent = {
      email,
      firstName,
      middleName,
      lastName,
      phoneNumber,
      password: passwords.password
    };

    this.authService.register(registerParent)
      .subscribe({
        next: () => {
          this.loading = false;
          this.router.navigateByUrl('/auth/login');
        },
        error: (err: HttpErrorResponse) => {
          if (err.status === HttpStatusCode.BadRequest) {
            this.openSnackBar(err.error.message);
            this.loading = false;
          }
        },
      });
  }
}
