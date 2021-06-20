import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';

import { AppComponent } from './app.component';
import { AuthComponent } from './auth/auth.component';
import { HeaderComponent } from './header/header.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { EventsComponent } from './events/events.component';
import { EventItemComponent } from './events/event-list/event-item/event-item.component';
import { EventListComponent } from './events/event-list/event-list.component';
import { EventService } from './events/event.service';
import { AuthInterceptorService } from './auth/auth-interceptor.service';
import { EventDetailComponent } from './events/event-detail/event-detail.component';
import { NewEventComponent } from './events/new-event/new-event.component';
import { LoadingSpinnerComponent } from './shared/loading-spinner/loading-spinner.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    HeaderComponent,
    EventsComponent,
    EventItemComponent,
    EventListComponent,
    EventDetailComponent,
    NewEventComponent,
    LoadingSpinnerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatTableModule,
    MatPaginatorModule,
  ],
  providers: [
    EventService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
