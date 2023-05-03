import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CalendarEvent } from 'angular-calendar';
import { format } from 'date-fns';
import { DoctorSchedulingDialogComponent } from 'src/app/features/schedule/helper-components/doctor-scheduling-dialog/doctor-scheduling-dialog.component';
import { SchedulingDialogComponent } from 'src/app/features/schedule/helper-components/scheduling-dialog/scheduling-dialog.component';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { EventDataInput, MetaInfo } from 'src/app/models/events/schedule';
import { Constants } from 'src/app/utils/constants';
import { UserDataService } from '../../data/user-data.service';
import { ScheduleDialogComponent } from 'src/app/features/schedule/helper-components/schedule-dialog/schedule-dialog.component';

@Injectable({
  providedIn: 'root'
})
export class ScheduleDialogService {

  constructor(private dialog: MatDialog, private userDataService: UserDataService) { }

  openSchedulingDialog(event: CalendarEvent<MetaInfo>, scheduleId: string, appointmentCauses: AppointmentCauseResponse[]) {
    const startTime = format(event.start, Constants.dateTimePattern);
    const endTime = format(event.end!, Constants.dateTimePattern);
    const dateTimeArgs = startTime.split(' ');
    
    if (this.isDoctor()) {
      return this.dialog
        .open(DoctorSchedulingDialogComponent, {
          data: {
            event: event,
            appointmentCauses: appointmentCauses,
            startTime,
            endTime,
            dateTimeArgs,
            scheduleId: scheduleId,
          },
        })
        .afterClosed();
    } else {
     return this.dialog
        .open(SchedulingDialogComponent, {
          data: {
            event: event,
            appointmentCauses: appointmentCauses,
            startTime,
            endTime,
            dateTimeArgs,
            scheduleId: scheduleId,
            currentUser: this.getUser()
          },
        })
        .afterClosed();
    }
  }

  openEventDataDialog(eventDataInput: EventDataInput) {
    return  this.dialog
    .open(ScheduleDialogComponent, {
      data: eventDataInput,
    })
    .afterClosed();
  }

  private isLoggedIn() {
    return this.userDataService.getLogin();
  }

  private isDoctor() {
    return this.userDataService.isDoctor() && this.isLoggedIn();
  }

  private getUser() {
    return this.userDataService.getUser();
  }
}
