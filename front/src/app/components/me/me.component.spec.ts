import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { MeComponent } from './me.component';
import { SessionService } from '../../services/session.service';
import { UserService } from '../../services/user.service';
import { User } from '../../interfaces/user.interface';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let httpMock: HttpTestingController;
  let sessionService: SessionService;
  let router: Router;
  let snackBar: MatSnackBar;

  const fakeUser: User = {
    id: 1,
    admin: false,
    firstName: 'User',
    lastName: 'One',
    email: 'user@mail.com',
    password: 'password123',
    createdAt: new Date(),
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MeComponent],
      imports: [
        MatSnackBarModule,
        RouterTestingModule.withRoutes([]),
        HttpClientTestingModule,
      ],
      providers: [UserService, SessionService],
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
    sessionService = TestBed.inject(SessionService);
    router = TestBed.inject(Router);
    snackBar = TestBed.inject(MatSnackBar);
    httpMock = TestBed.inject(HttpTestingController);

    sessionService['sessionInformation'] = {
      admin: false,
      firstName: 'User',
      lastName: 'One',
      token: 'fakeToken',
      type: 'Bearer',
      username: 'User1',
      id: 1,
    };

    jest.spyOn(snackBar, 'open').mockReturnValue({} as any);
    jest.spyOn(sessionService, 'logOut').mockImplementation(() => {});
    jest.spyOn(router, 'navigate').mockImplementation(() => Promise.resolve(true));
  });

  afterEach(() => {
    httpMock.verify();
    jest.clearAllMocks();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call back method', () => {
    const backSpy = jest.spyOn(window.history, 'back').mockImplementation(() => {});
    component.back();
    expect(backSpy).toHaveBeenCalled();
    backSpy.mockRestore();
  });

  it('should fetch user on init and assign to component', () => {
    fixture.detectChanges();

    const req = httpMock.expectOne('api/user/1');
    expect(req.request.method).toBe('GET');
    req.flush(fakeUser);

    expect(component.user).toEqual(fakeUser);
  });

  it('should delete the user and perform cleanup', fakeAsync(() => {
    component.delete();

    const req = httpMock.expectOne('api/user/1');
    expect(req.request.method).toBe('DELETE');
    req.flush(null, { status: 200, statusText: 'OK' });

    tick();

    expect(snackBar.open).toHaveBeenCalledWith(
      'Your account has been deleted !',
      'Close',
      { duration: 3000 }
    );
    expect(sessionService.logOut).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/']);
  }));
});
