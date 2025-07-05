import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClientService } from '../../services/HttpClientService';

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
  private httpClient = inject(HttpClientService);
  
  public error: string | null = null;

  ngOnInit() {
    const code = this.route.snapshot.queryParams['code'];
    const error = this.route.snapshot.queryParams['error'];

    if (error) {
      this.error = 'Authentication failed. Redirecting to login...';
      setTimeout(() => this.router.navigate(['/login']), 3000);
      return;
    }

    if (!code) {
      this.error = 'No authorization code received. Redirecting to login...';
      setTimeout(() => this.router.navigate(['/login']), 3000);
      return;
    }

    // TODO: Send the code to your backend to exchange for tokens
    this.exchangeCodeForToken(code);
  }

  private exchangeCodeForToken(code: string) {
    this.httpClient.fetch(`/api/v1/auth/${code}`)
  }
}
