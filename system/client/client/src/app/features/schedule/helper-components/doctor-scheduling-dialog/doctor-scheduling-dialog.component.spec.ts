import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoctorSchedulingDialogComponent } from './doctor-scheduling-dialog.component';

describe('DoctorSchedulingDialogComponent', () => {
  let component: DoctorSchedulingDialogComponent;
  let fixture: ComponentFixture<DoctorSchedulingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DoctorSchedulingDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DoctorSchedulingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
