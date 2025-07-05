import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card', 
  template: `
  <section [class]="cardClasses">
    <ng-content></ng-content>
  </section>
  `,
  styleUrl: './card.scss',
  standalone: true,
})
export class Card {
  @Input() class = ''; // Renamed to avoid confusion with native 'class'
  @Input() variant: 'hoverable' | 'default' = 'default';

  protected get cardClasses(): string {
    return `card ${this.class} ${this.variant}`;
  }
}
