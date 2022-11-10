import { Component, OnInit } from '@angular/core';
import { SearchService } from '../search.service';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {

  constructor(private searchService:SearchService) { }

  data: any;
  noReservation = false;
  existReservation = false;

  deleteItem() {
    window.alert("Reservation canceled!");
    console.log("are you sure that you want delete the reservation?")
    var key = (<HTMLElement>document.getElementById("businessName")).innerText;
    console.log(key);
    localStorage.removeItem(key);
    console.log(this.searchService.getAllReservation());
    this.data = this.searchService.getAllReservation();
    if (this.data.length <= 0) {
      this.noReservation = true;
      this.existReservation = false;
    } else {
      this.noReservation = false;
      this.existReservation = true;
      console.log('we have local data');
      console.log(this.data[0].reserveDate);
    }
  }

  ngOnInit(): void {
    this.data = this.searchService.getAllReservation();
    console.log(this.data);
    if (this.data.length <= 0) {
      this.noReservation = true;
      this.existReservation = false;
    } else {
      this.noReservation = false;
      this.existReservation = true;
      console.log('we have local data');
    }
  }

}
