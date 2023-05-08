import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { GalleryService } from 'src/app/services/gallery/gallery.service';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit {

  images$?: Observable<any>;
  pageNumber: number = 0;
  constructor(private galleryService: GalleryService) {}

  ngOnInit(): void {
    this.images$ = this.galleryService.getImages(this.pageNumber);
  }
}
