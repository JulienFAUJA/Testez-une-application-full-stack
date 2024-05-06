describe('User Infos', () => {
  it('Login', () => {
      // Visite la page de connexion
      cy.visit('/login');

      cy.intercept('POST', '/api/auth/login', {
          body: {
              admin: true,
              created_at: [2024,4,23,14,41,54],
              email: 'yoga@studio.com',
              firstName: 'firstName',
              id: 1,
              lastName: 'lastName',
              updatedAt: [2024,4,23,14,41,54]
          },
      }).as('login')

      cy.intercept('GET', '/api/session', {
          statusCode: 200,
          body: {
              admin: true,
              created_at: [2024,4,23,14,41,54],
              email: 'yoga@studio.com',
              firstName: 'firstName',
              id: 1,
              lastName: 'lastName',
              updatedAt: [2024,4,23,14,41,54]
          },
      }).as('session')

      cy.intercept('GET', '/api/user/1',{
          statusCode: 200,
          body: {
              admin: true,
              created_at: [2024,4,23,14,41,54],
              email: 'yoga@studio.com',
              firstName: 'firstName',
              id: 1,
              lastName: 'lastName',
              updatedAt: [2024,4,23,14,41,54]
          },
      }).as('getUser');



      // connexion
      cy.get('input[formControlName=email]').type("yoga@studio.com")
      cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)
  
      // Vérifie la redirection vers sessions
      cy.url().should('include', '/sessions');

      cy.contains('Account').click();

      cy.wait('@getUser').then((interception) => {
          // Vérifier que la requête a été interceptée
          expect(interception.response.statusCode).to.eq(200);
          expect(interception.response.body).to.deep.equal({
              admin: true,
              email: 'yoga@studio.com',
              firstName: 'firstName',
              id: 1,
              lastName: 'lastName',
              created_at: [2024,4,23,14,41,54],
              updatedAt: [2024,4,23,14,41,54]
          });
      });

      cy.contains('yoga@studio.com').should('be.visible');

      // Vérifie la redirection vers "Me"
      cy.url().should('include', '/me');
  
  });
});
