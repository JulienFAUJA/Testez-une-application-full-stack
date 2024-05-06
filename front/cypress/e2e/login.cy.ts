describe('Login spec', () => {

  it('Login failed no password', () => {
    cy.visit('/login')


    cy.get('input[formControlName=email]').type(`${"yoga@studio.com"}{enter}{enter}`)

    cy.contains('An error occurred')

  })

  it('Login failed no email', () => {
    cy.visit('/login')


    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.contains('An error occurred')

  })

  it('Login successfull', () => {
    cy.visit('/login')

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true
      },
    })

    cy.intercept(
      {
        method: 'GET',
        url: '/api/session',
      },
      []).as('session')

    cy.get('input[formControlName=email]').type("yoga@studio.com")
    cy.get('input[formControlName=password]').type(`${"test!1234"}{enter}{enter}`)

    cy.url().should('include', '/sessions')
  })


});