import { Component } from '@angular/core';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent{

  constructor(private authService: AuthService) {
  }

  get isLoggedIn():boolean {
    return this.authService.isLoggedIn();
  }

  onLogOut() {
    this.authService.logout();
  }
}