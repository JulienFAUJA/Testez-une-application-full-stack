import { TestBed } from '@angular/core/testing';
import { LoginComponent } from './login.component';
import { FormBuilder } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of, throwError } from 'rxjs';
import { SessionInformation } from 'src/app/interfaces/sessionInformation.interface';

// Composant de connexion
describe('LoginComponent', () => {
  let component: LoginComponent;
  let authService: AuthService;
  let router: Router;
  let sessionService: SessionService;

  // Avant chaque
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        LoginComponent,
        FormBuilder,
        { 
          provide: AuthService, 
          useValue: { login: jest.fn() } 
        },
        {
           provide: Router, 
           useValue: { navigate: jest.fn() } 
          },
        { 
          provide: SessionService,
          useValue: { logIn: jest.fn(), $isLogged: jest.fn() } 
        },
      ],
    });

    component = TestBed.inject(LoginComponent);
    authService = TestBed.inject(AuthService);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
  });

  // Connexion
  describe('submit', () => {
    it('should call AuthService.login with form values when form is submitted', () => {
      const formValues = {
        email: 'test@example.com',
        password: 'password',
      };
      component.form.setValue(formValues);
      jest.spyOn(authService, 'login').mockReturnValue(of({} as SessionInformation));

      component.submit();

      expect(authService.login).toHaveBeenCalledWith(formValues);
    });

    it('should call SessionService.logIn with AuthService.login response when form is submitted', () => {
      const formValues = {
        email: 'test@example.com',
        password: 'password',
      };
      const sessionInformation = {} as SessionInformation;
      component.form.setValue(formValues);
      jest.spyOn(authService, 'login').mockReturnValue(of(sessionInformation));
      const logInSpy = jest.spyOn(sessionService, 'logIn');

      component.submit();

      expect(logInSpy).toHaveBeenCalledWith(sessionInformation);
    });

    it('should call Router.navigate with \'/sessions\' when form is submitted', () => {
      const formValues = {
        email: 'test@example.com',
        password: 'password',
      };
      component.form.setValue(formValues);
      jest.spyOn(authService, 'login').mockReturnValue(of({} as SessionInformation));
      const navigateSpy = jest.spyOn(router, 'navigate');

      component.submit();

      expect(navigateSpy).toHaveBeenCalledWith(['/sessions']);
    });

    it('should set onError to true when AuthService.login returns an error', () => {
      const formValues = {
        email: 'test@example.com',
        password: 'password',
      };
      component.form.setValue(formValues);
      jest.spyOn(authService, 'login').mockReturnValue(throwError('error'));

      component.submit();

      expect(component.onError).toBe(true);
    });
  });
});
