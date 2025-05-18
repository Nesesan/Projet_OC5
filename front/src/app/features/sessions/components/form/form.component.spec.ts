import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { FormComponent } from './form.component';
import { TeacherService } from '../../../../services/teacher.service';
import { of } from 'rxjs';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let mockRouter: Partial<Router>;

  const mockSessionService = {
    sessionInformation: {
      admin: false,
      id: 1,
      token: 'fake-token',
      type: 'admin',
      username: 'admin_user',
      firstName: 'admin',
      lastName: 'user'
    }
  } as unknown as SessionService;

  const mockSessionApiService = {
    create: jest.fn().mockReturnValue(of({})),
    update: jest.fn().mockReturnValue(of({})),
    detail: jest.fn().mockReturnValue(of({
      id: 1,
      name: 'test Session',
      date: new Date(),
      teacher_id: 1,
      description: 'test description'
    })),
  } as unknown as SessionApiService;

  const mockMatSnackBar = { open: jest.fn() };
  const mockTeacherService = { all: jest.fn().mockReturnValue(of([])) };

  const setupTestBed = async (mode: 'create' | 'update') => {
    mockRouter = { navigate: jest.fn(), url: mode === 'create' ? '/sessions/create' : '/sessions/update/1' };

    const mockActivatedRoute = {
      snapshot: { paramMap: convertToParamMap(mode === 'create' ? {} : { id: '1' }) }
    };

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([]),
        HttpClientModule,
        MatCardModule,
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: mockSessionApiService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: MatSnackBar, useValue: mockMatSnackBar },
        { provide: TeacherService, useValue: mockTeacherService },
      ],
      declarations: [FormComponent]
    }).compileComponents();

    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  };

  describe('Create Mode', () => {
    beforeEach(async () => await setupTestBed('create'));


    it('should create', () => expect(component).toBeTruthy());


    it('should initialize form for create mode', () => {
      const form = component.sessionForm;
      expect(form).toBeDefined();
      expect(form?.get('name')?.value).toBe('');
      expect(form?.get('date')?.value).toBe('');
      expect(form?.get('teacher_id')?.value).toBe('');
      expect(form?.get('description')?.value).toBe('');
    });
  });

  describe('Update Mode', () => {
    beforeEach(async () => await setupTestBed('update'));

    it('should fetch session and populate form in update mode', () => {
      expect(mockSessionApiService.detail).toHaveBeenCalledWith('1');
      expect(component.sessionForm?.get('name')?.value).toBe('test Session');
      expect(component.sessionForm?.get('description')?.value).toBe('test description');
      expect(component.sessionForm?.get('teacher_id')?.value).toBe(1);
    });
  });

  describe('Admin Redirection', () => {

    it('should redirect to /sessions if user is not an admin', async () => {
      mockSessionService.sessionInformation!.admin = false;
      await setupTestBed('create');
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/sessions']);
    });


    it('should NOT redirect if user is an admin', async () => {
      mockSessionService.sessionInformation!.admin = true;
      await setupTestBed('create');
      expect(mockRouter.navigate).not.toHaveBeenCalled();
    });
  });

  describe('Form Submission', () => {
    it('should call create service when in create mode', async () => {
      mockSessionService.sessionInformation!.admin = true;
      await setupTestBed('create');
      component.sessionForm?.setValue({
        name: 'New Session',
        date: '2025-04-23',
        teacher_id: 1,
        description: 'A new session'
      });
      component.submit();
      expect(mockSessionApiService.create).toHaveBeenCalled();
      expect(mockRouter.navigate).toHaveBeenCalledWith(['sessions']);
    });

    it('should call update service when in update mode', async () => {
      mockSessionService.sessionInformation!.admin = true;
      await setupTestBed('update');
      component.sessionForm?.setValue({
        name: 'Updated Session',
        date: '2025-04-23',
        teacher_id: 1,
        description: 'Updated description'
      });
      component.submit();
      expect(mockSessionApiService.update).toHaveBeenCalled();
      expect(mockRouter.navigate).toHaveBeenCalledWith(['sessions']);
    });
  });
});
