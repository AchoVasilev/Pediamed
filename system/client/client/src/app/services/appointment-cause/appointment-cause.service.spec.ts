import { TestBed } from '@angular/core/testing';

import { AppointmentCauseService } from './appointment-cause.service';

describe('AppointmentCauseService', () => {
  let service: AppointmentCauseService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AppointmentCauseService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
