import { Component, OnInit } from '@angular/core';
import { DataStorageService } from 'src/app/shared/data-storage.service';
import { Subscription } from 'rxjs';
import { EventService } from '../event.service';
import { EventListResponse } from '../model/event-list.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
})
export class EventListComponent implements OnInit {
  eventListResponse: EventListResponse;
  subscription: Subscription;
  isLoading = false;

  pages: Array<number> = [];
  page: number;
  constructor(
    private dataStorageService: DataStorageService,
    private eventService: EventService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getEvents(0);
    this.eventService.eventsChanged.subscribe(
      (eventListResponse: EventListResponse) => {
        this.eventListResponse = eventListResponse;
        this.pages = Array.from(
          Array(this.eventListResponse.numberOfPages).keys()
        );
      }
    );
  }

  getEvents(page: number) {
    this.isLoading = true;
    this.dataStorageService.searchBy('', page).subscribe({
      complete: () => {
        this.eventListResponse = this.eventService.getEvents();
        this.isLoading = false;
        this.pages = Array.from(
          Array(this.eventListResponse.numberOfPages).keys()
        );
      },
    });
  }

  onCreateEvent() {
    this.router.navigate(['/newEvent'], { relativeTo: this.route });
  }
  setPage(i, event: any) {
    event.preventDefault();
    this.page = i;
    this.getEvents(this.page);
  }
}
