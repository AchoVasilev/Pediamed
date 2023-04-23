import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { shouldShowErrorForControl, parseErrorMessage } from 'src/app/utils/formValidator';
import { openSnackBar } from 'src/app/utils/matSnackBarUtil';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  hide = true;
  form: FormGroup;
  loading = false;
  errorMsg: string = '';

  constructor(
    private authService: AuthService, 
    private router: Router, 
    private snackBar: MatSnackBar,
    private fb: FormBuilder) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      persist: [false]
    });
  }

  get emailControl(): FormControl {
    return this.form.get('email') as FormControl;
  }

  get passwordControl(): FormControl {
    return this.form.get('password') as FormControl;
  }

  validateForm(control: string, formGroup: FormGroup = this.form) {
    return shouldShowErrorForControl(formGroup.controls[control]);
  }

  getErrorMessage(control: string, numberOfSymbols?: number) {
    return parseErrorMessage(control, numberOfSymbols);
  }

  submitForm() {
    this.loading = true;
    const { email, password, persist } = this.form.value;

    this.authService.login(email, password, persist)
      .subscribe({
        next: () => {
          this.loading = false;
          this.router.navigateByUrl('');
        },
        error: (err) => {
          if (err.status === 401) {
            this.openSnackBar(err.error.message);
            this.loading = false;
          }
        }
      })
  }

  toggleHide(event: Event) {
    event.preventDefault();
    this.hide = !this.hide;
  }

  openSnackBar(message: string) {
    openSnackBar(this.snackBar, message);
  }
}
