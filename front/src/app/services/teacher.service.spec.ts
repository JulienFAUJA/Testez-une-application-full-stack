import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { TeacherService } from './teacher.service';
import { Teacher } from '../interfaces/teacher.interface';

describe('TeacherService', () => {
  let service: TeacherService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(TeacherService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch the teacher detail', () => {
    // Given : une enseignante mockée
    const mockTeacher: Teacher = {
      id: 2,
      lastName: 'THIERCELIN',
      firstName: 'Hélène',
      createdAt: new Date('2022-01-01T00:00:00'),
      updatedAt: new Date('2022-01-01T00:00:00'),
    };
    const id = '2';

    // When
    service.detail(id).subscribe((teacher: Teacher) => {
      // Then
      expect(teacher).toEqual(mockTeacher);
    });
  });
});
