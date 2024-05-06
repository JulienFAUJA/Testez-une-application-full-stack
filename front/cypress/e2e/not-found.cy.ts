/// <reference types="Cypress" />
describe('NotFound spec', () => {
  it(`should display 'Page not found !' when page is not found`, () => {
    cy.visit('what-ever-you-want');

    cy.get('h1').should('have.text', 'Page not found !');
  });
});
