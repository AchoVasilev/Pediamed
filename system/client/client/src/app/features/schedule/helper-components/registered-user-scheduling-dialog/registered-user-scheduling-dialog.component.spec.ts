import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisteredUserSchedulingDialogComponent } from './registered-user-scheduling-dialog.component';

describe('RegisteredUserSchedulingDialogComponent', () => {
  let component: RegisteredUserSchedulingDialogComponent;
  let fixture: ComponentFixture<RegisteredUserSchedulingDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisteredUserSchedulingDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisteredUserSchedulingDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
