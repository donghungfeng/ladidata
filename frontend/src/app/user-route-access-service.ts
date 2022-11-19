import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { LocalStorageService, SessionStorageService } from 'ngx-webstorage';
@Injectable({ providedIn: 'root' })
export class UserRouteAccessService implements CanActivate {
  constructor(private localStorage: LocalStorageService, private sessionStorage: SessionStorageService, private router: Router) {}

  canActivate(): boolean {
    // const authorities = route.data['authorities'];
    // We need to call the checkLogin / and so the accountService.identity() function, to ensure,
    // that the client has a principal too, if they already logged in by the server.
    // This could happen on a page refresh.
    const isAuthen = this.checkLogin();
    if (isAuthen === false) this.router.navigate(['/logiin']);
    return isAuthen;
  }

  checkLogin(): boolean {
    const token = this.localStorage.retrieve('authenticationToken')
    if (!token) return false;
    return true;
  }
}
