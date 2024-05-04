describe('User Information Page (/me)', () => {
  const loginUrl = '/login';
  const sessionUrl = '/sessions';

  const login = () => {
    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type('test!1234{enter}{enter}');
  };

  const connectAsAdmin = () => {
    cy.visit(loginUrl);
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        firstName: 'Admin',
        lastName: 'Admin',
        email: 'yoga@studio.com',
        admin: true,
        createdAt: '2024-04-23',
        updatedAt: '2024-04-23',
      },
    }).as('loginRequest');
    login();
    cy.url().should('include', sessionUrl);
  };

  const connectAsNotAdmin = () => {
    cy.visit(loginUrl);
    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        firstName: 'Admin',
        lastName: 'Admin',
        email: 'yoga@studio.com',
        admin: false,
        createdAt: '2024-04-23',
        updatedAt: '2024-04-23',
      },
    }).as('loginRequest');
    login();
    cy.url().should('include', sessionUrl);
  };

  const checkUserDataDisplay = (isAdmin) => {
    cy.get('.mat-card-content').within(() => {
      cy.contains('Name:').should('contain.text', 'Admin ADMIN');
      cy.contains('Email:').should('contain.text', 'yoga@studio.com');
      cy.contains('Create at:').should('contain.text', 'April 23, 2024');
      cy.contains('Last update:').should('contain.text', 'April 23, 2024');

      if (isAdmin) {
        cy.contains('You are admin').should('exist');
        cy.contains('Delete my account:').should('not.exist');
        cy.contains('button.mat-raised-button.mat-warn').should('not.exist');
      } else {
        cy.contains('You are admin').should('not.exist');
        cy.contains('Delete my account:').should('exist');
        cy.contains('button.mat-raised-button.mat-warn').should('exist');
      }
    });
  };

  it('should display user information correctly for admin', () => {
    connectAsAdmin();
    cy.contains('span.link[routerlink="me"]', 'Account').click();
    checkUserDataDisplay(true);
  });

  it('should display user information correctly for non-admin', () => {
    connectAsNotAdmin();
    cy.contains('span.link[routerlink="me"]', 'Account').click();
    checkUserDataDisplay(false);
  });
});
