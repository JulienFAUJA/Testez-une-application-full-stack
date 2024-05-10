

describe('Edit session spec', () => {
  const user = {
    id: 1,
    username: 'yoga@studio.com',
    firstName: 'Admin',
    lastName: 'Admin',
    admin: true,
  };
  const sessions = [
    {
      id: 1,
      name: 'première session',
      date: 1711200995547,
      teacher_id: 1,
      description: 'première session de yoga ',
      users: [],
      createdAt: [2024, 5, 6, 11, 26, 20],
      updatedAt: [2024, 5, 6, 11, 26, 20],
    },
    {
      id: 2,
      name: 'deuxième session',
      date: 1711200995547,
      teacher_id: 2,
      description: 'deuxième session de yoga',
      users: [],
      createdAt: [2024, 5, 6, 11, 28, 55],
      updatedAt: [2024, 5, 6, 11, 28, 55],
    },
    {
      id: 3,
      name: 'première session (mise à jour)',
      date: 1711200995547,
      teacher_id: 1,
      description: 'première session de yoga ',
      users: [],
      createdAt: [2024, 5, 6, 11, 26, 20],
      updatedAt: [2024, 5, 6, 11, 26, 20],
    },
  ];

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

  beforeEach(() => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: user,
    });

    cy.intercept('GET', '/api/session', {
      satusCode: 200,
      body: [sessions[0], sessions[1]],
    }).as('sessions');

    cy.intercept('GET', '/api/session/1', {
      satusCode: 200,
      body: sessions[0],
    }).as('session');

    cy.intercept('GET', '/api/teacher', {
      statusCode: 200,
      body: teachers,
    }).as('getTeachers');

    cy.intercept('PUT', '/api/session/1', {
      statusCode: 200,
      body: sessions[2],
    }).as('editSession');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(
      `${'test!1234'}{enter}{enter}`
    );

    cy.url().should('include', '/sessions');
  });

  it('Edit successfully', () => {
    cy.contains('Rentals available').should('be.visible');

    cy.contains('Edit').should('be.visible').click();

    cy.url().should('include', '/sessions/update/1');

    cy.wait('@getTeachers').then((interception) => {
      // Vérifier que la requête a été interceptée avec succès
      expect(interception.response.statusCode).to.eq(200);
      expect(interception.response.body).to.deep.equal(teachers);
    });

    cy.get('input[formControlName=name]').clear().type(sessions[2].name);

    cy.get('form').submit();

    cy.wait('@editSession').then((interception) => {
      // Vérifier que la requête a été interceptée avec succès
      expect(interception.response.statusCode).to.eq(200);
      expect(interception.response.body).to.deep.equal(sessions[2]);
    });

    cy.contains('Session updated !').should('be.visible');
    cy.contains('Close').should('be.visible').click();

    cy.url().should('include', '/sessions');
  });

  it('Edit fail', () => {
    cy.contains('Rentals available').should('be.visible');

    cy.contains('Edit').should('be.visible').click();

    cy.url().should('include', '/sessions/update/1');

    cy.wait('@getTeachers').then((interception) => {
      // Vérifier que la requête a été interceptée avec succès
      expect(interception.response.statusCode).to.eq(200);
      expect(interception.response.body).to.deep.equal(teachers);
    });

    cy.get('input[formControlName=name]').clear();

    cy.get('button[type=submit]').should('be.disabled');

    cy.url().should('include', '/sessions');
  });
});
