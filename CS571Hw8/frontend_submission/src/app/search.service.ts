import { Injectable, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  // @Input() keyWord:any
  constructor(private http: HttpClient) { }

  keyWord = "";
  distance = "";
  location = "";
  category = "";
  latitude = "";
  longitude = "";
  yelp_api_key = '';
  yelpUrl = "https://hw8-backend-368020.wl.r.appspot.com/search";
  businessDetailUrl = 'https://hw8-backend-368020.wl.r.appspot.com/searchBusinessDetail'
  reviewUrl = 'https://hw8-backend-368020.wl.r.appspot.com/review'
  geolocation_api_key = 'AIzaSyAII0DbVU9uKZI07_PMYNLyRDeiXXH3a98';
  geolocationUrl = "https://maps.googleapis.com/maps/api/geocode/json?";
  autoDetectUrl = "https://ipinfo.io/";
  autoDetectToken = "77d13dbec3db4a";
  checkBox = false;
  message: any;

  // clickMe() {
  //   if (this.checkBox) {
  //     // call autoDetect.
  //   } else {
  //     // fetch lat&log for the certain location
  //     this.yelpUrl = this.yelpUrl + "?term=" + this.keyWord + "&categories=" + this.category 
  //                 + "&distance=" + this.distance;
  //     this.getGeolocationData();
  //   }
  // }

  getKeyWord() {
    this.keyWord = (<HTMLInputElement>document.getElementById("keyWord")).value;
    return this.keyWord;
  }

  getDistance() {
    this.distance = (<HTMLInputElement>document.getElementById("distance")).value;
    return this.distance;
  }

  getCategory() {
    this.category = (<HTMLInputElement>document.getElementById("sel")).value;
    return this.getCategory;
  }

  getLocation() {
    this.location = (<HTMLInputElement>document.getElementById("location")).value;
    return this.location;
  }

  getGeolocationData() {
    this.http.get(this.geolocationUrl + "address=" + this.location + "&key=" + this.geolocation_api_key).subscribe((res:any) => {
      this.latitude = res.results[0].geometry.location.lat;
      this.longitude = res.results[0].geometry.location.lng;
      console.log(this.latitude, this.longitude);
    })
  }

  autoDetectIp() {
    var loc;
    this.http.get(this.autoDetectUrl + "?token=" + this.autoDetectToken).subscribe((res:any) => {
      var result = res.loc;
      var loc = result.split(",");
      this.latitude = loc[0];
      this.longitude = loc[1];
      console.log(this.latitude, this.longitude);
    })
    return loc;
  }

  autoComplete(value) {
    console.log(value);
    return this.http.get('https://hw8-backend-368020.wl.r.appspot.com/autoComplete' + '?text=' + value);
  }

  searchYelp(data) {
    // step 1 ： get all the key word form user text input
    console.log(data.results[0]['geometry']['location']);
    this.latitude = data.results[0]['geometry']['location']['lat'];
    this.longitude = data.results[0]['geometry']['location']['lng'];
    console.log(this.latitude, this.longitude);
    var searchYelpUrl = this.yelpUrl + "?term=" + this.keyWord + "&categories=" + this.category 
    + "&distance=" + this.distance + "&latitude=" + this.latitude + "&longitude=" + this.longitude;
    console.log(searchYelpUrl);
    return this.http.get(searchYelpUrl);
  }

  searchYelp1(data) {
    console.log(data);
    var result = data.loc;
    var loc = result.split(",");
    this.latitude = loc[0];
    this.longitude = loc[1];
    console.log(this.latitude, this.longitude);
    var searchYelpUrl = this.yelpUrl + "?term=" + this.keyWord + "&categories=" + this.category 
    + "&distance=" + this.distance + "&latitude=" + this.latitude + "&longitude=" + this.longitude;
    console.log(searchYelpUrl);
    return this.http.get(searchYelpUrl);
  }


  searchBusinessDetail(businessId:any) {
    return this.http.get(this.businessDetailUrl + '?businessId=' + businessId);
  }

  searchReview(businessId:any) {
    return this.http.get(this.reviewUrl + '?businessId=' + businessId);
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
