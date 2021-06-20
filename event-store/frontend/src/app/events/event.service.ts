import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';
import { EventListResponse } from './model/event-list.model';
import { environment } from '../../environments/environment';
import { Event } from './model/event.model';
import { GetEvent } from './model/get-event.model';

@Injectable()
export class EventService {
  private eventListResponse: EventListResponse = {
    eventListDto: [],
    numberOfPages: 0,
  };
  eventsChanged = new BehaviorSubject<EventListResponse>(null);
  loadedEvent = new BehaviorSubject<GetEvent>(null);
  constructor(private http: HttpClient) {}

  getEvents() {
    return this.eventListResponse;
  }

  setEvents(events: EventListResponse) {
    this.eventListResponse = events;
    this.eventsChanged.next(this.eventListResponse);
  }

  getEvent(index: number) {
    return this.http.get<Event>(environment.apiUrl + `/event/${index}`);
  }

  addEvent(event: Event) {
    event.dateFrom = new Date(event.dateFrom);
    event.dateTo = new Date(event.dateTo);
    var tags: string[] = [];
    event.tags.map((tag) => tags.push(tag.tag));
    return this.http.post<EventListResponse>(environment.apiUrl + '/event', {
      name: event.name,
      description: event.description,
      dateFrom: event.dateFrom,
      dateTo: event.dateTo,
      tags: tags,
    });
  }

  joinEvent(eventId: number) {
    return this.http.post(environment.apiUrl + `/event/join/${eventId}`, {});
  }

  updateEvent(event: Event, eventId: number) {
    event.dateFrom = new Date(event.dateFrom);
    event.dateTo = new Date(event.dateTo);
    var tags: string[] = [];
    event.tags.map((tag) => tags.push(tag.tag));
    return this.http.post<EventListResponse>(
      environment.apiUrl + `/event/${eventId}`,
      {
        name: event.name,
        description: event.description,
        dateFrom: event.dateFrom,
        dateTo: event.dateTo,
        tags: tags,
      }
    );
  }

  deleteEvent(eventId: number) {
    return this.http.delete(environment.apiUrl + `/event/join/${eventId}`, {});
  }

  leaveEvent(eventId: number) {
    return this.http.post(environment.apiUrl + `/event/leave/${eventId}`, {});
  }
}
