import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { SessionService } from 'src/app/services/session.service';

import { LoginComponent } from './login.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { SessionInformation } from '../../../../interfaces/sessionInformation.interface';
import {of} from "rxjs";

describe('LoginComponent (Integration)', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let httpMock: HttpTestingController;
  let router: Router;
  let sessionService: SessionService;
  let authService: AuthService;

  const mockSession: SessionInformation = {
    token: 'fake_token',
    type: 'user',
    id: 1,
    username: 'user@mail.com',
    firstName: 'User',
    lastName: 'One',
    admin: false
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      providers: [SessionService, AuthService],
      imports: [
        RouterTestingModule.withRoutes([]),
        BrowserAnimationsModule,
        HttpClientTestingModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
    authService = TestBed.inject(AuthService);
    jest.spyOn(router, 'navigate');
    jest.spyOn(sessionService, 'logIn');
    fixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify();
    jest.clearAllMocks();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should set form values correctly', () => {
    component.form.setValue({
      email: 'user@mail.com',
      password: 'password'
    });

    expect(component.form.value.email).toBe('user@mail.com');
    expect(component.form.value.password).toBe('password');
  });

  it('should call authService.login with correct payload', () => {
    const loginRequest = { email: 'user@mail.com', password: 'password' };
    const loginSpy = jest
      .spyOn(authService, 'login')
      .mockReturnValue(of(mockSession));

    component.form.setValue(loginRequest);
    component.submit();

    expect(loginSpy).toHaveBeenCalledWith(loginRequest);
  });

  it('should perform full login flow on successful login', () => {
    component.form.setValue({
      email: 'user@mail.com',
      password: 'password'
    });

    component.submit();

    const req = httpMock.expectOne('api/auth/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({
      email: 'user@mail.com',
      password: 'password'
    });

    req.flush(mockSession);

    expect(sessionService.logIn).toHaveBeenCalledWith(mockSession);
    expect(router.navigate).toHaveBeenCalledWith(['/sessions']);
  });


  it('should set onError to true on failed login', () => {
    component.form.setValue({
      email: 'user@mail.com',
      password: 'wrongPassword'
    });

    component.submit();

    const req = httpMock.expectOne('api/auth/login');
    req.flush(
      { message: 'Unauthorized' },
      { status: 401, statusText: 'Unauthorized' }
    );

    expect(component.onError).toBe(true);
  });
});
