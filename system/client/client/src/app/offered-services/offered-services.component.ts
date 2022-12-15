import { Component, OnInit } from '@angular/core';
import { OfferedServiceView } from '../models/offeredServiceView';
import { OfferedServiceService } from '../services/offered-service/offered-service.service';

@Component({
  selector: 'app-offered-services',
  templateUrl: './offered-services.component.html',
  styleUrls: ['./offered-services.component.css']
})
export class OfferedServicesComponent implements OnInit {

  services: OfferedServiceView[] = [];
  constructor(private offeredServiceService: OfferedServiceService) { }

  ngOnInit(): void {
    this.offeredServiceService.getOfferedServices()
      .subscribe(data => {
        console.log(data);
        this.services = data;
      });

    this.carousel();
  }

  carousel() {
    //@ts-ignore
    $(".price-carousel").owlCarousel({
      autoplay: true,
      smartSpeed: 1000,
      margin: 45,
      dots: false,
      loop: true,
      nav: true,
      navText: [
        '<i class="bi bi-arrow-left"></i>',
        '<i class="bi bi-arrow-right"></i>'
      ],
      responsive: {
        0: {
          items: 1
        },
        992: {
          items: 2
        },
        1200: {
          items: 3
        }
      }
    });
  }
}
