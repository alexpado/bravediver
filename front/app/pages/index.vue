<template>
    <div>
        <h1>Welcome to BraveDiver</h1>
        <button @click="logout">
            logout
        </button>
     </div>
</template>


<script setup lang="ts">
    const userSession = useUserSession()

    const logout = async () => {
        await userSession.clear() 
        return navigateTo({ name: 'login'})
    }
</script>
