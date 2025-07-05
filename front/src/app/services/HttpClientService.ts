import { inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { $Fetch, createFetch } from "ofetch"

@Injectable({
  providedIn: 'root'
})
export class HttpClientService {
    private _router = inject(Router);
    
    constructor() { 
        this.fetch = createFetch({
              defaults: {
                onRequest: ({ options }) => {
                    // Add authorization header if token exists
                    const token = localStorage.getItem('token');
                    if (token) {
                        options.headers = new Headers({
                            ...Object.fromEntries(new Headers(options.headers).entries()),
                            'Authorization': `Bearer ${token}`
                        });
                    }
                },
                onResponseError: ({ response }) => {
                    if (response.status === 401) {
                        localStorage.removeItem('token');
                        this._router.navigate(['/login']);
                    }
                }
              }
        })
    }

    public fetch: $Fetch
    
    public get isAuthenticated(): boolean {
        return !!localStorage.getItem('token');
    }
}
