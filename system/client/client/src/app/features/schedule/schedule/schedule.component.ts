import { SchedulingDialogComponent } from '../../../reusable-components/scheduling-dialog/scheduling-dialog.component';
import { CabinetService } from './../../../services/cabinet/cabinet.service';
import { ScheduleData } from './../../../models/events/schedule';
import { ScheduleDialogComponent } from '../../../reusable-components/schedule-dialog/schedule-dialog.component';
import { EventData, EventDataInput } from '../../../models/events/schedule';
import { ScheduleService } from '../../../services/schedule/schedule.service';
import { MatDialog } from '@angular/material/dialog';
import { DateFormatter } from './../../../utils/dateFormatter';
import { CalendarDateFormatter } from 'angular-calendar';
import { BreakpointObserver } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CalendarEvent, CalendarView, DAYS_OF_WEEK } from 'angular-calendar';
import { map, Observable, Subject, takeUntil } from 'rxjs';
import { CabinetName } from 'src/app/models/enums/cabinetNameEnum';
import * as moment from 'moment';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Roles } from 'src/app/models/enums/roleEnum';
import { UserModel } from 'src/app/services/auth/authResult';
import { AppointmentCauseService } from 'src/app/services/appointment-cause/appointment-cause.service';
import { DoctorSchedulingDialogComponent } from 'src/app/reusable-components/doctor-scheduling-dialog/doctor-scheduling-dialog.component';
import { RegisteredUserSchedulingDialogComponent } from 'src/app/reusable-components/registered-user-scheduling-dialog/registered-user-scheduling-dialog.component';
import { LoadingService } from 'src/app/services/loading/loading.service';
import { Cabinet } from 'src/app/models/cabinet/cabinet';
import { colors } from '../colors/colors';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css'],
  providers: [
    {
      provide: CalendarDateFormatter,
      useClass: DateFormatter,
    },
  ],
})
export class ScheduleComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();
  private dateTimePattern = 'DD/MM/YYYY HH:mm';

  view: CalendarView = CalendarView.Week;
  daysInWeek = 7;
  dayStartHour = 7;
  dayEndHour = 20;
  viewDate = new Date();
  locale: string = 'bg-BG';
  weekStartsOn: number = DAYS_OF_WEEK.MONDAY;
  events$!: Observable<CalendarEvent<{ event: ScheduleData }>[]>;
  CalendarView = CalendarView;
  cabinet: Cabinet = { id: 2, name: CabinetName.Плевен };
  cabinetScheduleId?: string;
  eventData: EventData[] = [];
  excludedDays: number[] = [];
  isDoctor: boolean = false;
  appointmentCauses: AppointmentCauseResponse[] = [];
  currentUser?: UserModel;

  constructor(
    private breakpointObserver: BreakpointObserver,
    private cd: ChangeDetectorRef,
    private scheduleService: ScheduleService,
    private cabinetService: CabinetService,
    private authService: AuthService,
    private appointmentCauseService: AppointmentCauseService,
    private dialog: MatDialog,
    private loadingService: LoadingService
  ) {
    // could be cached
    this.scheduleService
      .getEventData()
      .subscribe((data) => (this.eventData = data));

    this.getAppointmentCauses();
    this.getUser();
  }

  ngOnInit(): void {
    this.onLoad();
    this.getCabinet(this.cabinet.id);

    const CALENDAR_RESPONSIVE = {
      small: {
        breakpoint: '(max-width: 576px)',
        daysInWeek: 1,
      },
      medium: {
        breakpoint: '(max-width: 768px)',
        daysInWeek: 3,
      },
      large: {
        breakpoint: '(max-width: 960px)',
        daysInWeek: 5,
      },
    };

    this.breakpointObserver
      .observe(
        Object.values(CALENDAR_RESPONSIVE).map(({ breakpoint }) => breakpoint)
      )
      .pipe(takeUntil(this.destroy$))
      .subscribe((state: any) => {
        const foundBreakpoint = Object.values(CALENDAR_RESPONSIVE).find(
          ({ breakpoint }) => !!state.breakpoints[breakpoint]
        );
        if (foundBreakpoint) {
          this.daysInWeek = foundBreakpoint.daysInWeek;
        } else {
          this.daysInWeek = 7;
        }
        this.cd.markForCheck();
      });
  }

  isLoggedIn() {
    return this.authService.isLoggedIn();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
  }

  setLoading(value: boolean) {
    this.loadingService.setLoading(value);
  }

  getCabinet(id: number) {
    this.events$ = this.cabinetService.getCabinet(id).pipe(
      map((result) => {
        const merged: ScheduleData[] = [
          ...result.cabinetSchedule.scheduleAppointments,
          ...result.cabinetSchedule.scheduleEvents,
        ];

        this.cabinetScheduleId = result.cabinetSchedule.id;

        this.calculateExcludedDays(result.workDays);

        return [...merged].map((ev) => {
          let startDate = moment(ev.startDate, this.dateTimePattern).toDate();
          let endDate = moment(ev.endDate, this.dateTimePattern).toDate();

          let title = '';
          let isAppointment = ev?.title.includes('Запазен час') ? true : false;
          if (!this.isDoctor) {
            title = ev?.title.includes('Запазен час') ? 'Запазен час' : 'Свободен час';
          }

          return {
            start: startDate,
            title: title,
            end: endDate,
            id: ev?.id,
            color: isAppointment ? colors.yellow : colors.blue
          };
        });
      })
    );
  }

  getUser() {
    if (this.isLoggedIn()) {
      this.authService.getUser().subscribe((u) => {
        this.currentUser = u;
        this.isDoctor = u.roles.some((r) => r === Roles.Doctor);
      });
    }
  }

  getAppointmentCauses() {
    this.appointmentCauseService
      .getAppointmentCauses()
      .subscribe((a) => (this.appointmentCauses = a));
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  calculateExcludedDays(workDays: number[]) {
    this.excludedDays = [0, 1, 2, 3, 4, 5, 6];
    this.excludedDays = this.excludedDays.filter((d) => !workDays.includes(d));
  }

  generateDayEvents(event: any) {
    if (!this.isLoggedIn() || !this.isDoctor) {
      this.isDoctor = false;
      return;
    }

    let date = event.day.date;
    if (moment(date).isBefore(Date.now(), 'day')) {
      return;
    }

    date = moment(date).format('DD/MM/yyyy');

    const eventDataInput: EventDataInput = {
      date,
      eventData: this.eventData,
      cabinetName: this.cabinet.name,
    };

    this.dialog
      .open(ScheduleDialogComponent, {
        data: eventDataInput,
      })
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          setTimeout(() => this.refetchEvents(this.cabinetScheduleId!), 1000);
        }
      });
  }

  refetchEvents(id: string) {
    this.events$ = this.scheduleService.getSchedule(id).pipe(
      map((result) => {
        const merged: ScheduleData[] = [
          ...result.scheduleAppointments,
          ...result.scheduleEvents,
        ];

        return [...merged].map((ev) => {
          let startDate = moment(ev.startDate, this.dateTimePattern).toDate();
          let endDate = moment(ev.endDate, this.dateTimePattern).toDate();

          let title = '';
          let isAppointment = ev?.title.includes('Запазен час') ? true : false;
          if (!this.isDoctor) {
            title = ev?.title.includes('Запазен час') ? 'Запазен час' : 'Свободен час';
          }

          return {
            start: startDate,
            title: ev?.title,
            end: endDate,
            id: ev?.id,
            color: isAppointment ? colors.yellow : colors.blue
          };
        });
      })
    );
  }

  eventClicked({ event }: { event: CalendarEvent }) {
    const startTime = moment(event.start).format(this.dateTimePattern);
    const endTime = moment(event.end).format(this.dateTimePattern);
    const dateTimeArgs = startTime.split(' ');

    if (this.isLoggedIn() && this.isDoctor) {
      this.dialog
        .open(DoctorSchedulingDialogComponent, {
          data: {
            event: event,
            appointmentCauses: this.appointmentCauses,
            startTime,
            endTime,
            dateTimeArgs,
            scheduleId: this.cabinetScheduleId,
          },
        })
        .afterClosed()
        .subscribe((res) => {
          if (res) {
            setTimeout(() => this.refetchEvents(this.cabinetScheduleId!), 500);
          }
        });
    } else if (this.isLoggedIn() && !this.isDoctor) {
      this.dialog
        .open(RegisteredUserSchedulingDialogComponent, {
          data: {
            event: event,
            user: this.currentUser,
            appointmentCauses: this.appointmentCauses,
            startTime,
            endTime,
            dateTimeArgs,
            scheduleId: this.cabinetScheduleId,
          },
        })
        .afterClosed()
        .subscribe((res) => {
          if (res) {
            setTimeout(() => this.refetchEvents(this.cabinetScheduleId!), 500);
          }
        });
    } else {
      this.dialog
        .open(SchedulingDialogComponent, {
          data: {
            event: event,
            appointmentCauses: this.appointmentCauses,
            startTime,
            endTime,
            dateTimeArgs,
            scheduleId: this.cabinetScheduleId,
          },
        })
        .afterClosed()
        .subscribe((res) => {
          if (res) {
            setTimeout(() => this.refetchEvents(this.cabinetScheduleId!), 500);
          }
        });
    }
  }

  onLoad() {
    document
      .querySelectorAll('a.nav-item.nav-link.active')
      .forEach((el) => el.classList.remove('active'));
    const calendarLink = document.getElementById('calendar');
    calendarLink?.classList.add('active');
  }
}
