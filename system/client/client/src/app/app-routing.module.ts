import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './shared/home/home.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'services', loadChildren: () => import('./features/offered-services/offered-services.module').then(m => m.OfferedServicesModule)
  },
  {
    path: 'auth', loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'schedule', loadChildren: () => import('./features/schedule/schedule.module').then(m => m.ScheduleModule)
  },
  {
    path: 'privacy', loadChildren: () => import('./features/privacy/privacy.module').then(m => m.PrivacyModule)
  },
  {
    path: 'gallery', loadChildren: () => import('./features/gallery/gallery.module').then(m => m.GalleryModule)
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
