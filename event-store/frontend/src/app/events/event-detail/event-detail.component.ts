import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { EventService } from '../event.service';
import { GetEvent } from '../model/get-event.model';
import { User } from 'src/app/auth/user.model';

@Component({
  selector: 'app-event-detail',
  templateUrl: './event-detail.component.html',
  styleUrls: ['./event-detail.component.css'],
})
export class EventDetailComponent implements OnInit {
  id: number;
  isLoading = false;
  convertedEvent: {
    name: string;
    description: string;
    dateFrom: string;
    dateTo: string;
    userList: string[];
    tags: string[];
  } = {
    name: '',
    description: '',
    dateFrom: '',
    dateTo: '',
    userList: [],
    tags: [],
  };

  disabled: boolean = false;
  userInfo: {
    email: string;
    userId: number;
    iat: number;
    exp: number;
  };
  visibleEdit: boolean = false;
  participants: string[] = ['Jonas', 'Petras', 'Marytė'];
  constructor(
    private eventService: EventService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.id = +params['id'];
      this.userInfo = JSON.parse(localStorage.getItem('userData'));
      this.getEvent();
    });
  }

  onJoinToEvent() {
    this.eventService.joinEvent(this.id).subscribe({
      complete: () => this.getEvent(),
    });
  }

  getEvent() {
    this.isLoading = true;
    this.eventService.getEvent(this.id).subscribe((event: GetEvent) => {
      this.convertedEvent.dateTo = new Date(event.dateTo).toLocaleDateString();
      this.convertedEvent.dateFrom = new Date(
        event.dateFrom
      ).toLocaleDateString();
      this.convertedEvent.name = event.name;
      this.convertedEvent.description = event.description;
      this.convertedEvent.userList = event.userList;
      this.convertedEvent.tags = event.tags;
      const user: User = JSON.parse(localStorage.getItem('userData'));
      this.disabled = this.convertedEvent.userList.includes(user.email);
      this.visibleEdit = this.userInfo.email == event.creator;
      this.isLoading = false;
      this.eventService.loadedEvent.next(event);
    });
  }
  onEditEvent() {
    this.router.navigate(['edit'], { relativeTo: this.route });
  }
  onLeaveToEvent() {
    this.eventService.leaveEvent(this.id).subscribe({
      complete: () => this.getEvent(),
    });
  }
}
