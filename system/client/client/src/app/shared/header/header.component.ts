import { Component, OnInit } from '@angular/core';
import { faClinicMedical } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent{
  menu: HTMLElement | undefined;
  faClinic = faClinicMedical;

  constructor(private authService: AuthService) {
  }

  get isLoggedIn():boolean {
    return this.authService.isLoggedIn();
  }

  onHover() {
    this.menu = document.getElementById('menu')!;
    this.menu.style.display = 'block';
  }

  onMouseLeave() {
    this.menu = document.getElementById('menu')!;
    if (this.menu.style.display === 'block') {
      this.menu.style.display = 'none';
    }
  }

  onLogOut() {
    this.authService.logout();
  }
}