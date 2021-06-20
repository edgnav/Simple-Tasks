import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { User } from './user.model';
import { tap } from 'rxjs/operators';
import jwt_decode from 'jwt-decode';
import { environment } from '../../environments/environment';

interface parsedToken {
  sub: string;
  userId: number;
  iat: number;
  exp: number;
}
@Injectable({ providedIn: 'root' })
export class AuthService {
  user = new BehaviorSubject<User>(null);
  private tokenExpirationTimer: any;
  constructor(private http: HttpClient, private route: Router) {}

  login(email: string, password: string) {
    return this.http
      .post<any>(environment.apiUrl + '/authenticate', {
        email: email,
        password: password,
      })
      .pipe(
        tap((resData) => {
          let parsedToken: parsedToken = this.getDecodedAccessToken(
            resData.jwt
          );
          this.handleAuth(
            parsedToken.sub,
            parsedToken.userId,
            resData.jwt,
            parsedToken.exp
          );
        })
      );
  }

  signup(email: string, password: string) {
    return this.http
      .post<any>(environment.apiUrl + '/signup', {
        email: email,
        password: password,
      })
      .pipe(
        tap((resData) => {
          let parsedToken: parsedToken = this.getDecodedAccessToken(
            resData.jwt
          );
          this.handleAuth(
            parsedToken.sub,
            parsedToken.userId,
            resData.jwt,
            parsedToken.exp
          );
        })
      );
  }

  private handleAuth(
    email: string,
    userId: number,
    token: string,
    expiresIn: number
  ) {
    const expirationDate = new Date(expiresIn * 1000);
    const user = new User(email, userId, token, expirationDate);
    this.user.next(user);
    localStorage.setItem('userData', JSON.stringify(user));
  }

  logout() {
    this.user.next(null);
    this.route.navigate(['/auth']);
    localStorage.removeItem('userData');
    if (this.tokenExpirationTimer) {
      clearTimeout(this.tokenExpirationTimer);
    }
    this.tokenExpirationTimer = null;
  }

  autoLogin() {
    const userData: {
      email: string;
      id: number;
      _token: string;
      _tokenExpirationDate: string;
    } = JSON.parse(localStorage.getItem('userData'));
    if (!userData) {
      return;
    }

    const loadedUser = new User(
      userData.email,
      userData.id,
      userData._token,
      new Date(userData._tokenExpirationDate)
    );

    if (loadedUser.token) {
      this.user.next(loadedUser);
      const expirationDuration =
        new Date(userData._tokenExpirationDate).getTime() -
        new Date().getTime();
      this.autoLogout(expirationDuration);
    }
  }
  autoLogout(expirationDuration: number) {
    this.tokenExpirationTimer = setTimeout(() => {
      this.logout();
    }, expirationDuration);
  }
  getDecodedAccessToken(token: string): parsedToken {
    try {
      return jwt_decode(token);
    } catch (Error) {
      console.log(Error);
      return null;
    }
  }
}
