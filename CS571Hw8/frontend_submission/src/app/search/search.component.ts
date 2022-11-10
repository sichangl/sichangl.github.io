import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, Output, ViewEncapsulation } from '@angular/core';
import { SearchService } from '../search.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { debounceTime, tap, switchMap, finalize, distinctUntilChanged, filter } from 'rxjs/operators';
import { NgbCarouselConfig } from '@ng-bootstrap/ng-bootstrap';
import { LocalStorageService } from '../local-storage.service';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

const API_KEY = "e8067b53"

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
  providers: [NgbCarouselConfig],  // add NgbCarouselConfig to the component providers
  encapsulation: ViewEncapsulation.None
})

export class SearchComponent implements OnInit {
  dis: string;
  link: any;
  window: any;
  constructor(
    private searchService:SearchService,
    lsService:LocalStorageService,           
    http: HttpClient,
    private formBuilder:FormBuilder,
    config: NgbCarouselConfig) { 
      config.interval = 2000;
      config.keyboard = true;
      config.pauseOnHover = true;
  }
  
  http:HttpClient;
  check = false;
  data: any = [];
  detail: any;
  userReviews: any = [];
  detailName: any;
  model:any;
  images: any;
  userEmail: any;
  userReserveDate: any;
  reserveHour: any;
  reserveMinute: any;
  showDetail = 0;
  showResult = 0;
  isOpen = false;
  reserveStatus = 0;
  searchKeyWordControl = new FormControl();
  isLoading = false;
  autoCate: any;
  autoTerms: any;

  autoDetectUrl = "https://ipinfo.io/";
  autoDetectToken = "77d13dbec3db4a";
  geolocation_api_key = 'AIzaSyAII0DbVU9uKZI07_PMYNLyRDeiXXH3a98';
  geolocationUrl = "https://maps.googleapis.com/maps/api/geocode/json?";
  keyWord: string;
  distance: string = '10';
  category: any;
  latitude = 0;
  longtitude = 0;
  location: any;
  successStatus = false;

  // get UserEmail() {
  //   return this.reservationForm.get('userEmail');
  // }
  // profileForm = new FormGroup({
  //   firstName: new FormControl('', Validators.required),
  //   lastName: new FormControl('', Validators.required),
  //   name: new FormControl('', Validators.required),
  // });
  // get name() { return this.profileForm.get('name'); }
  // onSubmit() {
  //   console.log(this.profileForm);
  // }
  

  mapOptions: google.maps.MapOptions = {
    center: { lat: 38.9987208, lng: -77.2538699 },
    zoom : 14,
  }

  marker = {
    position: { lat: this.latitude, lng: this.longtitude },
 }

  changePage() {
    this.showDetail = 1;
    this.showResult = 1;
  }

  clearAll(){
    this.showDetail = 0;
    this.showResult = 0;
    (<HTMLInputElement>document.getElementById("location")).value = '';
    (<HTMLInputElement>document.getElementById("keyWord")).value = '';
    this.dis = "10";
    (<HTMLInputElement>document.getElementById("sel")).value = 'default';
  }

  clickCheckBox() {
    this.check = !this.check;
    if (this.check) {
      console.log('disable location');
      (<HTMLInputElement>document.getElementById("location")).value = '';
      (<HTMLInputElement>document.getElementById("location")).disabled = true;
    } else {
      (<HTMLInputElement>document.getElementById("location")).disabled = false;
    }
    console.log("click on the checkBox");
  }

  async clickMe() {
    let that = this;
      if (this.check) { // auto detect.
        console.log("We need to autoDetect the lat&log of this ip");
        this.keyWord = this.searchService.getKeyWord();
        this.distance = this.searchService.getDistance();
        this.category = this.searchService.getCategory();
        await fetch(this.autoDetectUrl + "?token=" + this.autoDetectToken).then(response => response.json()).then(function(locData){
          that.searchService.searchYelp1(locData).subscribe((response)=>{
            console.log('receive response from node js backend')
            // console.log('response from post data is ', response);
            // get the response from yelp database : 
            that.data = response;
            console.log("the length of the data is :" + that.data.length)
            if (that.data.length <= 0) {
              that.showDetail = 0;
              console.log('no searchResult')
              that.showResult = 2;
            } else {
              console.log('get the searchResult')
              that.showDetail = 1;
              that.showResult = 1;
              console.log(that.data);
              console.log(that.showDetail);
              console.log(that.showResult);
            }
          },(error)=>{
            console.log('error during post is ', error)
          })
        })
      } else {
        this.searchService.getKeyWord();
        this.searchService.getDistance();
        this.searchService.getCategory();
        this.location = this.searchService.getLocation();
        await fetch(this.geolocationUrl + "address=" + this.location + "&key=" + this.geolocation_api_key)
        .then(response => response.json())
        .then(function(locData){
          that.searchService.searchYelp(locData).subscribe((response) => {
            console.log('receive response from node js backend')
            that.data = response;
            console.log(that.data)
            console.log("the length of the data is :" + that.data.length)
            if (that.data.length <= 0) {
              that.showDetail = 0;
              console.log('no searchResult')
              that.showResult = 2;
              console.log(that.showDetail);
              console.log(that.showResult);
            } else {
              console.log('get the searchResult')
              that.data = response;
              that.showDetail = 1;
              that.showResult = 1;
              console.log(that.data);
              console.log(that.showDetail);
              console.log(that.showResult);
            }
          },(error)=>{
            console.log('error during post is ', error)
          })
        })
      }
  }

