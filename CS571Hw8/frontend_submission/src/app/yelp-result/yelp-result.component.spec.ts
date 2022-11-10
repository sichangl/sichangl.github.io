import { ComponentFixture, TestBed } from '@angular/core/testing';

import { YelpResultComponent } from './yelp-result.component';

describe('YelpResultComponent', () => {
  let component: YelpResultComponent;
  let fixture: ComponentFixture<YelpResultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ YelpResultComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(YelpResultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
