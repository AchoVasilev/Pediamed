import { CabinetService } from './../../../services/cabinet/cabinet.service';
import {
  CabinetResponse,
  ScheduleData,
} from './../../../models/events/schedule';
import { ScheduleDialogComponent } from './../../../reusableComponents/schedule-dialog/schedule-dialog.component';
import { EventData, EventDataInput } from '../../../models/events/schedule';
import { ScheduleService } from '../../../services/schedule/schedule.service';
import { MatDialog } from '@angular/material/dialog';
import { DateFormatter } from './../../../utils/dateFormatter';
import { CalendarDateFormatter } from 'angular-calendar';
import { UserModel } from './../../../services/data/user/userModel';
import { UserDataService } from './../../../services/data/user/user-data.service';
import { BreakpointObserver } from '@angular/cdk/layout';
import { AfterViewInit, ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CalendarEvent, CalendarView, DAYS_OF_WEEK } from 'angular-calendar';
import { map, Subject, takeUntil } from 'rxjs';
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

  view: CalendarView = CalendarView.Week;
  daysInWeek = 7;
  dayStartHour = 7;
  dayEndHour = 20;
  viewDate = new Date();
  locale: string = 'bg-BG';
  weekStartsOn: number = DAYS_OF_WEEK.MONDAY;
  events: CalendarEvent<{ event: ScheduleData }>[] = [];
  CalendarView = CalendarView;
  user: UserModel;
  cabinetName: string = '';
  cabinetScheduleId = '';
  eventData: EventData[] = [];
  cabinetResponse: CabinetResponse[] = [];

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

    this.getCabinets().subscribe((res) => console.log(res));
  }

  ngOnInit(): void {
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

    this.cabinetName = CabinetName[CabinetName.Плевен];
  }

  ngOnDestroy(): void {
    this.destroy$.next();
  }

  private set scheduleId(id : string) {
    this.cabinetScheduleId = id;
  }
  
  getCabinets() {
    return this.cabinetService.getCabinets().pipe(
      map((result) => {
        this.cabinetResponse = result;

        const merged: ScheduleData[] = [];
        result.forEach(res => {          
          merged.push(...res.cabinetSchedule?.scheduleAppointments, ...res.cabinetSchedule?.scheduleEvents)
        })

        this.events = [
          ...merged
        ].map((ev) => {
          return {
            start: ev?.startDate,
            title: ev?.title,
            end: ev?.endDate,
            id: ev?.id,
          };
        });
      })
    );
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  generateDayEvents(event: any) {
    this.cabinetScheduleId = this.cabinetResponse?.filter(x => x.name == this.cabinetName)[0]?.cabinetSchedule?.id;

    debugger;
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
          this.refetchEvents(this.scheduleId);
        }
      });
  }

  refetchEvents(id: string) {
    this.scheduleService.getSchedule(id);
  }
}
