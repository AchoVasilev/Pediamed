import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedulingDialogComponent } from './scheduling-dialog.component';

describe('SchedulingDialogComponent', () => {
  let component: SchedulingDialogComponent;
  let fixture: ComponentFixture<SchedulingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchedulingDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchedulingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
