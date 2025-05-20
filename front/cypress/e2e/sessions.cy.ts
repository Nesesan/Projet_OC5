Cypress.Commands.add('loginAsAdmin', () => {
  cy.visit('http://localhost:4200/');
  cy.contains('Login').click();
  cy.get('#mat-input-0').type('yoga@studio.com');
  cy.get('#mat-input-1').type('test!1234');
  cy.contains('Submit').click();
});

describe('Sessions', () => {
  beforeEach(() => {
    cy.loginAsAdmin();
  });

  it('should create a new session successfully', () => {
    cy.contains('Create').click();
    cy.url().should('include', '/sessions/create');

    cy.get('input[formControlName="name"]').type('session yoga 2');
    cy.get('input[formControlName="date"]').type('2025-06-10');
    cy.get('mat-select[formControlName="teacher_id"]').click();
    cy.contains('Hélène THIERCELIN').click();
    cy.get('textarea[formControlName="description"]').type('test de update');

    cy.contains('Save').click();

    cy.url().should('include', '/sessions');
    cy.contains('yoga 2').should('exist');


  });


  it('should delete a session successfully', () => {
    cy.contains('Detail').click();
    cy.url().should('include', '/sessions/detail');
    cy.contains('Delete').click();
    cy.url().should('include', '/sessions');

  })

});

describe('Session error', () => {
  it('should not create a session if a field is missing', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      }
    }).as('loginRequest');

    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: {
        userId: 1,
        username: 'userName',
        isAuthenticated: true
      }
    }).as('getSession');

    cy.intercept('POST', '/api/sessions', {
      statusCode: 201,
      body: {
        id: 1,
        name: 'Yoga for Beginners',
        description: 'A beginner-friendly yoga session',
        date: '2025-05-10T09:00:00',
        createdAt: '2025-05-01T12:00:00',
        updatedAt: '2025-05-01T12:00:00',
      }
    }).as('createSession');

    cy.get('input[formControlName=email]').type("yoga@studio.com");
    cy.get('input[formControlName=password]').type('test!1234');
    cy.get('form').submit();

    cy.wait('@loginRequest');
    cy.url().should('include', '/sessions');
    cy.wait('@getSession');

    cy.contains('Create').click();
    cy.url().should('include', '/sessions/create');

    cy.get('input[formControlName="name"]').should('be.visible');
    cy.get('input[formControlName="date"]').should('be.visible');
    cy.get('mat-select[formControlName="teacher_id"]').should('be.visible');
    cy.get('textarea[formControlName="description"]').should('be.visible');
    cy.get('button[type=submit]').should('be.visible');

    cy.get('button[type=submit]').should('be.disabled');
  });

})
