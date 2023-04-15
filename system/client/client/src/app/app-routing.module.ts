import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './shared/home/home.component';
import { PrivacyComponent } from './shared/privacy/privacy.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    pathMatch: 'full'
  },
  {
    path: 'privacy',
    component: PrivacyComponent,
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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
