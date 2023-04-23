import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DateTimeEventDataComponent } from './date-time-event-data.component';

describe('DateTimeEventDataComponent', () => {
  let component: DateTimeEventDataComponent;
  let fixture: ComponentFixture<DateTimeEventDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DateTimeEventDataComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DateTimeEventDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
