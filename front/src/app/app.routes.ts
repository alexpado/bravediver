import { Routes } from '@angular/router';
import { loginGuard } from './guards/login.guard';

export const routes: Routes = [
    {
        path: 'login',
        loadComponent: () => import('./pages/login/login').then(m => m.Login),
        canActivate: [loginGuard]
    },
    {
        path: 'auth/callback',
        loadComponent: () => import('./pages/auth-callback/auth-callback').then(m => m.AuthCallback)
    }
];
