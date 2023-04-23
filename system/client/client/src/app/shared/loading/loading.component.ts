import { Component } from '@angular/core';
import { LoadingService } from 'src/app/services/loading/loading.service';

@Component({
  selector: 'app-loading',
  templateUrl: './loading.component.html',
  styleUrls: ['./loading.component.css']
})
export class LoadingComponent {
  constructor(private loadingService: LoadingService) {
  }

  isLoading() {
    return this.loadingService.getLoading();
  }
}
