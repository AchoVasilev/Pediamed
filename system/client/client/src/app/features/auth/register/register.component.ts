import { HttpErrorResponse, HttpStatusCode } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RegisterParent } from 'src/app/models/user/registerParent';
import { AuthService } from 'src/app/services/auth/auth.service';
import { checkForMaxLength, checkForMinLength, parseErrorMessage, shouldShowErrorForControl } from 'src/app/utils/formValidator';
import { openSnackBar } from 'src/app/utils/matSnackBarUtil';
import { passwordMatch } from 'src/app/utils/passwordValidator';

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
  loading: boolean = false;
  form: FormGroup;

  constructor(private authService: AuthService, private router: Router, private matSnackBar: MatSnackBar) {

    this.form = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      passwords: new FormGroup({
        password: this.passwordControl,
        repeatPassword: new FormControl('', [passwordMatch(this.passwordControl)])
      }),
      firstName: new FormControl('', [Validators.required, Validators.minLength(this.fieldMinLength)]),
      middleName: new FormControl('', [Validators.required, Validators.minLength(this.fieldMinLength)]),
      lastName: new FormControl('', [Validators.required, Validators.minLength(this.fieldMinLength)]),
      phoneNumber: new FormControl('', [Validators.required, Validators.minLength(this.phoneMinLength), Validators.maxLength(this.phoneMaxLength)]),
      terms: new FormControl(false, [Validators.requiredTrue])
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

  toggleHide(event: Event) {
    event.preventDefault();
    this.hide = !this.hide;
  }
}
