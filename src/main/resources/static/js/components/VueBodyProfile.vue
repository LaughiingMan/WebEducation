<template>
    <div id="vue-body-profile">
        <v-app>
            <v-container
                    class="fill-height align-start profile-background"
                    fluid
            >
                <v-fade-transition mode="out-in">
                    <router-view name="users"></router-view>
                    <router-view name="statistics"></router-view>
                    <router-view name="test"></router-view>
                    <router-view name="build"></router-view>
                    <router-view name="testing"></router-view>
                </v-fade-transition>
            </v-container>
        </v-app>
    </div>
</template>

<script>
    export default {
        name: 'VueBodyProfile'
    }
</script>

<style>
    .profile-background {
        background-image: url('/img/profile_background.png');
        background-position: center;
        background-repeat:  no-repeat;
        background-attachment: fixed;
        background-size:  cover;
        background-color: transparent;
    }
</style>