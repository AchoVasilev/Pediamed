import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-flat-button',
  templateUrl: './flat-button.component.html',
  styleUrls: ['./flat-button.component.css'],
})
export class FlatButtonComponent {
  @Input()
  disabled?: boolean;

  @Input()
  loading?: boolean;

  @Input()
  text?: string

  @Input()
  color?: string = 'primary'
}
