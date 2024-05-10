describe('Delete sessesion spec', () => {
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
          description: 'première session de yoga',
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
      ];
  
      const teacher = {
        id: 1,
        lastName: 'DELAHAYE',
        firstName: 'Margot',
        createdAt: [2024, 4, 23, 14, 41, 51],
        updatedAt: [2024, 4, 23, 14, 41, 51],
      };
  
  it('Delete succeed', () => {
   
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {
      body: user,
    });

    cy.intercept('GET', '/api/session', {
      satusCode: 200,
      body: sessions,
    }).as('sessions');

    cy.intercept('GET', '/api/session/1', {
      satusCode: 200,
      body: sessions[0],
    }).as('session');

    cy.intercept('GET', '/api/teacher/1', {
      statusCode: 200,
      body: teacher,
    }).as('getTeacher');

    cy.intercept('DELETE', '/api/session/1', {
      satusCode: 200,
      body: sessions[0],
    }).as('deleteSession');

    cy.get('input[formControlName=email]').type('yoga@studio.com');
    cy.get('input[formControlName=password]').type(`${'test!1234'}{enter}{enter}`
    );

    cy.url().should('include', '/sessions');

    cy.contains('Rentals available').should('be.visible');

    cy.contains('Detail').should('be.visible').click();

    cy.wait('@session').then((interception) => {
      // Vérifier que la requête a été interceptée avec succès
      expect(interception.response.statusCode).to.eq(200);
      expect(interception.response.body).to.deep.equal(sessions[0]);
    });

    cy.wait('@getTeacher').then((interception) => {
      // Vérifier que la requête a été interceptée avec succès
      expect(interception.response.statusCode).to.eq(200);
      expect(interception.response.body).to.deep.equal(teacher);
    });

    cy.url().should('include', '/sessions/detail/1');

    cy.contains('Delete').should('be.visible').click();

    cy.wait('@deleteSession').then((interception) => {
      // Vérifier que la requête a été interceptée avec succès
      expect(interception.response.statusCode).to.eq(200);
      expect(interception.response.body).to.deep.equal(sessions[0]);
    });

    cy.contains('Session deleted !').should('be.visible');
    cy.contains('Close').should('be.visible').click();
  });
});
