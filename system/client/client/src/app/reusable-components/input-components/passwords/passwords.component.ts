import { FormControl, FormGroup } from '@angular/forms';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-passwords',
  templateUrl: './passwords.component.html',
  styleUrls: ['./passwords.component.css']
})
export class PasswordsComponent {

  @Input()
  passwordsGroup!: FormGroup;

  get password(): FormControl {
    return this.passwordsGroup.get('password') as FormControl;
  }

  get repeatPassword(): FormControl {
    return this.passwordsGroup.get('repeatPassword') as FormControl;
  }
}
