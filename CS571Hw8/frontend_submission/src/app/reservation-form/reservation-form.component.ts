import { Component, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SearchService } from '../search.service';


@Component({
  selector: 'app-reservation-form',
  templateUrl: './reservation-form.component.html',
  styleUrls: ['./reservation-form.component.css']
})
export class ReservationFormComponent implements OnInit {
  @Input() name:any;
  @Output() success:boolean;

  reservationForm !:FormGroup;
  submitted = false;
  userEmail: any;
  userReserveDate: any;
  reserveHour: any;
  reserveMinute: any;
  displayDate: any;
  displayTime:any;
  succeess = true;
  searchService:SearchService;

  constructor(private formBuilder:FormBuilder,
    searchService : SearchService) { }

  ngOnInit(): void {
    this.reservationForm = this.formBuilder.group({
      userEmail : ['', [Validators.required, Validators.email]],
      userDate : ['', Validators.required],
      userHour : ['', Validators.required],
      userMinute : ['', Validators.required]
    })
  }

  onSubmit() {
    this.submitted = true;
    if (this.reservationForm.invalid) {
      return
    } else {
      this.userEmail = this.reservationForm.value.userEmail;
      this.userReserveDate = this.reservationForm.value.userDate;
      this.reserveHour = this.reservationForm.value.userHour;
      this.reserveMinute = this.reservationForm.value.userMinute;
      console.log(this.userEmail);
      console.log(this.userReserveDate);
      console.log(this.reserveHour);
      console.log(this.reserveMinute);
      window.alert("http:/localHost/4200 says Reservation created!");
      this.displayDate = this.userReserveDate.year + '-' + this.userReserveDate.month + '-' + this.userReserveDate.day;
      console.log(this.displayDate);
      this.displayTime = this.reserveHour + ':' + this.reserveMinute;
      this.saveReservation(this.name, this.displayDate, this.displayTime, this.userEmail);
      this.getAllReservation();
      console.log('successful saved in local memory')
    }  
  }

  saveReservation(BusinessName:any, date:any, time:any, email:any) {
    let reserveJson = {
      reserveName : BusinessName,
      reserveDate : date,
      reserveTime : time,
      reserveEmail : email,
    }
    if (!localStorage.getItem(BusinessName) && typeof BusinessName !== 'undefined') {
      localStorage.setItem(BusinessName, JSON.stringify(reserveJson));
    }
  }

  getAllReservation() {
    var archive = [];
    for (var i = 0; i < localStorage.length; i++) {
      archive[i] = JSON.parse(localStorage.getItem(localStorage.key(i)!)!);
      console.log(archive[i]);
    }
    return archive;
  }

  clearAllStorge() {
    localStorage.clear();
  }

}
