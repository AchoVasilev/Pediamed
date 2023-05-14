import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { GalleryService } from 'src/app/services/gallery/gallery.service';
import { LoadingService } from 'src/app/services/loading/loading.service';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit {

  images$?: Observable<any>;
  pageNumber: number = 0;
  constructor(private galleryService: GalleryService, private loadingService: LoadingService) {}

  ngOnInit(): void {
    this.images$ = this.galleryService.getImages(this.pageNumber);
  }

  setLoading(value: boolean) {
    this.loadingService.setLoading(value);
  }
}
