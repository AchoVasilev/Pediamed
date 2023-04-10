import { SchedulingDialogComponent } from '../../../reusable-components/scheduling-dialog/scheduling-dialog.component';
import { AppointmentCause } from './../../../models/events/appointment';
import { CabinetService } from './../../../services/cabinet/cabinet.service';
import {
  CabinetResponse,
  ScheduleData,
} from './../../../models/events/schedule';
import { ScheduleDialogComponent } from '../../../reusable-components/schedule-dialog/schedule-dialog.component';
import { EventData, EventDataInput } from '../../../models/events/schedule';
import { ScheduleService } from '../../../services/schedule/schedule.service';
import { MatDialog } from '@angular/material/dialog';
import { DateFormatter } from './../../../utils/dateFormatter';
import { CalendarDateFormatter } from 'angular-calendar';
import { UserModel } from './../../../services/data/user/userModel';
import { UserDataService } from './../../../services/data/user/user-data.service';
import { BreakpointObserver } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CalendarEvent, CalendarView, DAYS_OF_WEEK } from 'angular-calendar';
import { map, Observable, Subject, takeUntil } from 'rxjs';
import { CabinetName } from 'src/app/models/enums/cabinetNameEnum';
import * as moment from 'moment';

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
  user: UserModel;
  cabinetName: string = CabinetName[CabinetName.Плевен];
  cabinetScheduleId: string | undefined;
  eventData: EventData[] = [];
  cabinetResponse?: CabinetResponse;
  appointmentCauses: AppointmentCause[] = [];
  excludedDays: number[] = [];

  constructor(
    private breakpointObserver: BreakpointObserver,
    private cd: ChangeDetectorRef,
    private userDataService: UserDataService,
    private scheduleService: ScheduleService,
    private cabinetService: CabinetService,
    private dialog: MatDialog
  ) {
    this.user = this.userDataService.getUser();

    // could be cached
    this.scheduleService
      .getEventData()
      .subscribe((data) => (this.eventData = data));
  }

  ngOnInit(): void {
    this.getCabinet();

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

  ngOnDestroy(): void {
    this.destroy$.next();
  }

  getCabinet() {
    this.events$ = this.cabinetService.getCabinet(this.cabinetName).pipe(
      map((result) => {
        this.cabinetResponse = result;

        const merged: ScheduleData[] = [
          ...this.cabinetResponse.cabinetSchedule.scheduleAppointments,
          ...this.cabinetResponse.cabinetSchedule.scheduleEvents,
        ];

        this.cabinetScheduleId = this.cabinetResponse.cabinetSchedule.id;

        this.calculateExcludedDays(result.workDays);
        return [...merged].map((ev) => {
          let startDate = moment(ev.startDate, this.dateTimePattern).toDate();
          let endDate = moment(ev.endDate, this.dateTimePattern).toDate();

          return {
            start: startDate,
            title: ev?.title,
            end: endDate,
            id: ev?.id,
          };          
        });
      })
    );
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  calculateExcludedDays(workDays: number[]) {
    this.excludedDays = [0, 1, 2, 3, 4, 5, 6];
    this.excludedDays = this.excludedDays.filter(d => !workDays.includes(d));
  }

  generateDayEvents(event: any) {
    let date = event.day.date;
    if (moment(date).isBefore(Date.now(), 'day')) {
      return;
    }

    date = moment(date).format('DD/MM/yyyy');

    const eventDataInput: EventDataInput = {
      date,
      eventData: this.eventData,
      cabinetName: this.cabinetName,
    };

    this.dialog
      .open(ScheduleDialogComponent, {
        data: eventDataInput,
      })
      .afterClosed()
      .subscribe((res) => {
        if (res) {
          setTimeout(() => this.refetchEvents(this.cabinetScheduleId!), 500)
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

        console.log(result);
        console.log(merged);
        
        return [...merged].map((ev) => {
          let startDate = moment(ev.startDate, this.dateTimePattern).toDate();
          let endDate = moment(ev.endDate, this.dateTimePattern).toDate();

          return {
            start: startDate,
            title: ev?.title,
            end: endDate,
            id: ev?.id,
          };
        });
      })
    );
  }

  eventClicked({event}: {event:CalendarEvent}) {
    this.dialog.open(SchedulingDialogComponent, {
      data: event
    });
  }
}
