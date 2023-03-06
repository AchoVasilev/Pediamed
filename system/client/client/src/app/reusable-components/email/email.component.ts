import { FormControl, Validators } from '@angular/forms';
import { Component } from '@angular/core';
import { parseErrorMessage, shouldShowErrorForControl } from 'src/app/utils/formValidator';

@Component({
  selector: 'app-email',
  templateUrl: './email.component.html',
  styleUrls: ['./email.component.css']
})
export class EmailComponent {

  emailControl: FormControl = new FormControl('', [Validators.required, Validators.email]);

  validateForm() {
    return shouldShowErrorForControl(this.emailControl);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }
}
