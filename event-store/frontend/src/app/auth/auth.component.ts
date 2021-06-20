import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
})
export class AuthComponent implements OnInit {
  isLoading = false;
  constructor(private authService: AuthService, private router: Router) {}
  isLoginMode = true;
  ngOnInit(): void {}
  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
  }
  onSubmit(authForm: NgForm) {
    let authObs: Observable<any>;
    if (!authForm.valid) {
      return;
    }
    this.isLoading = true;

    const email = authForm.value.email;
    const password = authForm.value.password;

    if (this.isLoginMode) {
      authObs = this.authService.login(email, password);
    } else {
      authObs = this.authService.signup(email, password);
    }

    authObs.subscribe(
      (resData) => {
        this.isLoading = false;
        this.router.navigate(['/events']);
      },
      (errorMessage) => {
        this.isLoading = false;
      }
    );
    authForm.reset();
  }
}
