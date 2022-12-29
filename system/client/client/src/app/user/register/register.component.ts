import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { passwordMatch } from 'src/app/shared/utils/passwordValidator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  private fieldMinLength: number = 4;
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
      phoneNumber: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(13)])
    });
  }

  toggleHide(event: Event) {
    event.preventDefault();
    this.hide = !this.hide;
  }
}
