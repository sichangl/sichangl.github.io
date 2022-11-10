//app.component.ts
import { Component, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { debounceTime, tap, switchMap, finalize, distinctUntilChanged, filter } from 'rxjs/operators';

const API_KEY = "e8067b53"

@Component({
  selector: 'app-autocomplete',
  templateUrl: './autoComplete.component.html',
  styleUrls: ['./autoComplete.component.css']
})
export class AutocompleteComponent implements OnInit {
  @Output() value:any
  autoCate:any;
  autoTerms:any;
  searchMoviesCtrl = new FormControl();
  filteredMovies: any;
  isLoading = false;
  errorMsg!: string;
  minLengthTerm = 3;
  selectedMovie: any = ""; 

  constructor(
    private http: HttpClient
  ) { }

  onSelected() {
    console.log(this.selectedMovie);
    this.selectedMovie = this.selectedMovie;
  }

  displayWith(value: any) {
    return value?.Title;
  }

  clearSelection() {
    this.selectedMovie = "";
    this.filteredMovies = [];
  }

  ngOnInit() {
    // this.searchMoviesCtrl.valueChanges
    //   .pipe(
    //     filter(res => {
    //       return res !== null && res.length >= this.minLengthTerm
    //     }),
    //     distinctUntilChanged(),
    //     debounceTime(1000),
    //     tap(() => {
    //       this.errorMsg = "";
    //       this.filteredMovies = [];
    //       this.isLoading = true;
    //     }),
    //     switchMap(value => this.http.get('http://www.omdbapi.com/?apikey=' + API_KEY + '&s=' + value)
    //       .pipe(
    //         finalize(() => {
    //           this.isLoading = false
    //         }),
    //       )
    //     )
    //   )
    //   .subscribe((data: any) => {
    //     if (data['Search'] == undefined) {
    //       this.errorMsg = data['Error'];
    //       this.filteredMovies = [];
    //     } else {
    //       this.errorMsg = "";
    //       this.filteredMovies = data['Search'];
    //     }
    //     console.log(this.filteredMovies);
    //   });
    this.searchMoviesCtrl.valueChanges.pipe(
      distinctUntilChanged(),
      debounceTime(1000),
      tap(() => {
        this.errorMsg = "";
        this.autoCate = [];
        this.autoTerms = [];
        this.isLoading = true;
      }),
      switchMap(value => this.http.get('/api/autocomplete?term=' + value).pipe(
        finalize(() => {
          this.isLoading = false;
        }),
      ))
    ).subscribe((autoCompleteRes: any) => {
        this.autoCate = autoCompleteRes['categories'];
        this.autoTerms = autoCompleteRes['terms'];
        console.log(this.autoCate)
        console.log(this.autoTerms);
    });
}
}
