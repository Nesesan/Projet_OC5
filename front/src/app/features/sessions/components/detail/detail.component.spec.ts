import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { Router, ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { DetailComponent } from './detail.component';
import { SessionService } from '../../../../services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { TeacherService } from '../../../../services/teacher.service';
import { Session } from '../../interfaces/session.interface';
import { Teacher } from '../../../../interfaces/teacher.interface';

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;

  let mockSessionService: Partial<SessionService>;
  let mockSessionApiService: jest.Mocked<SessionApiService>;
  let mockTeacherService: jest.Mocked<TeacherService>;
  let mockMatSnackBar: jest.Mocked<MatSnackBar>;
  let mockRouter: jest.Mocked<Router>;

  const mockActivatedRoute = {
    snapshot: {
      paramMap: {
        get: jest.fn().mockReturnValue('123'),
      },
    },
  };

  const mockSession: Session = {
    id: 123,
    users: [1],
    teacher_id: 42,
  } as Session;

  const mockTeacher: Teacher = {
    id: 42,
    firstName: 'Charles',
    lastName: 'Xavier',
    createdAt: new Date('2023-01-01'),
    updatedAt: new Date('2023-01-02')
  };

  beforeEach(async () => {
    mockSessionService = {
      sessionInformation: {
        id: 1,
        admin: true,
        token: '123456789',
        type: 'admin',
        username: 'professeurX',
        firstName: 'Charles',
        lastName: 'Xavier'
      }
    };

    mockSessionApiService = {
      detail: jest.fn().mockReturnValue(of(mockSession)),
      delete: jest.fn().mockReturnValue(of(void 0)),
      participate: jest.fn().mockReturnValue(of(void 0)),
      unParticipate: jest.fn().mockReturnValue(of(void 0)),
    } as unknown as jest.Mocked<SessionApiService>;

    mockTeacherService = {
      detail: jest.fn().mockReturnValue(of(mockTeacher)),
    } as unknown as jest.Mocked<TeacherService>;

    mockMatSnackBar = {
      open: jest.fn(),
    } as unknown as jest.Mocked<MatSnackBar>;

    mockRouter = {
      navigate: jest.fn(),
    } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        MatSnackBarModule,
        ReactiveFormsModule,
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: TeacherService, useValue: mockTeacherService },
        { provide: MatSnackBar, useValue: mockMatSnackBar },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });


  it('should call fetchSession on ngOnInit', () => {
    const fetchSpy = jest.spyOn(component as any, 'fetchSession');
    component.ngOnInit();
    expect(fetchSpy).toHaveBeenCalled();
  });

  it('should call delete and navigate on delete()', () => {
    component.delete();

    expect(mockSessionApiService.delete).toHaveBeenCalledWith('123');
    expect(mockMatSnackBar.open).toHaveBeenCalledWith('Session deleted !', 'Close', { duration: 3000 });
    expect(mockRouter.navigate).toHaveBeenCalledWith(['sessions']);
  });

  it('should participate and fetchSession', () => {
    const fetchSpy = jest.spyOn(component as any, 'fetchSession');
    component.participate();
    expect(mockSessionApiService.participate).toHaveBeenCalledWith('123', '1');
    expect(fetchSpy).toHaveBeenCalled();
  });


  it('should unParticipate and fetchSession', () => {
    const fetchSpy = jest.spyOn(component as any, 'fetchSession');
    component.unParticipate();
    expect(mockSessionApiService.unParticipate).toHaveBeenCalledWith('123', '1');
    expect(fetchSpy).toHaveBeenCalled();
  });


  it('should set isParticipate to true and load teacher in fetchSession()', () => {
    (component as any).fetchSession();

    expect(mockSessionApiService.detail).toHaveBeenCalledWith('123');
    expect(mockTeacherService.detail).toHaveBeenCalledWith('42');
    expect(component.isParticipate).toBe(true);
    expect(component.teacher?.firstName).toBe('Charles');
    expect(component.teacher?.lastName).toBe('Xavier');
  });

  it('should call window.history.back on back()', () => {
    const backSpy = jest.spyOn(window.history, 'back');
    component.back();
    expect(backSpy).toHaveBeenCalled();
  });
});
