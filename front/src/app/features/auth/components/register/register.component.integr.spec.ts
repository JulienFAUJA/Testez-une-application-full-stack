import { TestBed } from '@angular/core/testing';
import { RegisterComponent } from './register.component';
import { FormBuilder } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';

// Début tests intégration
describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let authService: AuthService;
  let router: Router;

  // Configuration pour tous
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        RegisterComponent,
        FormBuilder,
        {
          provide: AuthService,
          useValue: { register: jest.fn() },
        },
        {
          provide: Router,
          useValue: { navigate: jest.fn() },
        },
      ],
    });

    component = TestBed.inject(RegisterComponent);
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
  });

  // Test de la méthode submit()
  describe('submit', () => {
    // Test AuthService.register
    it('should call AuthService.register with form values when form is submitted', () => {
      // Given : Initialisation des données
      const formValues = {
        email: 'julienfaujanet@email.com',
        firstName: 'Julien',
        lastName: 'Faujanet',
        password: 'ju_lien_*@v 4+',
      };
      component.form.setValue(formValues);
      jest.spyOn(authService, 'register').mockReturnValue(of(undefined)); // Espionnage de la méthode register

      // When : Appel méthode à tester
      component.submit();

      // Then : Vérification méthode register du AuthService a été appelée
      expect(authService.register).toHaveBeenCalledWith(formValues);
    });

    // Vérifier qu'onError est à true
    it('should set onError to true when AuthService.register returns an error', () => {
      // Given : Initialisation données
      const formValues = {
        email: 'julienfaujanet@email.com',
        firstName: 'Julien',
        lastName: 'Faujanet',
        password: 'ju_lien_*@v 4+',
      };
      component.form.setValue(formValues);
      jest.spyOn(authService, 'register').mockImplementation(() => {
        return new Observable<void>((subscriber) => {
          subscriber.error(new Error('error'));
        });
      });

      // When : Appel méthode à tester
      component.submit();

      // Then : Vérification qu'onError est à true
      expect(component.onError).toBe(true);
    });
  });
});
