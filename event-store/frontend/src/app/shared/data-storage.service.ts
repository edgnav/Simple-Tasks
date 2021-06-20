import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs/operators';
import { EventListResponse } from '../events/model/event-list.model';
import { EventService } from '../events/event.service';
import { environment } from '../../environments/environment';

@Injectable({ providedIn: 'root' })
export class DataStorageService {
  constructor(private http: HttpClient, private eventService: EventService) {}

  searchBy(searchCriteria: string, page: number) {
    return this.http
      .get(
        environment.apiUrl +
          `/event/search?search=${searchCriteria}&page=${page}&size=10`,
        {}
      )
      .pipe(
        tap((events: EventListResponse) => {
          this.eventService.setEvents(events);
        })
      );
  }
}
