import { Component, Input, HostBinding, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

export type ButtonVariant = 'filled' | 'outlined' | 'text';
export type ButtonColor = 'super-earth' | 'democratic' | 'alert-red' | 'tactic-green';
export type ButtonSize = 'small' | 'normal' | 'large';
export type IconPosition = 'left' | 'right';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './button.html',
  styleUrl: './button.scss', 
})
export class ButtonComponent {
  @Input() variant: ButtonVariant = 'filled';
  @Input() color: ButtonColor = 'super-earth';
  @Input() size: ButtonSize = 'normal';
  @Input() disabled = false;
  @Input() loading = false;
  @Input() icon?: string;
  @Input() iconPos: IconPosition = 'left';
  @Input() type: 'button' | 'submit' | 'reset' = 'button';
  
  // Navigation props
  @Input() href?: string;
  @Input() routerLink?: string | string[];
  @Input() target?: '_blank' | '_self' | '_parent' | '_top';
  
  @Output() buttonClick = new EventEmitter<Event>();

  @HostBinding('class') get cssClasses(): string {
    const classes = [
      'app-button',
      `app-button--${this.variant}`,
      `app-button--${this.color}`,
      `app-button--${this.size}`,
      this.disabled ? 'app-button--disabled' : '',
      this.loading ? 'app-button--loading' : ''
    ];
    
    return classes.filter(Boolean).join(' ');
  }

  @HostBinding('attr.disabled') get isDisabled(): boolean | null {
    return (this.disabled || this.loading) ? true : null;
  }

  get isExternalLink(): boolean {
    return !!this.href;
  }

  get isRouterLink(): boolean {
    return !!this.routerLink;
  }

  get isButton(): boolean {
    return !this.isExternalLink && !this.isRouterLink;
  }

  onButtonClick(event: Event): void {
    if (this.disabled || this.loading) {
      event.preventDefault();
      event.stopPropagation();
      return;
    }
    
    this.buttonClick.emit(event);
  }
}
