import { Component } from '@angular/core';
import { faClinicMedical } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})

export class HeaderComponent {
  menu: HTMLElement | undefined;
  faClinic = faClinicMedical;

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
}