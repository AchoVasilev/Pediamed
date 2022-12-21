import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  hide: boolean = true;

  toggleHide(event: Event) {
    event.preventDefault();
    this.hide = !this.hide;
  }
}
