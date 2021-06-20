import { Component, OnInit } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { EventService } from '../event.service';
import { GetEvent } from '../model/get-event.model';

@Component({
  selector: 'app-new-event',
  templateUrl: './new-event.component.html',
  styleUrls: ['./new-event.component.css'],
})
export class NewEventComponent implements OnInit {
  id: number;
  event: GetEvent;

  constructor(
    private eventService: EventService,
    private router: Router,
    private route: ActivatedRoute
  ) {}
  editMode = false;
  eventForm!: FormGroup;
  defaultDate: Date = new Date();
  disabled = false;
  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.editMode = params['id'] != null;
      this.id = +params['id'];
      this.editMode ? this.getEvent() : this.initForm(this.event);
    });
  }

  getEvent() {
    this.eventService.getEvent(this.id).subscribe((event: GetEvent) => {
      this.initForm(event);
    });
  }

  private initForm(event: GetEvent) {
    let eventName = '';
    let eventDescription = '';
    let eventDateFrom = new Date();
    let eventDateTo = new Date();
    let eventTags = new FormArray([]);
    if (this.editMode) {
      eventName = event.name;
      eventDescription = event.description;
      eventDateFrom = new Date(event.dateFrom);
      eventDateTo = new Date(event.dateTo);
      if (event.tags) {
        for (let tag of event.tags) {
          eventTags.push(
            new FormGroup({
              tag: new FormControl(tag, Validators.required),
            })
          );
        }
      }
    }

    this.eventForm = new FormGroup({
      name: new FormControl(eventName, Validators.required),
      description: new FormControl(eventDescription, Validators.required),
      dateFrom: new FormControl(eventDateFrom, Validators.required),
      dateTo: new FormControl(eventDateTo, [Validators.required]),
      tags: eventTags,
    });
  }

  get tags() {
    return (<FormArray>this.eventForm.get('tags')).controls;
  }

  onSubmit() {
    if (!this.editMode && this.eventForm.valid) {
      this.eventService.addEvent(this.eventForm.value).subscribe({
        complete: () => {
          this.router.navigate(['/events']);
        },
      });
    } else if (this.editMode && this.eventForm.valid) {
      this.eventService.updateEvent(this.eventForm.value, this.id).subscribe({
        complete: () => {
          this.router.navigate(['/events']);
        },
      });
    } else {
      this.eventForm.markAllAsTouched();
    }
  }

  onAddTag() {
    (<FormArray>this.eventForm.get('tags')).push(
      new FormGroup({
        tag: new FormControl(null, Validators.required),
      })
    );
  }
  onDeleteTag(index: number) {
    (<FormArray>this.eventForm.get('tags')).removeAt(index);
  }
  onCancel() {
    this.router.navigate(['../'], { relativeTo: this.route });
  }
  get name() {
    return this.eventForm.get('name');
  }
  get description() {
    return this.eventForm.get('description');
  }
  get dateFrom() {
    return this.eventForm.get('dateFrom');
  }
  get dateTo() {
    return this.eventForm.get('dateTo');
  }
}
