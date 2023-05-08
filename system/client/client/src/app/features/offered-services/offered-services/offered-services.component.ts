import { Component, OnInit } from '@angular/core';
import { OfferedServiceView } from '../../../models/offeredServiceView';
import { OfferedServiceService } from '../../../services/offered-service/offered-service.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-offered-services',
  templateUrl: './offered-services.component.html',
  styleUrls: ['./offered-services.component.css']
})
export class OfferedServicesComponent implements OnInit {

  services$?: Observable<OfferedServiceView[]>;
  
  constructor(private offeredServiceService: OfferedServiceService) { }

  ngOnInit(): void {
    this.services$ = this.offeredServiceService.getOfferedServices();
  }
}
