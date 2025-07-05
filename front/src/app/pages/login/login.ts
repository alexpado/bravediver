import { Component } from '@angular/core';
import { Card } from "../../components/card/card";
import { ButtonComponent } from '../../components/button';

@Component({
  selector: 'app-login',
  imports: [Card, ButtonComponent],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {
  public discordClientId = import.meta.env.VITE_DISCORD_CLIENT_ID;
  
  public get discordLoginUrl(): string {
    const params = new URLSearchParams({
      client_id: this.discordClientId,
      redirect_uri: `${window.location.origin}/auth/callback`,
      response_type: 'code',
      scope: 'identify'
    });
    
    return `https://discord.com/api/oauth2/authorize?${params.toString()}`;
  }

  public loginWithDiscord(): void {
    window.location.href = this.discordLoginUrl;
  }
}
