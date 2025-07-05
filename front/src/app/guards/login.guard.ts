import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { CanActivateFn } from '@angular/router';

export const loginGuard: CanActivateFn = () => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  
  if (token) {
    // User is already logged in, redirect to home
    router.navigate(['/']);
    return false;
  }
  
  return true; // Allow access to login page
};
