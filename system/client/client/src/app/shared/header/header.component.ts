import { Component, ElementRef, ViewChild } from '@angular/core';
import { faClinicMedical } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent{
  @ViewChild('#menu')
  menu: ElementRef | undefined;
  faClinic = faClinicMedical;

  constructor(private authService: AuthService) {
  }

  get isLoggedIn():boolean {
    return this.authService.isLoggedIn();
  }

  onClick(event: any) {
    document.querySelectorAll('a.nav-item.nav-link.active').forEach(el => el.classList.remove('active'));
    event.target.classList.add('active');
  }

  onLogOut() {
    this.authService.logout();
  }
}