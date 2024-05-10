import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { NgZone } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionApiService } from '../../services/session-api.service';
import { SessionService } from '../../../../services/session.service';
import { DetailComponent } from './detail.component';

// Début de la suite de tests pour DetailComponent
describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;
  let service: SessionService;
  let serviceApi: SessionApiService;
  let httpTestingController: HttpTestingController;
  
  let route: ActivatedRoute;
  let ngZone: NgZone;
  let router: Router;

  // Mock SessionService
  const mockSessionService = {
    sessionInformation: {
      admin: true,
      id: 1,
    },
  };

  // Config avant chacun des tests,
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([
          { path: 'sessions', component: DetailComponent },
        ]),
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule,
        HttpClientTestingModule,
      ],
      declarations: [DetailComponent],
      providers: [
        {
          provide: SessionService,
          useValue: mockSessionService,
        },
        SessionApiService,
      ],
    }).compileComponents();
    service = TestBed.inject(SessionService);
    serviceApi = TestBed.inject(SessionApiService);
    fixture = TestBed.createComponent(DetailComponent);
    router = TestBed.inject(Router);
    route = TestBed.inject(ActivatedRoute);
    ngZone = TestBed.inject(NgZone);
    httpTestingController = TestBed.inject(HttpTestingController);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  // Test vérif que le composant est bien créé
  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Test vérif que ngOnInit call fetchSession
  it('should call ngOnInit', () => {
    // Given
    const mockPrivateFetchSession = jest.spyOn(
      component as any,
      'fetchSession'
    );

    // When
    component.ngOnInit();

    // Then
    expect(mockPrivateFetchSession).toBeCalled();
  });

  // Test vérif appel window.history.back
  it('should call back', () => {
    // Given
    jest.spyOn(window.history, 'back');

    // When
    component.back();

    // Then
    expect(window.history.back).toBeCalled();
  });

  // Test vérif que méthode delete envoie request DELETE
  it('should call delete', () => {
    // Given
    const navigateSpy = jest.spyOn(router, 'navigate');
    component.sessionId = '1';

    // When
    expect(component.delete()).toBe(void 0);

    // Then
    const req = httpTestingController.expectOne('api/session/1');
    expect(req.request.method).toEqual('DELETE');
    req.flush(true);
  });

  // Test vérif méthode participate
  it('should participate', () => {
    const spyParticipate = jest.spyOn(component, 'participate');

    component.participate();

    expect(spyParticipate).toHaveBeenCalled();
  })

  it('should unParticipate', () => {
    const spyUnParticipate = jest.spyOn(component, 'unParticipate');

    component.unParticipate();

    expect(spyUnParticipate).toHaveBeenCalled();
  })
});
