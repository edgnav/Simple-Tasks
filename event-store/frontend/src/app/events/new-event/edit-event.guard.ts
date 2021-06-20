import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable, Subject } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { EventService } from '../event.service';
import { GetEvent } from '../model/get-event.model';

@Injectable({ providedIn: 'root' })
export class EditEventGuard implements CanActivate {
  constructor(
    private eventService: EventService,
    private router: Router,
    private authService: AuthService
  ) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
    let subject = new Subject<boolean | UrlTree>();
    this.eventService
      .getEvent(route.params['id'])
      .subscribe((event: GetEvent) => {
        if (event.creator !== this.authService.user.value.email) {
          subject.next(this.router.createUrlTree(['/events']));
        } else {
          subject.next(true);
        }
      });
    return subject;
  }
}
