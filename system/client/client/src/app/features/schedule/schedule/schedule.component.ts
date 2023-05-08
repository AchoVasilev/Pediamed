import { MetaInfo, ScheduleData } from './../../../models/events/schedule';
import { EventData, EventDataInput } from '../../../models/events/schedule';
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
import { format, isPast, parse } from 'date-fns';
import { Constants } from 'src/app/utils/constants';
import { CalendarService } from 'src/app/services/calendar/calendar.service';
import { WebSocketService } from 'src/app/services/web-socket/web-socket.service';
import { ScheduleDialogService } from 'src/app/services/schedule/schedule-dialog/schedule-dialog.service';

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
    private appointmentCauseService: AppointmentCauseService,
    private loadingService: LoadingService,
    private userDataService: UserDataService,
    private scheduleDataService: ScheduleDataService,
    private calendarService: CalendarService,
    private webSocketService: WebSocketService,
    private scheduleDialogService: ScheduleDialogService,
  ) {}

  ngOnInit(): void {
    this.getEventData();

    this.getAppointmentCauses();

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

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  isLoggedIn() {
    return this.userDataService.getLogin();
  }

  isDoctor() {
    return this.userDataService.isDoctor() && this.isLoggedIn();
  }

  setLoading(value: boolean) {
    this.loadingService.setLoading(value);
  }

  getCabinet(id: number) {
    this.events$ = this.webSocketService.connect(id.toString()).pipe(
      map((result) => {
        this.scheduleDataService.setSchedule([
          ...result.cabinetSchedule.scheduleAppointments,
          ...result.cabinetSchedule.scheduleEvents,
        ]);

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
    if (!this.isLoggedIn() || !this.isDoctor()) {
      title = ev?.title?.includes('Запазен час')
        ? 'Запазен час'
        : 'Свободен час';
    } else {
      title = ev?.title;
    }

    return title;
  }

  private getEventData() {
    this.calendarService
      .getEventData()
      .subscribe((data) => (this.eventData = data));
  }

  refetchEvents(payload: string) {
    this.webSocketService.send(payload);
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

  generateEmptySlots(event: any) {
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

    this.scheduleDialogService
      .openEventDataDialog(eventDataInput)
      .subscribe((res) => {
        if (res) {
          this.refetchEvents(this.cabinet.id.toString());
        }
      });
  }

  private mapEvent(ev: ScheduleData): CalendarEvent<MetaInfo> {
    let startDate = parse(ev.startDate, this.dateTimePattern, new Date());
    let endDate = parse(ev.endDate, this.dateTimePattern, new Date());

    let isAppointment = ev?.title?.includes('Запазен час') ? true : false;

    return {
      start: startDate,
      title: this.getTitle(ev),
      end: endDate,
      id: ev?.id,
      color: isAppointment ? colors.yellow : colors.blue,
      meta: {
        isAppointment,
      },
    };
  }

  eventClicked({ event }: { event: CalendarEvent<MetaInfo> }) {
    if ((event?.meta?.isAppointment || isPast(event.start)) && !this.isDoctor()) {
      return;
    }

    this.scheduleDialogService
      .openSchedulingDialog(
        event,
        this.cabinetScheduleId!,
        this.appointmentCauses
      )
      .subscribe((res) => {
        if (res) {
          this.refetchEvents(this.cabinet.id.toString());
        }
      });
  }
}
