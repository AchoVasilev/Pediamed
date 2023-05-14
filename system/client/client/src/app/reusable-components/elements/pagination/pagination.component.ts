import { Component, Input } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {

  @Input()
  length: number = 1;

  @Input()
  pageSize: number = 15;

  @Input()
  pageIndex: number = 0;

  pageEvent?: PageEvent;

  handlePageEvent(pageEvent: PageEvent) {
    this.pageEvent = pageEvent;
    this.length = pageEvent.length;
    this.pageSize = pageEvent.pageSize;
    this.pageIndex = pageEvent.pageIndex;
  }
}
