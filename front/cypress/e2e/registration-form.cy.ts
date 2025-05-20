
describe('Registration Form', () => {
  const user = {
    firstName: 'User',
    lastName: 'One',
    email: 'user1@mail.com',
    password: 'password!',
  };

  beforeEach(() => {
    cy.visit('/register');
  });

  it('should display the registration form', () => {

    cy.get('form.register-form').should('be.visible');


    cy.get('input[formControlName="firstName"]').should('be.visible');
    cy.get('input[formControlName="lastName"]').should('be.visible');
    cy.get('input[formControlName="email"]').should('be.visible');
    cy.get('input[formControlName="password"]').should('be.visible');


    cy.get('button[type="submit"]').should('be.visible').and('be.disabled');
  });

  it('should enable the submit button when the form is valid', () => {

    cy.get('input[formControlName="firstName"]').type(user.firstName);
    cy.get('input[formControlName="lastName"]').type(user.lastName);
    cy.get('input[formControlName="email"]').type(user.email);
    cy.get('input[formControlName="password"]').type(user.password);


    cy.get('button[type="submit"]').should('be.enabled');
  });

  it('should submit the form when it is valid and redirect to login', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 200,
    }).as('registerRequest');


    cy.get('input[formControlName="firstName"]').type(user.firstName);
    cy.get('input[formControlName="lastName"]').type(user.lastName);
    cy.get('input[formControlName="email"]').type(user.email);
    cy.get('input[formControlName="password"]').type(user.password);


    cy.get('button[type="submit"]').click();


    cy.wait('@registerRequest');
    cy.url().should('include', '/login');
  });

  it('should show an error message when the form submission fails', () => {
    cy.intercept('POST', '/api/auth/register', {
      statusCode: 500,
      body: { success: false, message: 'An error occurred' },
    }).as('registerErrorRequest');


    cy.get('input[formControlName="firstName"]').type(user.firstName);
    cy.get('input[formControlName="lastName"]').type(user.lastName);
    cy.get('input[formControlName="email"]').type(user.email);
    cy.get('input[formControlName="password"]').type(user.password);


    cy.get('button[type="submit"]').click();


    cy.wait('@registerErrorRequest');
    cy.get('.error').should('contain', 'An error occurred');
  });


  it('should not submit the form if it is invalid', () => {

    cy.get('button[type="submit"]').should('be.disabled');

    cy.get('input[formControlName="firstName"]').type(user.firstName);
    cy.get('button[type="submit"]').should('be.disabled');

    cy.get('input[formControlName="email"]').type('invalid-email');
    cy.get('button[type="submit"]').should('be.disabled');

    cy.get('input[formControlName="email"]').clear()
    cy.get('input[formControlName="email"]').type(user.email);
    cy.get('button[type="submit"]').should('be.disabled'); // toujours désactivé, car lastName + password manquent

    cy.get('input[formControlName="lastName"]').type(user.lastName);
    cy.get('input[formControlName="password"]').type(user.password);

    cy.get('button[type="submit"]').should('be.enabled');

    cy.get('button[type="submit"]').click();


  });

});
