import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

  constructor() { }

  clickBooking() {
    (<HTMLLinkElement>document.getElementById("booking")).setAttribute('border', 'none');
    (<HTMLLinkElement>document.getElementById("search")).setAttribute('border', 'none');
  }

  clickSearch() {

  }

  ngOnInit(): void {
  }

}
