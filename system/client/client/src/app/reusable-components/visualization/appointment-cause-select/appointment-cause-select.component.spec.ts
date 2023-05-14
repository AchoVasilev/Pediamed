import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppointmentCauseSelectComponent } from './appointment-cause-select.component';

describe('AppointmentCauseSelectComponent', () => {
  let component: AppointmentCauseSelectComponent;
  let fixture: ComponentFixture<AppointmentCauseSelectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppointmentCauseSelectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppointmentCauseSelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
