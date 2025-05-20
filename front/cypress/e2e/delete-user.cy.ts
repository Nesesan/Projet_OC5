describe('User flow to /me page', () => {
  const user = {
    firstName: 'User',
    lastName: 'One',
    email: 'user1@mail.com',
    admin: false,
    createdAt: '2023-01-01T12:00:00Z',
    updatedAt: '2024-04-01T12:00:00Z'
  };

  beforeEach(() => {
    cy.intercept('GET', '/api/session', {
      statusCode: 200,
      body: {
        userId: 1,
        username: 'user.one',
        isAuthenticated: true,
      },
    }).as('getSession');

    cy.intercept('GET', '/api/user/1', {
      statusCode: 200,
      body: {
        id: 1,
        firstName: user.firstName,
        lastName: user.lastName,
        email: user.email,
        admin: user.admin,
        createdAt: user.createdAt,
        updatedAt: user.updatedAt,
      },
    }).as('getUser');

    cy.intercept('DELETE', '/api/user/1', {
      statusCode: 200,
    }).as('deleteUser');
  });

  it('should login, navigate to /me, display user info correctly and allow account deletion', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      statusCode: 200,
      body: {
        id: 1,
        username: 'user.one',
        firstName: user.firstName,
        lastName: user.lastName,
        admin: user.admin,
      },
    }).as('loginRequest');

    cy.get('input[formControlName=email]').type(user.email);
    cy.get('input[formControlName=password]').type('password!');
    cy.get('form').submit();

    cy.wait('@loginRequest');
    cy.url().should('include', '/sessions');

    cy.contains('Account').should('be.visible').click();
    cy.url().should('include', '/me');


    cy.wait('@getUser');

    cy.get('mat-card-content').should('be.visible');

    cy.get('mat-card-title').contains('User information').should('be.visible');

    cy.get('mat-card-content').within(() => {

      cy.contains(`Name: ${user.firstName} ${user.lastName.toUpperCase()}`);

      cy.contains(`Email: ${user.email}`);

      cy.contains('Delete my account:');

      cy.contains('Create at:');

      cy.contains('Last update:');

      cy.get('p.my2').should('not.exist');
    });

    cy.contains('Delete my account:').next('button').click();


    cy.wait('@deleteUser');

    cy.get('.mat-snack-bar-container').should('contain', 'Your account has been deleted !');

    cy.url().should('include', '/');
  });
});

