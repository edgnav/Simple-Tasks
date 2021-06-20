import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  Resolve,
  RouterStateSnapshot,
} from '@angular/router';
import { DataStorageService } from '../shared/data-storage.service';
import { EventListResponse } from './model/event-list.model';
import { EventService } from './event.service';

@Injectable({ providedIn: 'root' })
export class EventsResolverService implements Resolve<EventListResponse> {
  constructor(
    private dataStorageService: DataStorageService,
    private eventService: EventService
  ) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    const events = this.eventService.getEvents();
    if (events.eventListDto.length === 0) {
      return this.dataStorageService.searchBy('', 1);
    } else {
      return events;
    }
  }
}
