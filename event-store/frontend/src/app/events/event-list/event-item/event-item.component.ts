import { Component, Input, OnInit } from '@angular/core';
import { EventListModel } from '../../model/event-list.model';

@Component({
  selector: 'app-event-item',
  templateUrl: './event-item.component.html',
  styleUrls: ['./event-item.component.css'],
})
export class EventItemComponent implements OnInit {
  @Input() event: EventListModel;
  @Input() index: number;

  constructor() {}

  ngOnInit(): void {}
}
