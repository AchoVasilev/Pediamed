import { Component } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  hide = true;

  toggleHide(event: Event) {
    event.preventDefault();
    this.hide = !this.hide;
    return this.hide;
  }
}
