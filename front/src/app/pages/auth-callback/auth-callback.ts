import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { authenticate, WebException } from '../../../client';

@Component({
  selector: 'app-auth-callback',
  template: `
    <div class="flex items-center justify-center min-h-screen">
      <div class="text-center">
        <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-500 mx-auto mb-4"></div>
        <p class="text-lg">Processing authentication...</p>
        @if (error) {
          <p class="text-sm text-gray-500 mt-2">{{ error }}</p>
        }
      </div>
    </div>
  `,
  standalone: true,
  imports: []
})
export class AuthCallback implements OnInit {
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  
  public error: string | null = null;

  async ngOnInit() {
    const code = this.route.snapshot.queryParams['code'];

    if (!code) {
      this.error = 'No authorization code received. Redirecting to login...';
      setTimeout(() => this.router.navigate(['/login']), 3000);
      return;
    }

    const { data, error } = await this.exchangeCodeForToken(code);
    if (error) {
      this.handleError(error);
      return;
    }
    if (data && data.session) {
      console.log(data)
      this.handleSuccess(data.session);
    } else {
      this.error = 'No token received. Redirecting to login...';
      console.warn('No session data received:', data);
      setTimeout(() => this.router.navigate(['/login']), 3000);
    }
  }

  private exchangeCodeForToken(code: string) {
    return authenticate({ path: { code } })
  }

  private handleError(error: WebException) {
    console.error('Authentication error:', error);
    this.error = 'An error occurred during authentication. Redirecting to login...';
    setTimeout(() => this.router.navigate(['/login']), 3000);
  }

  private handleSuccess(token: string) {
    window.localStorage.setItem('authToken', token);
    this.router.navigate(['/']);
  }
}
