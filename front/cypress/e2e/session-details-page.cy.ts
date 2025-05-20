Cypress.Commands.add('loginAsUser', () => {
  cy.visit('http://localhost:4200/');
  cy.contains('Login').click();
  cy.get('#mat-input-0').type('lounss@mail.com');
  cy.get('#mat-input-1').type('password');
  cy.contains('Submit').click();
});

Cypress.Commands.add('loginAsAdmin', () => {
  cy.visit('http://localhost:4200/');
  cy.contains('Login').click();
  cy.get('#mat-input-0').type('yoga@studio.com');
  cy.get('#mat-input-1').type('test!1234');
  cy.contains('Submit').click();
});

describe('User Login and Session Detail Redirect', () => {
  it('logs in and redirects to session detail page', () => {
    cy.loginAsUser();
    cy.get('.list').should('exist')
    cy.contains("Detail").click()
    cy.contains('button', 'Participate').should('be.visible');
    cy.contains('attendees').should('exist')
  });

  describe('Detail Page – User View', () => {
    it('should display session title, teacher, attendees, and participate button', () => {
      cy.loginAsUser();
      cy.get('.list').should('exist');
      cy.contains('Detail').click();

      cy.get('h1').should('exist');

      cy.get('mat-card-subtitle').contains(/.+ [A-Z]+/).should('exist');

      cy.contains('attendees').should('exist');

      cy.contains('button', 'Participate').should('be.visible');
    });
  });

  describe('Detail Page – Participation Flow', () => {
    it('should allow a user to participate in a session', () => {
      cy.loginAsUser();
      cy.get('.list').should('exist');
      cy.contains('Detail').click();

      cy.contains('button', 'Participate').click();

      cy.contains('button', 'Do not participate').should('be.visible');

      cy.contains('button', 'Do not participate').click();

      cy.contains('attendees').should('exist');
    });
  });

  describe('Detail Page – Admin View', () => {
    it('should display Delete button for admin users', () => {
      cy.loginAsAdmin();
      cy.get('.list').should('exist');
      cy.contains('Detail').click();


      cy.contains('button', 'Delete').should('be.visible');
    });
  });
  describe('Admin Delete Session', () => {
    it('should allow admin to delete a session and redirect', () => {
      cy.loginAsAdmin();
      cy.get('.list').should('exist');
      cy.contains('Detail').click();

      cy.contains('button', 'Delete').click();


      cy.contains('Session deleted !').should('exist');

      cy.url().should('include', '/sessions');
    });
  });





});

describe("Create Session", () => {
  it('should create a new session successfully', () => {

    cy.loginAsAdmin();
    cy.contains('Create').click();
    cy.url().should('include','/sessions/create');

    cy.get('input[formControlName="name"]').type('session yoga 1');
    cy.get('input[formControlName="date"]').type('2025-05-23');
    cy.get('mat-select[formControlName="teacher_id"]').click();
    cy.contains('Margot DELAHAYE').click();
    cy.get('textarea[formControlName="description"]').type("Test de session");

    cy.contains('Save').click();

    cy.url().should('include','/sessions');
    cy.contains('yoga 1').should('exist');
  });

  it('should not create a new session if teacher is missing ', () => {
    cy.loginAsAdmin()
    cy.contains('Create').click();
    cy.url().should('include','/sessions/create');

    cy.get('input[formControlName="name"]').type('session yoga 1');
    cy.get('input[formControlName="date"]').type('2025-05-23');
    cy.get('mat-select[formControlName="teacher_id"]').should('be.visible');
    cy.get('textarea[formControlName="description"]').type("Test de session invalide");
    cy.get('button[type=submit]').should('be.visible');

    cy.get('button[type=submit]').should('be.disabled');
  })
});

