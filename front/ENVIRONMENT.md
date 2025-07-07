# Environment Setup

## Local Development

1. Copy the environment template:
   ```bash
   cp src/environments/environment.local.template.ts src/environments/environment.local.ts
   ```

2. Update `environment.local.ts` with your actual values:
   - `discordClientId`: Get from [Discord Developer Portal](https://discord.com/developers/applications)

3. The `environment.local.ts` file is git-ignored and won't be committed.

## Production Deployment

For production builds, use environment variables or CI/CD secrets:

```bash
# Replace placeholders during build
sed -i 's/REPLACE_WITH_YOUR_DISCORD_CLIENT_ID/'"$DISCORD_CLIENT_ID"'/g' src/environments/environment.prod.ts
npm run build
```

## Environment Variables

- `DISCORD_CLIENT_ID`: Discord OAuth2 client ID
- `API_URL`: Backend API URL
