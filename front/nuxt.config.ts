// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2025-07-15',
  devtools: { enabled: true },
 
  css: ['~/assets/css/main.css'],

  modules: [
    '@nuxt/eslint',
    '@nuxt/image',
    '@nuxt/scripts',
    '@nuxt/test-utils',
    '@nuxt/ui',
    'nuxt-auth-utils'
    ],

  runtimeConfig: {
    oauth: {
      discord: {
        clientId: '',
        clientSecret: '',
        redirectURL: ''
      }
    }
  },

  nitro: {
    experimental: {
      database: true,
      tasks: true,
    },
    database: {
      default: {
        connector: 'sqlite',
        options: { name: 'db' }
      },
    }
  }
})
