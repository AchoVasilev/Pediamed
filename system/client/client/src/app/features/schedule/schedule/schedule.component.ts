import { SchedulingDialogComponent } from '../helper-components/scheduling-dialog/scheduling-dialog.component';
import { CabinetService } from './../../../services/cabinet/cabinet.service';
import {
  CabinetSchedule,
  MetaInfo,
  ScheduleData,
} from './../../../models/events/schedule';
import { EventData, EventDataInput } from '../../../models/events/schedule';
import { ScheduleService } from '../../../services/schedule/schedule.service';
import { MatDialog } from '@angular/material/dialog';
import { DateFormatter } from './../../../utils/dateFormatter';
import { CalendarDateFormatter } from 'angular-calendar';
import { BreakpointObserver } from '@angular/cdk/layout';
import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { CalendarEvent, CalendarView, DAYS_OF_WEEK } from 'angular-calendar';
import { map, Observable, Subject, Subscription, takeUntil, tap } from 'rxjs';
import { CabinetName } from 'src/app/models/enums/cabinetNameEnum';
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { AppointmentCauseService } from 'src/app/services/appointment-cause/appointment-cause.service';
import { LoadingService } from 'src/app/services/loading/loading.service';
import { Cabinet } from 'src/app/models/cabinet/cabinet';
import { colors } from '../colors/colors';
import { UserDataService } from 'src/app/services/data/user-data.service';
import { ScheduleDataService } from 'src/app/services/data/schedule-data.service';
import { format, isPast, parse } from 'date-fns';
import { DoctorSchedulingDialogComponent } from '../helper-components/doctor-scheduling-dialog/doctor-scheduling-dialog.component';
import { RegisteredUserSchedulingDialogComponent } from '../helper-components/registered-user-scheduling-dialog/registered-user-scheduling-dialog.component';
import { ScheduleDialogComponent } from '../helper-components/schedule-dialog/schedule-dialog.component';
import { Constants } from 'src/app/utils/constants';
import { EventSourceService } from 'src/app/services/event-source/event-source.service';
import { CalendarService } from 'src/app/services/calendar/calendar.service';
import { WebSocketService } from 'src/app/services/web-socket/web-socket.service';

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
export class ScheduleComponent implements OnInit, OnDestroy, AfterViewInit {
  private destroy$ = new Subject<void>();
  private dateTimePattern = Constants.dateTimePattern;
  private datePattern = Constants.datePattern;
  private subs$?: Subscription;

  refresh: Subject<void> = new Subject<void>();
  view: CalendarView = CalendarView.Week;
  daysInWeek = 7;
  dayStartHour = 7;
  dayEndHour = 20;
  viewDate = new Date();
  locale: string = 'bg-BG';
  weekStartsOn: number = DAYS_OF_WEEK.MONDAY;
  events$!: Observable<CalendarEvent<MetaInfo>[]>;
  CalendarView = CalendarView;
  cabinet: Cabinet = { id: 2, name: CabinetName.Плевен };
  cabinetScheduleId?: string;
  eventData: EventData[] = [];
  excludedDays: number[] = [];
  appointmentCauses: AppointmentCauseResponse[] = [];

  constructor(
    private breakpointObserver: BreakpointObserver,
    private cd: ChangeDetectorRef,
    private scheduleService: ScheduleService,
    private cabinetService: CabinetService,
    private appointmentCauseService: AppointmentCauseService,
    private dialog: MatDialog,
    private loadingService: LoadingService,
    private userDataService: UserDataService,
    private scheduleDataService: ScheduleDataService,
    private eventSourceService: EventSourceService,
    private calendarService: CalendarService,
    private webSocketService: WebSocketService
  ) {}

  ngAfterViewInit(): void {}

  ngOnInit(): void {
    this.getEventData();

    this.getAppointmentCauses();
    this.getUser();

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
    return this.userDataService.getLogin();
  }

  isDoctor() {
    return this.userDataService.isDoctor() && this.isLoggedIn();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.subs$;
    this.subs$?.unsubscribe();
  }

  setLoading(value: boolean) {
    this.loadingService.setLoading(value);
  }

