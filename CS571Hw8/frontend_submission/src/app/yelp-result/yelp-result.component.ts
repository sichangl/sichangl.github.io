import { HttpClient } from '@angular/common/http';
import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SearchService } from '../search.service';


@Component({
  selector: 'app-yelp-result',
  templateUrl: './yelp-result.component.html',
  styleUrls: ['./yelp-result.component.css']
})
export class YelpResultComponent implements OnInit {
  @Input() yelpReturnedData:any
  @Input() businessDetail:any
  category: any;
  
  constructor(private searchService:SearchService,
              http:HttpClient, 
              router: Router,
              route: ActivatedRoute) { }


  
  http:any
  ngOnInit(): void {
    for(let cate of this.businessDetail.categories) {
      this.category += cate.title + ' | ';
    }
    this.category = this.category.slice(0,-3);
    console.log(this.category);
  }

}
