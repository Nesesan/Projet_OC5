import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {User} from "../interfaces/user.interface";

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;

  const mockUser: User = {
    id:1,
    email:'user@email.com',
    password: 'password1',
    firstName:'first',
    lastName: 'User',
    admin: false,
    createdAt: new Date(),
    updatedAt: new Date()}

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientTestingModule
      ],
      providers: [UserService]
    });
    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
  });
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get a user by id via GET', ()=> {
    const user_id = '1';

    service.getById(user_id).subscribe(user => {
      expect(user).toEqual(mockUser);
    });
    const req = httpMock.expectOne(`api/user/${user_id}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('should delete user by ID via DELETE', () => {
    const user_Id = '1';

    service.delete(user_Id).subscribe((response) => {
      expect(response).toEqual({ message: 'User deleted' }); // Simule une réponse attendue
    });

    const req = httpMock.expectOne(`api/user/${user_Id}`);
    expect(req.request.method).toBe('DELETE');
    req.flush({ message: 'User deleted' });
  });

});
