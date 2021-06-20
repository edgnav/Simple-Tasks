import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthComponent } from './auth/auth.component';
import { AuthGuard } from './auth/auth.guard';
import { EventDetailComponent } from './events/event-detail/event-detail.component';
import { EventsResolverService } from './events/events-list-resolver.service';
import { EventsComponent } from './events/events.component';
import { EditEventGuard } from './events/new-event/edit-event.guard';
import { NewEventComponent } from './events/new-event/new-event.component';

const appRoutes: Routes = [
  { path: '', redirectTo: '/events', pathMatch: 'full' },

  {
    path: 'events',
    component: EventsComponent,
    resolve: [EventsResolverService],
    canActivate: [AuthGuard],
  },
  {
    path: 'newEvent',
    canActivate: [AuthGuard],
    component: NewEventComponent,
    resolve: [EventsResolverService],
  },
  {
    path: 'events/:id',
    canActivate: [AuthGuard],
    resolve: [EventsResolverService],
    component: EventDetailComponent,
  },
  {
    path: 'events/:id/edit',
    canActivate: [AuthGuard, EditEventGuard],
    component: NewEventComponent,
    resolve: [EventsResolverService],
  },

  { path: 'auth', component: AuthComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
