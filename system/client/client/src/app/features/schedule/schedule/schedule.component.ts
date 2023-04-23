import { SchedulingDialogComponent } from '../helper-components/scheduling-dialog/scheduling-dialog.component';
import { CabinetService } from './../../../services/cabinet/cabinet.service';
import { ScheduleData } from './../../../models/events/schedule';
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
import { AppointmentCauseResponse } from 'src/app/models/appointment-cause/appointmentCauseResponse';
import { AppointmentCauseService } from 'src/app/services/appointment-cause/appointment-cause.service';
import { LoadingService } from 'src/app/services/loading/loading.service';
import { Cabinet } from 'src/app/models/cabinet/cabinet';
import { colors } from '../colors/colors';
import { UserDataService } from 'src/app/services/data/user-data.service';
import { ScheduleDataService } from 'src/app/services/data/schedule-data.service';
import { format, isBefore, parse } from 'date-fns';
import { DoctorSchedulingDialogComponent } from '../helper-components/doctor-scheduling-dialog/doctor-scheduling-dialog.component';
import { RegisteredUserSchedulingDialogComponent } from '../helper-components/registered-user-scheduling-dialog/registered-user-scheduling-dialog.component';
import { ScheduleDialogComponent } from '../helper-components/schedule-dialog/schedule-dialog.component';
import { Constants } from 'src/app/utils/constants';

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
  private dateTimePattern = Constants.dateTimePattern;
  private datePattern = Constants.datePattern;

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
    private scheduleDataService: ScheduleDataService
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
    return this.userDataService.getLogin();
  }

  isDoctor() {
    return this.userDataService.isDoctor();
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
        this.scheduleDataService.setSchedule([...result.cabinetSchedule.scheduleAppointments, ...result.cabinetSchedule.scheduleEvents]);

        const merged: ScheduleData[] = [
          ...this.scheduleDataService.getSchedule(),
        ];

        this.cabinetScheduleId = result.cabinetSchedule.id;

        this.calculateExcludedDays(result.workDays);

        return [...merged].map((ev) => {
          return this.mapEvent(ev);
        });
      })
    );
  }

  private getTitle(ev: ScheduleData) {
    let title = '';
    if (!this.isDoctor()) {
      title = ev?.title?.includes('Запазен час')
        ? 'Запазен час'
        : 'Свободен час';
    } else {
      title = ev?.title;
    }
    
    return title;
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
    if (!this.isLoggedIn() || !this.isDoctor()) {
      return;
    }

    let date = event.day.date;
    if (isBefore(date, Date.now())) {
      return;
    }

    date = format(date, this.datePattern);

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
          this.refetchEvents();
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

  private mapEvent(ev: ScheduleData) {        
    let startDate = parse(ev.startDate, this.dateTimePattern, new Date());
    let endDate = parse(ev.endDate, this.dateTimePattern, new Date());

    let isAppointment = ev?.title?.includes('Запазен час') ? true : false;
    let title = this.getTitle(ev);

    return {
      start: startDate,
      title: title,
      end: endDate,
      id: ev?.id,
      color: isAppointment ? colors.yellow : colors.blue,
    };
  }

  eventClicked({ event }: { event: CalendarEvent }) {
    const startTime = format(event.start, this.dateTimePattern);
    const endTime = format(event.end!, this.dateTimePattern);
    const dateTimeArgs = startTime.split(' ');

    if (this.isLoggedIn() && this.isDoctor()) {
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
            this.refetchEvents();
          }
        });
    } else if (this.isLoggedIn() && !this.isDoctor) {
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
            setTimeout(() => this.refetchEvents(), 500);
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
            this.scheduleDataService.removeItem(event!.id!.toString());
            this.scheduleDataService.addItem(res.appointment);
            this.refetchEvents();
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
