import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { DataStorageService } from '../shared/data-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit, OnDestroy {
  collapsed = true;
  private userSub: Subscription;
  isAuthenticated = false;
  searchForm: FormGroup;
  timeout: any = null;

  constructor(
    private authService: AuthService,
    private dsStorage: DataStorageService
  ) {}

  ngOnInit() {
    this.userSub = this.authService.user.subscribe((user) => {
      this.isAuthenticated = !!user;
    });
    this.initForm();
  }

  private initForm() {
    let searchValue = '';

    this.searchForm = new FormGroup({
      searchValue: new FormControl(searchValue, Validators.required),
    });
  }

  onLogout() {
    this.authService.logout();
  }

  ngOnDestroy() {
    this.userSub.unsubscribe();
  }

  onKeySearch(event: any) {
    clearTimeout(this.timeout);
    var $this = this;
    this.timeout = setTimeout(function () {
      $this.dsStorage.searchBy(event.target.value, 0).subscribe();
    }, 500);
  }

  onHome() {
    this.dsStorage.searchBy('', 0).subscribe();
  }
}
