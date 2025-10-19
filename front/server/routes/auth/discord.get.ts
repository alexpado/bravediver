 export default defineOAuthDiscordEventHandler({
    config: useRuntimeConfig().oauth.discord,
    async onSuccess(event, result) {
         await replaceUserSession(event, {
            user: {
                username: result.user.username,
            }
        }, {
            cookie: {
                httpOnly: true,
                secure: !import.meta.dev,
                expires: new Date(Date.now() + result.tokens.expires_in * 1000 - 60000) // 1 minute before token expiration
            }
        })
        return sendRedirect(event, '/')
    }
})
