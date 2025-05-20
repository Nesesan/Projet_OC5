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
describe('Lists shown', () => {
  it('Affichage liste des sessions', () => {
    cy.loginAsAdmin();
    cy.contains('Submit').click()
    cy.get('.list').should('exist')
  })

  it('User Infos Shown',  () => {
    cy.visit('http://localhost:4200/')
    cy.contains('Login').click()
    cy.get('#mat-input-0').type('yoga@studio.com');
    cy.get('#mat-input-1').type('test!1234')
    cy.contains('Submit').click()
    cy.contains('Account').click()
    cy.contains('Name').should('exist')
    cy.contains('Email').should('exist')
    cy.contains('Create at:').should('exist')
    cy.contains('Last update:').should('exist')
  })

  it ('Session Infos Shown' , () => {
    cy.visit('http://localhost:4200/')
    cy.contains('Login').click()
    cy.get('#mat-input-0').type('yoga@studio.com')
    cy.get('#mat-input-1').type('test!1234')
    cy.contains('Submit').click()
    cy.contains('Sessions').click()
    cy.get('.list').should('exist')
  })

  it('Delete button show if AdminUser logged', () => {
    cy.loginAsAdmin();
    cy.contains('Detail').click()
    cy.contains('Delete').should('exist')
  });

  it('Delete button disabled as User logged', () => {
    cy.loginAsUser();
    cy.contains('Detail').click()
    cy.contains('Delete').should('not.exist')
  });

  it('Details button shown if AdminUser logged', () => {
    cy.loginAsAdmin();
    cy.contains('Detail').should('exist')

  })

  it('Create button shown if AdminUser logged', () => {
    cy.loginAsAdmin();
    cy.contains('Create').should('exist')


  })

  it('Details button shown if User logged', () => {
    cy.loginAsUser();
    cy.contains('Detail').should('exist')


  })
  it('Create button disabled if User logged', () => {
    cy.loginAsUser();
    cy.contains('Create').should('not.exist')

  })

  it('Logout button shown if User / Admin User logged', () => {
    cy.loginAsUser();
    cy.contains('Logout').click()
    cy.contains('Login').should('exist')
  })

  it ('Check if session is not able without info ' , () => {
    cy.loginAsAdmin();
    cy.contains('Create').click()
    cy.contains('Save').should('exist')
    cy.get('button.mat-focus-indicator.mat-raised-button.mat-button-base.mat-primary.mat-button-disabled').should('exist')
    cy.get('button.mat-focus-indicator.mat-raised-button.mat-button-base.mat-primary.mat-button-disabled').should('be.disabled')

  })


  it ('Show differents infos as Creating session', () => {
    cy.loginAsAdmin();
    cy.contains('Create').click()
    cy.contains('Name')



  })

})
