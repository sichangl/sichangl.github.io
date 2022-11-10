import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { SearchComponent } from './search/search.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { YelpResultComponent } from './yelp-result/yelp-result.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AutocompleteComponent } from './autocomplete/autocomplete.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import {NgbPaginationModule, NgbAlertModule} from '@ng-bootstrap/ng-bootstrap';
import { MatTabsModule } from '@angular/material/tabs';
import { DetailCardsComponent } from './detail-cards/detail-cards.component';
import { AppRoutingModule } from './app-routing.module'; 
import { GoogleMapsModule } from '@angular/google-maps';
import { BookingComponent } from './booking/booking.component';
import { ReservationFormComponent } from './reservation-form/reservation-form.component';




@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    TopBarComponent,
    YelpResultComponent,
    AutocompleteComponent,
    DetailCardsComponent,
    BookingComponent,
    ReservationFormComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatAutocompleteModule,
    NgbModule,
    NgbPaginationModule, 
    NgbAlertModule,
    MatTabsModule,
    AppRoutingModule,
    GoogleMapsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule { }
