import { NgModule } from '@angular/core';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { InputComponentsModule } from 'src/app/reusable-components/input-components/input-components.module';
import { ElementsModule } from 'src/app/reusable-components/elements/elements.module';
import { MaterialModule } from 'src/app/material/material/material.module';

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent
  ],
  imports: [
    AuthRoutingModule,
    InputComponentsModule,
    ElementsModule,
    MaterialModule
  ]
})
export class AuthModule { }
