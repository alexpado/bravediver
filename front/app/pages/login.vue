<template>
    <div class="relative min-h-svh overflow-hidden">
        <!-- Decorative background -->
        <div aria-hidden="true" class="pointer-events-none absolute inset-0 -z-10">
            <div class="absolute -top-48 -left-32 size-[36rem] rounded-full bg-super-earth/10 blur-3xl" />
            <div class="absolute -bottom-40 -right-20 size-[28rem] rounded-full bg-democratic/10 blur-3xl" />
            <div
                class="absolute inset-0 bg-[radial-gradient(ellipse_at_top,rgba(255,255,255,0.06),transparent_60%),radial-gradient(ellipse_at_bottom,rgba(59,130,246,0.08),transparent_60%)]" />
        </div>

        <main class="mx-auto flex min-h-svh max-w-7xl items-center justify-center px-4 sm:px-6 lg:px-8">
            <section class="w-full max-w-md">
                <div class="mb-8 text-center">
                    <span
                        class="inline-flex items-center gap-2 rounded-full border border-white/10 bg-white/5 px-3 py-1 text-xs/5 text-white/70 backdrop-blur">
                        FOR DEMOCRACY
                        <span class="h-1 w-1 rounded-full bg-super-earth" />
                        SUPER EARTH COMMAND
                    </span>
                    <h1
                        class="mt-4 sm:text-5xl">
                        BraveDiver</h1>
                    <h2 class="mx-auto mt-3 max-w-prose text-balance ">
                        Welcome, Helldiver. 
                    </h2>
                        <p>Log in with Discord to rally with your squad, track loadouts, and spread
                        Managed Democracy across the galaxy.</p>

                        <p>This is a kind of mission where you randomize your loadout and dive into the fray. 
                  
                    </p>
                    <p>Good luck!</p>
                </div>

                <!-- Card -->
                <div
                    class="rounded-2xl border border-white/10 bg-white/5 p-6 shadow-2xl shadow-black/30 backdrop-blur sm:p-8">
                    <div class="mb-6 flex items-center gap-3">
                        <div
                            class="grid size-10 place-items-center rounded-xl bg-super-earth/20 ring-1 ring-super-earth/30">
                            <span class="text-xl">★</span>
                        </div>
                        <div>
                            <h2 class="text-lg font-semibold leading-tight">Secure Access</h2>
                            <p class="text-sm text-white/60">Authenticate via Discord to proceed</p>
                        </div>
                    </div>

                    <button
                        type="button"
                        class="group relative flex w-full items-center justify-center gap-3 overflow-hidden rounded-xl bg-[#5865F2] px-5 py-3 text-sm font-semibold text-white shadow-lg shadow-[#5865F2]/30 outline-none transition duration-200 hover:brightness-110 focus-visible:ring-2 focus-visible:ring-white/30 disabled:cursor-not-allowed disabled:opacity-60"
                        @click="loginWithDiscord"
                    >    
                       <Icon name="line-md:discord-twotone" class="size-5" />
                        <span>{{ 'Continue with Discord' }}</span>
                    </button>
                </div>
            </section>
        </main>
    </div>

</template>

<script setup lang="ts"> 
useHead({
    title: 'Login • BraveDiver'
})

function loginWithDiscord() {
    const url = '/auth/discord'
    window.location.href = url
}
 
</script>

<style scoped>
/* Subtle animated twinkle effect */
@keyframes twinkle {

    0%,
    100% {
        opacity: 0.35;
    }

    50% {
        opacity: 0.7;
    }
}

[aria-hidden="true"]>div:first-child,
[aria-hidden="true"]>div:nth-child(2) {
    animation: twinkle 6s ease-in-out infinite alternate;
}

/* Respect user motion preferences */
@media (prefers-reduced-motion: reduce) {

    [aria-hidden="true"]>div:first-child,
    [aria-hidden="true"]>div:nth-child(2) {
        animation: none;
    }
}
</style>