import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-parent-form',
  templateUrl: './parent-form.component.html',
  styleUrls: ['./parent-form.component.css']
})
export class ParentFormComponent {

  @Input()
  email!: FormControl;

  @Input()
  phoneNumber!: FormControl;

  @Input()
  firstName!: FormControl;

  @Input()
  lastName!: FormControl;
}
