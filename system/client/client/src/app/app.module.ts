import { ScheduleModule } from './features/schedule/schedule.module';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { HomeComponent } from './shared/home/home.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthModule } from './features/auth/auth.module';
import { OfferedServicesModule } from './features/offered-services/offered-services.module';
import { PrivacyComponent } from './shared/privacy/privacy.component';
import { AuthInterceptorProviders } from './services/auth/auth.interceptor';
import { AuthService } from './services/auth/auth.service';
import { OfferedServiceService } from './services/offered-service/offered-service.service';
import { CalendarModule } from 'angular-calendar';
import { DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { registerLocaleData } from '@angular/common';
import localeBg from '@angular/common/locales/bg';
import { ReusableComponentsModule } from './reusable-components/reusable-components.module';
import { LoadingComponent } from './shared/loading/loading.component';
import { MaterialModule } from './material/material/material.module';
registerLocaleData(localeBg);

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    PrivacyComponent,
    LoadingComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    RouterModule,
    AuthModule,
    OfferedServicesModule,
    ScheduleModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory,
    }),
    ReusableComponentsModule,
    MaterialModule
  ],
  providers: [AuthService, OfferedServiceService, AuthInterceptorProviders],
  bootstrap: [AppComponent],
})
export class AppModule {}
