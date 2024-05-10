
describe('Session spec', () => {
    const newSession1 = {
        id: 1,
        name: 'première session de yoga',
        description:  'première session de yoga',
        date: new Date(),
        teacher_id: 1,
        users: [],
        createdAt: new Date(),
        updatedAt: new Date(),
      };
      
    const teachers = [
        {
          id: 1,
          lastName: 'DELAHAYE',
          firstName: 'Margot',
          createdAt: [2024, 4, 23, 14, 41, 51],
          updatedAt: [2024, 4, 23, 14, 41, 51],
        },
        {
          id: 2,
          lastName: 'THIERCELIN',
          firstName: 'Hélène',
          createdAt: [2024, 0, 0, 0, 0, 0],
          updatedAt: [2024, 0, 0, 0, 0, 0],
        },
      ];
  const teacher_name = `${teachers[0].firstName} ${teachers[0].lastName}`;

  it('Login successfull', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: {
        id: 1,
        username: 'userName',
        firstName: 'firstName',
        lastName: 'lastName',
        admin: true,
      },
    })

    cy.intercept(
        {
          method: 'GET',
          url: '/api/session',
        },
        []).as('session')

 
    cy.get('input[formControlName=email]').type('yoga@studio.com')
    cy.get('input[formControlName=password]').type(`${'test!1234'}{enter}{enter}`)

    cy.url().should('include', '/sessions')

  })

  it('Session create failed', () => {
  
  cy.intercept('GET', '/api/teacher', {
    statusCode: 200,
    body: teachers,
  })

  cy.intercept('POST', '/api/session', {
    status: 200,

  })

  cy.intercept(
    {
      method: 'GET',
      url: '/api/session',
    },
    [
      {
        id: 1,
        name: newSession1.name,
        date: new Date(),
        teacher_id:  newSession1.teacher_id,
        description:  newSession1.description,
        createdAt: new Date(),
        updatedAt: new Date()
      }
    ]).as('session')

    cy.contains('Rentals available').should('be.visible');

  cy.get('.mat-raised-button').eq(0).click();

  cy.url().should('include','/create');


        cy.get('input[formControlName=name]').type(newSession1.name);
        cy.get('input[formControlName=date]').type(newSession1.date.toISOString().split('T')[0]);
        cy.get('textarea[formControlName=description]').type(newSession1.description);
        
        
        cy.get('button[type=submit]').should('be.disabled');
        
    });

    it('Session created', () => {
  
        cy.intercept('GET', '/api/teacher', {
          statusCode: 200,
          body: teachers,
        })
      
        cy.intercept('POST', '/api/session', {
          status: 200,
      
        })
      
        cy.intercept(
          {
            method: 'GET',
            url: '/api/session',
          },
          [
            {
              id: 1,
              name: newSession1.name,
              date: new Date(),
              teacher_id:  newSession1.teacher_id,
              description:  newSession1.description,
              createdAt: new Date(),
              updatedAt: new Date()
            }
          ]).as('session')
      
         
      
        cy.url().should('include','/create');
      
      
              cy.get('input[formControlName=name]').type(newSession1.name);
              cy.get('input[formControlName=date]').type(newSession1.date.toISOString().split('T')[0]);
              cy.get('textarea[formControlName=description]').type(newSession1.description);
              
              cy.get('mat-select[formControlName=teacher_id]').click().then(() => {
                  cy.get(`.cdk-overlay-container .mat-select-panel .mat-option-text`).should('contain', teacher_name);
                  cy.get(`.cdk-overlay-container .mat-select-panel .mat-option-text:contains(${teacher_name})`).first().click().then(() => {
                    cy.get(`[formcontrolname=teacher_id]`).contains(teacher_name);})
                })
              
              
              
              cy.get('button[type=submit]').click()
              cy.url().should('include','/session');
          })

 
});
