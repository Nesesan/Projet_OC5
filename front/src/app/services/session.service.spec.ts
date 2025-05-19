import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionService } from './session.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {SessionApiService} from "../features/sessions/services/session-api.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Session} from "../features/sessions/interfaces/session.interface";
import {SessionInformation} from "../interfaces/sessionInformation.interface";

describe('SessionService', () => {
  let serviceApi: SessionApiService;
  let service: SessionService;
  let httpTestingController: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService, SessionService]
    });
    serviceApi = TestBed.inject(SessionApiService);
    service = TestBed.inject(SessionService);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  it('should be created', () => {
    expect(serviceApi).toBeTruthy();
  });

  it('should log out and clear session information', () => {
    const mockSessionInfo: SessionInformation = {
      token: 'mockToken',
      type: 'user',
      id: 1,
      username: 'testUser',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    };

    service.logIn(mockSessionInfo);
    service.logOut();

    expect(service.isLogged).toBe(false);
    expect(service.sessionInformation).toBeUndefined();
  });

  it('should emit false initially from $isLogged()', (done) => {
    service.$isLogged().subscribe(value => {
      expect(value).toBe(false);
      done();
    });
  });

  it('should emit true after logIn() via $isLogged()', (done) => {
    const mockSessionInfo: SessionInformation = {
      token: 'mockToken',
      type: 'user',
      id: 1,
      username: 'testUser',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    };

    service.$isLogged().subscribe(value => {
      if (value) {
        expect(value).toBe(true);
        done();
      }
    });



    service.logIn(mockSessionInfo);
  });


  it('should return error with sessions', () => {
    const errorMessage = 'Error retrieving sessions';

    serviceApi.all().subscribe(
      () => fail('should have failed'),
      error => {
        expect(error instanceof HttpErrorResponse).toBeTruthy();
        expect(error.status).toBe(500);
        expect(error.error).toEqual(errorMessage);
      }
    );

    const req = httpTestingController.expectOne('api/session');
    expect(req.request.method).toEqual('GET');

    req.flush(errorMessage, { status: 500, statusText: 'Server Error' });
  });


  it('should retrieve all sessions', () => {
    const testData = [
      { id: 1, name: 'Session 1' },
      { id: 2, name: 'Session 2' }
    ];

    serviceApi.all().subscribe(sessions => {
      expect(sessions).toEqual(testData);
    });

    const req = httpTestingController.expectOne('api/session');
    expect(req.request.method).toEqual('GET');
    req.flush(testData);
  });

  it('should retrieve a session by id', () => {
    const testData = { id: 1, name: 'Session 1' };

    serviceApi.detail('1').subscribe(session => {
      expect(session).toEqual(testData);
    });

    const req = httpTestingController.expectOne('api/session/1');
    expect(req.request.method).toEqual('GET');
    req.flush(testData);
  });

  it('should retrieve session detail by ID', () => {
    const testSession: Session = { id: 1, name: 'Session 1', description: 'Description 1', date: new Date(), teacher_id: 1, users: [], createdAt: new Date(), updatedAt: new Date() };
    const sessionId = '1';

    serviceApi.detail(sessionId).subscribe(session => {
      expect(session).toEqual(testSession);
    });

    const req = httpTestingController.expectOne(`api/session/${sessionId}`);
    expect(req.request.method).toEqual('GET');

    req.flush(testSession);
  });


  it('should update session', () => {
    const testData: Session = {
      id: 1,
      name: 'Updated Session',
      description: 'Updated Description',
      date: new Date(),
      teacher_id: 123,
      users: [1,2]
    };

    serviceApi.update('1', testData).subscribe(session => {
      expect(session).toEqual(testData);
    });

    const req = httpTestingController.expectOne('api/session/1');
    expect(req.request.method).toEqual('PUT');
    req.flush(testData);
  });

  it('should add a participant to a session', () => {
    const sessionId = '1';
    const userId = '2';

    let addedParticipant: any;

    serviceApi.participate(sessionId, userId).subscribe((response: any) => {
      addedParticipant = response;
    });

    const req = httpTestingController.expectOne(`api/session/${sessionId}/participate/${userId}`);
    expect(req.request.method).toEqual('POST');

    const mockResponse = { sessionId: sessionId, userId: userId }; // Définir les données du participant ajouté
    req.flush(mockResponse);

    expect(addedParticipant.sessionId).toEqual(sessionId);
    expect(addedParticipant.userId).toEqual(userId);
  });

  it('should log in and update session information', () => {
    const mockSessionInfo: SessionInformation = {
      token: 'mockToken',
      type: 'user',
      id: 1,
      username: 'testUser',
      firstName: 'Test',
      lastName: 'User',
      admin: false
    };

    service.logIn(mockSessionInfo);

    expect(service.isLogged).toBe(true);
    expect(service.sessionInformation).toEqual(mockSessionInfo);
  });



});
