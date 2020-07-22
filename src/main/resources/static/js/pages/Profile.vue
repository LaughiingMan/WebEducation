<template>
    <v-app>
        <app-bar />

        <vue-body-profile />
    </v-app>
</template>

<script>
    import AppBar from '../components/AppBar.vue'
    import VueBodyProfile from '../components/VueBodyProfile.vue'
    import store from '../store/store'

    export default {
        components: {VueBodyProfile, AppBar},
    }
</script>

<style>

</style>