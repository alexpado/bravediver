class Bravediver {

    static REDIRECT_URI = encodeURIComponent('http://localhost:8080/')
    static CLIENT_ID    = '1387790842508476508'
    static SCOPE        = 'identify'

    static LOGIN_URL = `https://discord.com/oauth2/authorize?response_type=code&client_id=${Bravediver.CLIENT_ID}&scope=${Bravediver.SCOPE}&redirect_uri=${Bravediver.REDIRECT_URI}&prompt=none`;

    constructor() {
        this.session = null;
    }

    async authenticate() {
        const search = new URLSearchParams(window.location.search);
        if (search.has('code')) {
            const response = await fetch(`/api/v1/auth/${search.get('code')}`);
            const auth     = await response.json();

            if (auth.authenticated) {
                this.session = auth.session;
                return true;
            } else {
                // Do something with auth.message or/and auth.details (technical message).
                return false;
            }
        }
        window.location.href = Bravediver.LOGIN_URL;
    }
}
