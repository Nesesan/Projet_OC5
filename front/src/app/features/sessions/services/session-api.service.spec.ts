import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SessionApiService } from './session-api.service';
import { Session } from '../interfaces/session.interface';

describe('SessionApiService', () => {
  let service: SessionApiService;
  let httpMock: HttpTestingController;

  const fakeSession: Session = {
    id: 1,
    name: 'Test Session',
    description: 'Session Description',
    date: new Date(),
    teacher_id: 1,
    users: [],
    createdAt: new Date(),
    updatedAt: new Date()
  };

  const fakeSessions: Session[] = [fakeSession];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [SessionApiService]
    });

    service = TestBed.inject(SessionApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });


  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('should fetch all sessions', () => {
    service.all().subscribe(sessions => {
      expect(sessions).toEqual(fakeSessions);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('GET');
    req.flush(fakeSessions);
  });


  it('should fetch session detail by id', () => {
    const sessionId = '1';

    service.detail(sessionId).subscribe(session => {
      expect(session).toEqual(fakeSession);
    });

    const req = httpMock.expectOne(`api/session/${sessionId}`);
    expect(req.request.method).toBe('GET');
    req.flush(fakeSession);
  });

  it('should create a new session', () => {
    service.create(fakeSession).subscribe(session => {
      expect(session).toEqual(fakeSession);
    });

    const req = httpMock.expectOne('api/session');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(fakeSession);
    req.flush(fakeSession);
  });

  it('should update an existing session', () => {
    const sessionId = '1';

    service.update(sessionId, fakeSession).subscribe(session => {
      expect(session).toEqual(fakeSession);
    });

    const req = httpMock.expectOne(`api/session/${sessionId}`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(fakeSession);
    req.flush(fakeSession);
  });


  it('should delete a session', () => {
    const sessionId = '1';

    service.delete(sessionId).subscribe(response => {
      expect(response).toEqual({});
    });

    const req = httpMock.expectOne(`api/session/${sessionId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should participate in a session', () => {
    const sessionId = '1';
    const userId = '123';

    service.participate(sessionId, userId).subscribe(response => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne(`api/session/${sessionId}/participate/${userId}`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toBeNull();
    req.flush(null);
  });


  it('should unparticipate from a session', () => {
    const sessionId = '1';
    const userId = '123';

    service.unParticipate(sessionId, userId).subscribe(response => {
      expect(response).toBeUndefined();
    });

    const req = httpMock.expectOne(`api/session/${sessionId}/participate/${userId}`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});

