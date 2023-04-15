import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OfferedServicesComponent } from './offered-services/offered-services.component';

const routes: Routes = [
  {
    path: '',
    component: OfferedServicesComponent,
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OfferedServicesRoutingModule { }
