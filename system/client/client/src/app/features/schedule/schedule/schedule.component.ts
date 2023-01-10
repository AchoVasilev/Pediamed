import { BreakpointObserver } from '@angular/cdk/layout';
import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CalendarView } from 'angular-calendar';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit, OnDestroy{
  private destroy$ = new Subject<void>();

  view: CalendarView = CalendarView.Week;
  daysInWeek = 7;
  viewDate = new Date();
  
  constructor(private breakpointObserver: BreakpointObserver,
    private cd: ChangeDetectorRef) {}

  ngOnInit(): void {
    const CALENDAR_RESPONSIVE = {
      small: {
        breakpoint: '(max-width: 576px)',
        daysInWeek: 2,
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

}
