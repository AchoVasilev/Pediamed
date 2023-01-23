import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
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

  constructor(private authService: AuthService, private router: Router, private snackBar: MatSnackBar) {
    this.form = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
      persist: new FormControl(false)
    });
  }

  validateForm(control: string, form: FormGroup = this.form) {
    return shouldShowErrorForControl(control, form);
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
