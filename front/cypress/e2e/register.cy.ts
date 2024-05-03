/// <reference types="Cypress" />

describe('Register spec', () => {
    // Given
    // Définition user infos pour test
    const user = {
      id: 1,
      firstName: 'Julien',
      lastName: 'Faujanet',
      email: 'julienfaujanet@email.com',
      password: 'ju_lien_*@v 4+',
    };
  
    // Avant chaque test, visite de la register page
    beforeEach(() => {
      cy.visit('/register');
    });
  
    it('should display an error if required email field not filled', () => {
      // When
      // Remplissage form sans l'email & submit
      cy.get('input[formControlName=firstName]').type(user.firstName)
      cy.get('input[formControlName=lastName]').type(user.lastName)
      cy.get('input[formControlName="email"]').type(`{enter}`);
      cy.get('input[formControlName=password]').type(user.password)
  
      // Then
      // Vérification champ email = invalid
      cy.get('input[formControlName="email"]').should('have.class', 'ng-invalid');
  
      // Vérification bouton soumission = disabled
      cy.get('button[type=submit]').should('be.disabled')
    });
  
    it('should display an error if the register service returns an error', () => {
      // Given
      // Intercept POST request  & simulation d'une error response
      cy.intercept('POST', '/api/auth/register', {
        statusCode: 500,
        body: { error: 'An error occurred' },
      }).as('register')
    
      // When
      // Remplissage form &  submit
      cy.get('input[formControlName=firstName]').type(user.firstName)
      cy.get('input[formControlName=lastName]').type(user.lastName)
      cy.get('input[formControlName="email"]').type(user.email)
      cy.get('input[formControlName=password]').type(user.password)
      cy.get('button[type=submit]').click()
    
      // Then
      // Vérification message "An error occurred"
      cy.get('span.error.ml2.ng-star-inserted').should('contain.text', 'An error occurred');
    });
  
    it('should display an error if the required password field is to short', () => {
      // When
      // Remplissage form sans password & submit
      cy.get('input[formControlName=firstName]').type(user.firstName)
      cy.get('input[formControlName=lastName]').type(user.lastName)
      cy.get('input[formControlName="email"]').type(user.email)
      cy.get('input[formControlName=password]').type("12")
      cy.get('button[type=submit]').click()
  
      // Then
      // Vérification message "An error occurred"
      cy.get('span.error.ml2.ng-star-inserted').should('contain.text', 'An error occurred');
    });
  
    it('should register, log in, verify user account details and delete it', () => {
      // Given
      // Interception requête d'inscription & simulation d'une success response
      cy.intercept('POST', '/api/auth/register', {
        statusCode: 201,
        body: user,
      }).as('register')
  
      // When
      // Remplissage form & submit
      cy.get('input[formControlName=firstName]').type(user.firstName)
      cy.get('input[formControlName=lastName]').type(user.lastName)
      cy.get('input[formControlName=email]').type(user.email)
      cy.get('input[formControlName=password]').type(user.password)
      cy.get('button[type=submit]').click()
  
      // Then
      // Vérification url change pour login page 
      cy.url().should('include', '/login')
  
      // Given
      // Interception requête connexion & simulation d'une success response
      cy.intercept('POST', '/api/auth/login', {
        statusCode: 201,
        body: user,
      }).as('login')
  
      // Interception requête de session & simulation d'une success response
      cy.intercept('GET', '/api/session', {
        statusCode: 200,
        body: user,
      }).as('getSession')
  
      // When
      // Remplissage login form & submit
      cy.get('input[formControlName=email]').type(user.email)
      cy.get('input[formControlName=password]').type(`${user.password}{enter}{enter}`)
  
      // Interception user request & simulation d'une success réponse
      cy.intercept('GET', `/api/user/${user.id}`, {
        statusCode: 200,
        body: user,
      }).as('getUser')
  
      // Clic lien vers user page
      cy.get('span[routerLink=me]').click();
  
      // Then
      // Vérification url change pour user page 
      cy.url().should('include', '/me');
  
      // Vérification user details sont corrects
      cy.get('.m3 mat-card-content p').contains(`Name: ${user.firstName} ${user.lastName.toUpperCase()}`).should('exist');
      cy.get('.m3 mat-card-content p').contains(`Email: ${user.email}`).should('exist');
      cy.get('.m3 mat-card-content div.my2').should('exist');
  
      // Given
      // Interception  DELETE request & simulation d'une success réponse
      cy.intercept('DELETE', `/api/user/${user.id}`, {
        statusCode: 200,
      }).as('deleteUser')
  
      // When
      // Clic bouton suppression
      cy.get('.my2 > .mat-focus-indicator').click();
  
      // Then
      // Vérification appel de DELETE request
      cy.wait('@deleteUser').its('response.statusCode').should('eq', 200);
  
      // Vérification que le user est redirigé vers la login page après suppression
      cy.url().should('eq', 'http://localhost:4200/');
    });
  })