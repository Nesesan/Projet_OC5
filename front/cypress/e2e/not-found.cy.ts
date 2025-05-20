describe('NotFoundComponent', () => {
  it('should display the "Page not found!" message', () => {

    cy.visit('/some-non-existent-page');

    cy.get('h1').should('contain', 'Page not found !');
  });
});
