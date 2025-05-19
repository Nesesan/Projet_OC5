import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';
import { HttpClientTestingModule, HttpTestingController } from "@angular/common/http/testing";
import { Teacher } from "../interfaces/teacher.interface";

describe('TeacherService', () => {
  let service: TeacherService;
  let httpMock: HttpTestingController;

  const mockTeachers: Teacher[] = [
    { id: 1, firstName: 'Jacques', lastName: 'Henri', createdAt: new Date(), updatedAt: new Date() },
    { id: 2, firstName: 'Edouardo', lastName: 'Mitchell', createdAt: new Date(), updatedAt: new Date() }
  ];

  const mockTeacher: Teacher = { id: 1, firstName: 'Jacques', lastName: 'Henri', createdAt: new Date(), updatedAt: new Date() };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeacherService]
    });

    service = TestBed.inject(TeacherService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });


  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('should fetch all teachers via GET', () => {
    service.all().subscribe((teachers) => {
      expect(teachers.length).toBe(2);
      expect(teachers).toEqual(mockTeachers);
    });


    const req = httpMock.expectOne('api/teacher');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeachers);
  });


  it('should fetch teacher by id via GET', () => {
    const teacherId = '1';

    service.detail(teacherId).subscribe((teacher) => {
      expect(teacher).toEqual(mockTeacher);
    });

    const req = httpMock.expectOne(`api/teacher/${teacherId}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeacher);
  });
});
