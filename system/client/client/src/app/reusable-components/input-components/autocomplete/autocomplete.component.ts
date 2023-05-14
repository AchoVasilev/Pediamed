import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Observable, mergeMap } from 'rxjs';
import { PatientView } from 'src/app/models/user/patient';
import { PatientService } from 'src/app/services/patient/patient.service';

@Component({
  selector: 'app-autocomplete',
  templateUrl: './autocomplete.component.html',
  styleUrls: ['./autocomplete.component.css']
})
export class AutocompleteComponent implements OnInit {
  control: FormControl = new FormControl(null);

  @Output() 
  patientSelectedEvent = new EventEmitter<PatientView>();

  patients$?: Observable<PatientView[]>;
  constructor(private patientService: PatientService) {}

  ngOnInit(): void {
    this.patients$ = this.control.valueChanges.pipe(
      mergeMap((value) => this.patientService.searchPatient(value))
    )
  }

  displayFn(patient: PatientView): string {
    return patient && patient.firstName && patient.lastName ? `${patient.firstName} ${patient.lastName}` : '';
  }

  onSelected(patient: PatientView) {
    this.patientSelectedEvent.emit(patient);
  }
}
