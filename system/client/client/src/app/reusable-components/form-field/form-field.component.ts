import { FormControl } from '@angular/forms';
import { Constants } from './../../utils/constants';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-form-field',
  templateUrl: './form-field.component.html',
  styleUrls: ['./form-field.component.css']
})
export class FormFieldComponent {
fieldMinLength: any = Constants.fieldMinLength;

@Input()
label!: string;

@Input()
control!: FormControl;

@Input()
matSuffix: string = '';

getErrorMessage(arg0: string,arg1: any) {
  throw new Error('Method not implemented.');
}

validateForm(arg0: string): any {
  throw new Error('Method not implemented.');
}

}
