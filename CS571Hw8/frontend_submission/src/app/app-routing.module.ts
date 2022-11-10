import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { DetailCardsComponent } from './detail-cards/detail-cards.component';
import { BookingComponent } from './booking/booking.component';
import { SearchComponent } from './search/search.component';

const routes: Routes = [
  {path: "search", component: SearchComponent},
  {path: "booking", component: BookingComponent},
  {path: "", redirectTo: "/search", pathMatch:"full"}
]


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    RouterModule.forRoot(routes),
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
