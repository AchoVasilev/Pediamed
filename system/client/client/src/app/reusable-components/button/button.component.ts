import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.css']
})
export class ButtonComponent {
  @Input()
  color?: string = 'primary';

  @Input()
  name?: string;

  @Input()
  disabled?: boolean;
}
