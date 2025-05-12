import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { expect } from '@jest/globals';

import { RegisterComponent } from './register.component';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Router} from "@angular/router";

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let httpMock: HttpTestingController;
  let router: Router;

  const validRegisterData = {
    firstName: 'user',
    lastName: 'One',
    email: 'user@mail.com',
    password: 'password',
  }

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      imports: [
        BrowserAnimationsModule,
        HttpClientTestingModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule
      ]
    })
      .compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    router = TestBed.inject(Router);
    httpMock = TestBed.inject(HttpTestingController);

    jest.spyOn(router, 'navigate');

    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('Should set form values correctly', () => {
    component.form.setValue(validRegisterData);

    expect(component.form.value.firstName).toEqual('user');
    expect(component.form.value.lastName).toEqual('One');
    expect(component.form.value.email).toEqual('user@mail.com');
    expect(component.form.value.password).toEqual('password');
  });

  it('Should register successfully and navigate to login', () => {
    component.form.setValue(validRegisterData);

    fixture.detectChanges();

    component.submit();

    const req = httpMock.expectOne('api/auth/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(validRegisterData);

    req.flush({})

    expect(router.navigate).toHaveBeenCalledWith(['/login']);
  });

  it('should handle registration error correctly', () => {

    component.form.setValue(validRegisterData);
    component.submit();

    const req = httpMock.expectOne('api/auth/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(validRegisterData);

    req.flush(
      {message: 'Registration failed'},
      {status: 400, statusText: 'Bad Request'}
    );

    expect(component.onError).toBe(true);

  });
});
