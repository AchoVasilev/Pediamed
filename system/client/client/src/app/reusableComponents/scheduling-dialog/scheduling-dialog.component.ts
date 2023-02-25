import { Constants } from './../../utils/constants';
import { UserModel } from './../../services/data/user/userModel';
import { UserDataService } from './../../services/data/user/user-data.service';
import { CalendarEvent } from 'angular-calendar';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Component, Inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  checkForMaxLength,
  checkForMinLength,
  parseErrorMessage,
  shouldShowErrorForControl,
} from 'src/app/utils/formValidator';

@Component({
  selector: 'app-scheduling-dialog',
  templateUrl: './scheduling-dialog.component.html',
  styleUrls: ['./scheduling-dialog.component.css'],
})
export class SchedulingDialogComponent implements OnInit {
  form = this.fb.group({
    start: new FormControl('', [Validators.required]),
  });

  isLoggedIn: boolean = false;
  data: CalendarEvent;
  currentUser: UserModel | undefined;
  phoneMinLength = Constants.phoneMinLength;
  phoneMaxLength = Constants.phoneMaxLength;
  fieldMinLength = Constants.fieldMinLength;

  constructor(
    @Inject(MAT_DIALOG_DATA) data: CalendarEvent,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<SchedulingDialogComponent>,
    private userService: UserDataService
  ) {
    this.data = data;
  }

  ngOnInit(): void {
    this.getUser();
  }

  validateForm(control: string, form: FormGroup = this.form) {
    return shouldShowErrorForControl(control, form);
  }

  getErrorMessage(errorType: string, numberOfSymbols?: number) {
    return parseErrorMessage(errorType, numberOfSymbols);
  }

  getUser() {
    this.isLoggedIn = this.userService.isLoggedIn();
    if (this.isLoggedIn) {
      this.currentUser = this.userService.getUser();
    }
  }

  close() {
    const date = this.data.date;
    const { hours, endHour, intervals } = this.form.value;

    const eventData: EventDataCreate = {
      startDateTime: `${date} ${hours}`,
      endDateTime: `${date} ${endHour}`,
      intervals,
      cabinetName: this.data.cabinetName,
    };

    this.scheduleService.postEventData(eventData).subscribe({
      next: (res) => {
        openSnackBar(this.snackBar, res.message);
      },
    });

    this.dialogRef.close(true);
  }

  checkForMinLength(property: string, form: FormGroup = this.form): any {
    checkForMinLength(property, form);
  }

  checkForMaxLength(property: string, form: FormGroup = this.form): any {
    checkForMaxLength(property, form);
  }
}
