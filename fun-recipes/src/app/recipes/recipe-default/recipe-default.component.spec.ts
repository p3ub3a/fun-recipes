import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecipeDefaultComponent } from './recipe-default.component';

describe('RecipeDefaultComponent', () => {
  let component: RecipeDefaultComponent;
  let fixture: ComponentFixture<RecipeDefaultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RecipeDefaultComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RecipeDefaultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
