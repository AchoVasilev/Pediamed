import { Component, OnInit } from '@angular/core';
import { OfferedServiceView } from '../../models/offeredServiceView';
import { OfferedServiceService } from '../../services/offered-service/offered-service.service';
import { OwlOptions } from 'ngx-owl-carousel-o';

@Component({
  selector: 'app-offered-services',
  templateUrl: './offered-services.component.html',
  styleUrls: ['./offered-services.component.css']
})
export class OfferedServicesComponent implements OnInit {

  services: OfferedServiceView[] = [];
  customOptions: OwlOptions = {
    loop: true,
    mouseDrag: false,
    touchDrag: false,
    pullDrag: false,
    dots: false,
    navSpeed: 700,
    navText: ['', ''],
    responsive: {
      0: {
        items: 1
      },
      400: {
        items: 2
      },
      740: {
        items: 3
      },
      940: {
        items: 4
      }
    },
    nav: true
  }
  constructor(private offeredServiceService: OfferedServiceService) { }

  ngOnInit(): void {
    this.offeredServiceService.getOfferedServices()
      .subscribe(data => {
        this.services = data;
      });

    this.carousel();
  }

  carousel() {

  }
}
