import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ButtonComponent } from './button';

describe('ButtonComponent', () => {
  let component: ButtonComponent;
  let fixture: ComponentFixture<ButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ButtonComponent, RouterTestingModule]
    }).compileComponents();

    fixture = TestBed.createComponent(ButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should render as button by default', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('button')).toBeTruthy();
  });

  it('should render as router link when routerLink is provided', () => {
    component.routerLink = '/test';
    fixture.detectChanges();
    
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('a[ng-reflect-router-link="/test"]')).toBeTruthy();
  });

  it('should render as external link when href is provided', () => {
    component.href = 'https://example.com';
    fixture.detectChanges();
    
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('a[href="https://example.com"]')).toBeTruthy();
  });

  it('should apply correct CSS classes', () => {
    component.variant = 'outlined';
    component.color = 'democratic';
    component.size = 'large';
    component.disabled = true;
    
    const classes = component.cssClasses;
    expect(classes).toContain('app-button--outlined');
    expect(classes).toContain('app-button--democratic');
    expect(classes).toContain('app-button--large');
    expect(classes).toContain('app-button--disabled');
  });

  it('should show loading spinner when loading', () => {
    component.loading = true;
    fixture.detectChanges();
    
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.app-button__spinner')).toBeTruthy();
  });

  it('should disable button when disabled or loading', () => {
    component.disabled = true;
    expect(component.isDisabled).toBe(true);
    
    component.disabled = false;
    component.loading = true;
    expect(component.isDisabled).toBe(true);
  });

  it('should emit buttonClick event', () => {
    spyOn(component.buttonClick, 'emit');
    const mockEvent = new Event('click');
    
    component.onButtonClick(mockEvent);
    
    expect(component.buttonClick.emit).toHaveBeenCalledWith(mockEvent);
  });

  it('should prevent click when disabled', () => {
    component.disabled = true;
    spyOn(component.buttonClick, 'emit');
    const mockEvent = new Event('click');
    spyOn(mockEvent, 'preventDefault');
    spyOn(mockEvent, 'stopPropagation');
    
    component.onButtonClick(mockEvent);
    
    expect(mockEvent.preventDefault).toHaveBeenCalled();
    expect(mockEvent.stopPropagation).toHaveBeenCalled();
    expect(component.buttonClick.emit).not.toHaveBeenCalled();
  });
});