  getCabinet(id: number) {
    this.events$ = this.webSocketService.connect('6bba2627-3f52-43f4-9c28-5ab3d7ce6151').pipe(
      map((schedule: CabinetSchedule) => {
        this.cabinetScheduleId = schedule.id;
        const merged = [...schedule.scheduleAppointments, ...schedule.scheduleEvents];
        this.scheduleDataService.setSchedule(merged);
        return [...merged].map((ev) => {
          return this.mapEvent(ev);
        });
      })
    )
    // this.events$ = this.cabinetService
    //   .getCabinet(id)
    //   .pipe(
    //     map((result) => {
    //       this.scheduleDataService.setSchedule([
    //         ...result.cabinetSchedule.scheduleAppointments,
    //         ...result.cabinetSchedule.scheduleEvents,
    //       ]);

    //       const merged: ScheduleData[] = [
    //         ...this.scheduleDataService.getSchedule(),
    //       ];

    //       this.cabinetScheduleId = result.cabinetSchedule.id;

    //       this.calculateExcludedDays(result.workDays);

    //       return [...merged].map((ev) => {
    //         return this.mapEvent(ev);
    //       });
    //     })
    //   ).pipe(
    //     tap(() => {
    //       this.events$ = this.webSocketService.connect('6bba2627-3f52-43f4-9c28-5ab3d7ce6151').pipe(
    //         takeUntil(this.destroy$),
    //         map((schedule: CabinetSchedule) => {
    //           console.log(schedule);
              
    //           const merged = [...schedule.scheduleAppointments, ...schedule.scheduleEvents];
    //           this.scheduleDataService.setSchedule(merged);
    //           return [...merged].map((ev) => {
    //             return this.mapEvent(ev);
    //           });
    //         })
    //       )
    //     })
    //   )
      // .pipe(
      //   tap(() => {
      //     this.events$ = this.eventSourceService
      //       .getCalendarSource$(this.cabinetScheduleId!)
      //       .pipe(
      //         map((events) => {
      //           const parsed: CabinetSchedule = JSON.parse(events);
                
      //           const merged = [
      //             ...parsed.scheduleAppointments,
      //             ...parsed.scheduleEvents,
      //           ];

      //           this.scheduleDataService.setSchedule(merged);
      //           return [...merged].map((ev) => {
      //             return this.mapEvent(ev);
      //           });
      //         })
      //       );
      //   })
      // );
  }

  // private getTitle(ev: ScheduleData) {
  //   let title = '';
  //   if (!this.isLoggedIn() || !this.isDoctor()) {
  //     title = ev?.title?.includes('Запазен час')
  //       ? 'Запазен час'
  //       : 'Свободен час';
  //   } else {
  //     title = ev?.title;
  //   }

  //   return title;
  // }

  private getEventData() {
    this.calendarService
      .getEventData()
      .subscribe((data) => (this.eventData = data));
  }

  refetch(payload: string) {
    this.webSocketService.send(payload);
  }

  getUser() {
    return this.userDataService.getUser();
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
    if (!this.isDoctor()) {
      return;
    }

    if (event.day.isPast) {
      return;
    }

    const date = format(event.day.date, this.datePattern);

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
          this.scheduleDataService.addMultiple(res.events);
          // this.refetchEvents();
          this.refetch(JSON.stringify(this.cabinetScheduleId));
        }
      });
  }

  refetchEvents() {
    this.events$ = this.scheduleDataService.getSchedule$().pipe(
      map((result) => {
        return [...result].map((ev) => {
          return this.mapEvent(ev);
        });
      })
    );
  }

  private mapEvent(ev: ScheduleData): CalendarEvent<MetaInfo> {
    let startDate = parse(ev.startDate, this.dateTimePattern, new Date());
    let endDate = parse(ev.endDate, this.dateTimePattern, new Date());

    let isAppointment = ev?.title?.includes('Запазен час') ? true : false;

    return {
      start: startDate,
      title: ev.title,
      end: endDate,
      id: ev?.id,
      color: isAppointment ? colors.yellow : colors.blue,
      meta: {
        isAppointment,
      },
    };
  }

  eventClicked({ event }: { event: CalendarEvent<MetaInfo> }) {
    if (
      (event?.meta?.isAppointment || isPast(event.start)) &&
      !this.isDoctor()
    ) {
      return;
    }

    const startTime = format(event.start, this.dateTimePattern);
    const endTime = format(event.end!, this.dateTimePattern);
    const dateTimeArgs = startTime.split(' ');

    if (this.isDoctor()) {
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
          }
        });
    } else if (this.isLoggedIn() && !this.isDoctor()) {
      this.dialog
        .open(RegisteredUserSchedulingDialogComponent, {
          data: {
            event: event,
            user: this.getUser(),
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