  clearReserve() {
    (<HTMLInputElement>document.getElementById("email")).value = '';
    (<HTMLInputElement>document.getElementById("date")).value = '';
    (<HTMLInputElement>document.getElementById("hour")).value = '';
    (<HTMLInputElement>document.getElementById("minute")).value = '';
  }

  getBusinessDetail(yelpResult:any) {
    console.log('you have clicked on this row')
    var businessId = yelpResult.id;
    console.log('the id of the business is ' + businessId);
    this.showDetail = 2;
    this.searchService.searchBusinessDetail(businessId).subscribe((response) => {
      console.log('receive business detail from Node js');
      console.log(response);
      this.detail = response;
      this.detailName = this.detail.name;
      this.images = this.detail.photos;
      this.link = this.detail.url;
      console.log(this.images);
      // category string 
      var strr = '';
      for (let str of this.detail.categories) {
        console.log(str['title']);
        strr += str['title'] + ' | '
      }
      strr = strr.slice(0, -3);
      // is open or not
      console.log(this.detail.hours[0]);
      console.log(this.detail.hours[0].is_open_now);
      if (this.detail.hours[0].is_open_now === true) {
        console.log("I am open");
        this.isOpen = true;
      } else {
        console.log("I am closed");
        this.isOpen = false;
      }
      // for (let idx of this.detail.categories) {
      //   this.category += this.detail.categories[idx]['title'] + ' | '
      // }
      // this.category = this.category.slice(0, -3);
      (<HTMLInputElement>document.getElementById("address")).innerText = this.detail.location.display_address;
      (<HTMLInputElement>document.getElementById("category")).innerText = strr;
      (<HTMLInputElement>document.getElementById("phoneNumber")).innerText = this.detail.display_phone;
      (<HTMLInputElement>document.getElementById("price")).innerText = this.detail.price;
      (<HTMLLinkElement>document.getElementById("website")).href = this.detail.url;
      console.log(this.detail.url)
      this.getReview(yelpResult);
      (<HTMLInputElement>document.getElementById("detailCards")).style.visibility = 'visible';
    },(error) =>{
      console.log('request failed');
    })
  }

  getReview(yelpResult:any) {
    var businessId = yelpResult.id;
    console.log('i am here!!!!')
    this.searchService.searchReview(businessId).subscribe((response) => {
      console.log('receive reviews from Node js');
      console.log(response);
      this.userReviews = response;
      console.log(this.userReviews);
    },(error) =>{
      console.log('request failed');
    })
  }

  getReservation() {
      this.userEmail = (<HTMLInputElement>document.getElementById("email")).value;
      this.userReserveDate = (<HTMLInputElement>document.getElementById("date")).value;
      this.reserveHour = (<HTMLInputElement>document.getElementById("hour")).value;
      this.reserveMinute = (<HTMLInputElement>document.getElementById("minute")).value;
      console.log(this.userEmail);
      console.log(this.userReserveDate);
      console.log(this.reserveHour);
      console.log(this.reserveMinute);
      window.alert("Reservation created!");
      (<HTMLButtonElement>document.getElementById("reserveButton")).innerText = "Cancel Reservation";
      this.searchService.saveReservation(this.detailName, this.userReserveDate, this.reserveHour, this.userEmail);
      this.searchService.getAllReservation();
      console.log('successful saved in local memory')
  }

  tweet() {
    let url = "https://twitter.com/intent/tweet?text=";
    url += "Check " + this.detailName + " on Yelp." +this.link;
    window.open(url, "tweet");
  }

  displayWith(value: any) {
    console.log(value?.title);
    return value?.title;
  }

  onSelected() {
    console.log(this.autoCate);
    this.autoCate = this.autoCate;
  }

  ngOnInit() {
    this.dis = "10";
    this.searchKeyWordControl.valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(1000),
      tap(() => {
        this.autoCate = [];
        this.isLoading = true;
      }),
      switchMap(value => this.searchService.autoComplete(value).pipe(
        finalize(() => {
          this.isLoading = false;
        }),
      ))
    ).subscribe((autoCompleteRes: any) => {
      console.log('start autocomplete')
      console.log(autoCompleteRes);
      this.autoCate = autoCompleteRes['categories'];
      // this.autoTerms = autoCompleteRes['terms'];
      // console.log(this.autoTerms);
  });

  }
  // clickMe() {
  //   if (this.checkBox) {
  //     // call autoDetect.
  //   } else {
  //     // fetch lat&log for the certain location
  //     this.getKeyWord();
  //     this.getDistance();
  //     this.getCategory();
  //     this.getLocation();
  //     this.yelpUrl = this.yelpUrl + "?term=" + this.keyWord + "&categories=" + this.category 
  //                 + "&distance=" + this.distance;
  //     this.getGeolocationData();
  //   }
  // }

  // getKeyWord() {
  //   this.keyWord = (<HTMLInputElement>document.getElementById("keyWord")).value;
  // }
  
  // getDistance() {
  //   this.distance = (<HTMLInputElement>document.getElementById("distance")).value;
  // }

  // getCategory() {
  //   this.category = (<HTMLInputElement>document.getElementById("sel")).value;
  // }

  // getLocation() {
  //   this.location = (<HTMLInputElement>document.getElementById("location")).value;
  // }

  // getGeolocationData() {
  //   this.http.get(this.geolocationUrl + "address=" + this.location + "&key=" + this.geolocation_api_key).subscribe((res:any) => {
  //     this.latitude = res.results[0].geometry.location.lat;
  //     this.longitude = res.results[0].geometry.location.lng;
  //     console.log(this.latitude, this.longitude);
  //     this.yelpUrl = this.yelpUrl + "&latitude=" + this.latitude + "&longitude" + this.longitude;
  //   })
  // }
}
